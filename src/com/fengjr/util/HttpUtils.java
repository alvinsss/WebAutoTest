package com.fengjr.util;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * HTTP 请求工具类
 * @author bincode
 * @email	5235852@qq.com
 */
public class HttpUtils {
    private static Logger logger = Logger.getLogger(HttpUtils.class);
    private static String tempfilepath = "/httptemp";
    private static Map<String, DefaultHttpClient> _threadHttpClient = new HashMap<String, DefaultHttpClient>();
    private static Map<String, HttpContext> _threadHttpContext = new HashMap<String, HttpContext>();
    private static DefaultHttpClient httpclient=null;
    static {
        tempfilepath = JavaUtils.classpath + tempfilepath;
        File tempfilepathFile = new File(tempfilepath);
        if (!tempfilepathFile.exists())
            tempfilepathFile.mkdirs();
    }

    /**
     * 打印Cookies信息
     * @param httpContext
     */
    public static void printCookies(HttpContext httpContext){
//		CookieStore cookieStore = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
//		List<Cookie> cookies = cookieStore.getCookies();
//		if (cookies.isEmpty()) {
//			System.out.println("None");
//		  } else {
//		for (int i = 0; i < cookies.size(); i++) {
//			System.out.println("- " + cookies.get(i).toString());
//			}
//		}
    }
    /**
     * 打印Cookies信息
     */
    public static List<Cookie> getCookies(){
        HttpContext httpContext = _threadHttpContext.get("httpContext");
        CookieStore cookieStore = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
        List<Cookie> cookies = cookieStore.getCookies();
        return cookies;
    }

    /**
     * 获取请求URL的网页内容
     */
    public static HttpClient createHttpClient() {
        httpclient = _threadHttpClient.get("httpclient");
        if (httpclient != null) {
            return httpclient;
        }
        SchemeRegistry registry = new SchemeRegistry();//创建schema
        SSLContext sslContext = null;//https类型的消息访问
        try{
            TrustManager easyTrustManager = new X509TrustManager() {
                public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                }
                public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                }
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];  //To change body of implemented methods use File | Settings | File Templates.
                }
            };

            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{easyTrustManager}, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        SSLSocketFactory sslFactory = new SSLSocketFactory(sslContext,SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
        sslFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));//http 80 端口
        registry.register(new Scheme("https", 443, sslFactory));//https 443端口
        PoolingClientConnectionManager cm = new PoolingClientConnectionManager(registry);//创建connectionManager
        cm.setDefaultMaxPerRoute(20);//对每个指定连接的服务器（指定的ip）可以创建并发20 socket进行访问
        cm.setMaxTotal(200);//创建socket的上线是200
        HttpHost localhost = new HttpHost("locahost", 80);
        cm.setMaxPerRoute(new HttpRoute(localhost), 80);//对本机80端口的socket连接上限是80
        HttpClient httpClient = new DefaultHttpClient(cm);//使用连接池创建连接
        HttpParams params = httpClient.getParams();

        HttpConnectionParams.setSoTimeout(params, 0);//设定连接等待时间
        HttpConnectionParams.setConnectionTimeout(params, 0);//设定超时时间

        httpclient = new DefaultHttpClient(cm);//使用连接池创建连接
        _threadHttpClient.put("httpclient", httpclient);

        httpclient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(final HttpRequest request,
                                final HttpContext context) throws HttpException,
                    IOException {
                // if (!request.containsHeader("Accept-Encoding")) {
                // request.addHeader("Accept-Encoding", "gzip");
                // }
                if (!request.containsHeader("Accept")) {
                    request.addHeader("Accept", "*/*");
                }
                if (request.containsHeader("User-Agent")) {
                    request.removeHeaders("User-Agent");
                }
                if (request.containsHeader("Connection")) {
                    request.removeHeaders("Connection");
                }
                request.addHeader("User-Agent","Mozilla/5.0 (Windows NT 5.1; rv:8.0) Gecko/20100101 Firefox/8.0");
                request.addHeader("Connection", "keep-alive");
                // request.addHeader("Connection", "close");
            }

        });
        httpclient.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(final HttpResponse response,
                                final HttpContext context) throws HttpException,
                    IOException {
//				HttpEntity entity = response.getEntity();
            }
        });
        httpclient.setRedirectHandler(new DefaultRedirectHandler());
        return httpclient;
    }

    private static HttpContext getHttpContext(String urlHost, String cookies) {
        HttpContext httpContext = _threadHttpContext.get("httpContext");

        if (httpContext != null) {
            printCookies(httpContext);
            return httpContext;
        }
        httpContext = new BasicHttpContext();
        CookieStore cookieStore = createCookieStore(urlHost, cookies);
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        _threadHttpContext.put("httpContext", httpContext);
        printCookies(httpContext);
        return httpContext;
    }

    public static void shutdownHttpClient() {
        _threadHttpContext.remove("httpContext");
        httpclient = _threadHttpClient.get("httpclient");
        if (httpclient != null) {
            httpclient.getConnectionManager().shutdown();
        }
        _threadHttpClient.remove("httpclient");
    }

    public static CookieStore createCookieStore(String urlHost, String cookieStr) {
        // Create a local instance of cookie store
        CookieStore cookieStore = new BasicCookieStore();
        if (cookieStr == null || "".equals(cookieStr))
            return cookieStore;
        String domain = urlHost.substring(urlHost.indexOf("//")+2);
        if (null != cookieStr && !cookieStr.trim().equals("")) {
            String[] cookies = cookieStr.split(";");
            // userCookieList = new AttributeList();
            for (int i = 0; i < cookies.length; i++) {
                int _i = cookies[i].indexOf("=");
                if (_i != -1) {
                    String name = cookies[i].substring(0, _i);
                    String value = cookies[i].substring(_i + 1);
                    BasicClientCookie _cookie = new BasicClientCookie(name,
                            value);
                    _cookie.setDomain(domain);
                    cookieStore.addCookie(_cookie);
                }
            }
        }
        return cookieStore;
    }

    public static List<NameValuePair> createNameValuePair(String params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (null != params && !params.trim().equals("")) {
            String[] _params = params.split("&");
            // userCookieList = new AttributeList();
            for (int i = 0; i < _params.length; i++) {
                int _i = _params[i].indexOf("=");
                if (_i != -1) {
                    String name = _params[i].substring(0, _i);
                    String value = _params[i].substring(_i + 1);
                    nvps.add(new BasicNameValuePair(name, value));
                }
            }
        }
        return nvps;
    }
    /**
     * 将参数转换成MAP
     * @param params
     * @return
     */
    public static Map<String, String> asMap(String params) {
        Map<String, String> nvps = new HashMap<String, String>();
        if (null != params && !params.trim().equals("")) {
            String[] _params = params.split("&");
            for (int i = 0; i < _params.length; i++) {
                int _i = _params[i].indexOf("=");
                if (_i != -1) {
                    String name = _params[i].substring(0, _i);
                    String value = _params[i].substring(_i + 1);
                    nvps.put(name, value);
                }
            }
        }
        return nvps;
    }

    public static String doGetBody(String url, String cookieStr) {
        try {
            String urlEx = url.substring(0, url.lastIndexOf("/"));
            String urlHost = url;


            HttpClient httpclient = createHttpClient();
            HttpContext localContext = getHttpContext(urlHost, cookieStr);
            String resultBody = null;
            int _count = 0;
            String loadUrl = url;
            HttpGet httpget = null;
            while (_count++ < 5) {
                try {
                	//请求内容
//                    logger.info("加载(" + _count + "):" + url + "==>" + loadUrl);
                    localContext.removeAttribute(DefaultRedirectHandler.REDIRECT_LOCATIONS);
                    httpget = new HttpGet(loadUrl);
                    HttpResponse response = httpclient.execute(httpget, localContext);
                    String locationUrl = checkLocation(response);
                    if (locationUrl != null) {
                        loadUrl = locationUrl;
                        if (!loadUrl.startsWith("/")&&loadUrl.indexOf("://") == -1)
                            loadUrl = urlEx + loadUrl;
                        else if (loadUrl.indexOf("://") == -1) {
                            loadUrl = urlHost + loadUrl;
                        }
                        continue;
                    }
                    if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                        continue;
                    }
                    HttpEntity entity = response.getEntity();
                    // Consume response content
                    if (entity != null) {
                        resultBody = EntityUtils.toString(entity);
                        entity.consumeContent();
                        locationUrl = checkLocation(resultBody);
                        if (resultBody == null) {
                        } else {
                            locationUrl = checkLocation(resultBody);
                            if (locationUrl != null) {
                                loadUrl = locationUrl;
                                if (!loadUrl.startsWith("/"))
                                    loadUrl = urlEx + loadUrl;
                                else if (loadUrl.indexOf("://") == -1) {
                                    loadUrl = urlHost + loadUrl;
                                }
                            } else
                                break;
                        }
                    }

                } catch (ClientProtocolException e) {
                    //e.printStackTrace();
                } catch (IOException e) {
                    //e.printStackTrace();
                } finally {
                    if (httpget != null)
                        httpget.abort();
                }
            }
            return resultBody;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File doGetFile(String url, String cookieStr) {
        url = url.replaceAll("###(.*)", "");
        String urlHost = url;
        try {
            urlHost = url.substring(0, url.indexOf("/", 9));
        } catch (Exception e) {
        }

        HttpClient httpclient = createHttpClient();
        HttpContext localContext = getHttpContext(urlHost, cookieStr);
        HttpGet httpget = new HttpGet(url);

        HttpResponse response;
        try {
            response = httpclient.execute(httpget, localContext);
            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            String filename = new Date().getTime() + ".temp";
            if (entity != null) {
                BufferedInputStream bis = new BufferedInputStream(entity
                        .getContent());
                File file = new File(tempfilepath + "/" + filename);
                FileOutputStream fs = new FileOutputStream(file);

                byte[] buf = new byte[1024];
                int len = bis.read(buf);
                if(len == -1 || len == 0){
                    file.delete();
                    file = null;
                    entity.consumeContent();
                    return file;
                }
                while (len != -1) {
                    fs.write(buf, 0, len);
                    len = bis.read(buf);
                }
                fs.close();

                entity.consumeContent();
                return file;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doPostBody(String url, String params,String cookieStr, String encode, boolean redirt) {
        url = url.replaceAll("###(.*)", "");
        String urlEx = url.substring(0, url.lastIndexOf("/"));
        String urlHost = url;
        try {
            urlHost = url.substring(0, url.indexOf("/", 9));
        } catch (Exception e) {
        }

        HttpClient httpclient = createHttpClient();
        HttpContext localContext = getHttpContext(urlHost, cookieStr);
        int _count = 0;
        String loadUrl = null;
        HttpPost httpost = null;
        String resultBody = null;
        try {
            httpost = new HttpPost(url);
            httpost.setHeader("Referer", "https://115.28.36.45:86");
            if (encode == null) {
                StringEntity stringEntity = new StringEntity(params);
                httpost.setEntity(stringEntity);
                httpost.setHeader("Content-Type","application/x-www-form-urlencoded");
            } else {
                List<NameValuePair> nvps = createNameValuePair(params);
                try {
                    httpost.setEntity(new UrlEncodedFormEntity(nvps,
                            encode));
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            HttpResponse response = httpclient.execute(httpost,localContext);
            String locationUrl = checkLocation(response);
            if (locationUrl != null) {
                loadUrl = locationUrl;
                if (!loadUrl.startsWith("/")
                        && loadUrl.indexOf("://") == -1)
                    loadUrl = urlEx + loadUrl;
                else if (loadUrl.indexOf("://") == -1) {
                    loadUrl = urlHost + loadUrl;
                }
            }
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {

            }
            HttpEntity entity = response.getEntity();
            // Consume response content
            if (entity != null) {
                resultBody = EntityUtils.toString(entity);
                entity.consumeContent();
                locationUrl = checkLocation(resultBody);
                if (resultBody == null) {
                } else {
                    locationUrl = checkLocation(resultBody);
                    if (locationUrl != null) {
                        loadUrl = locationUrl;
                        if (!loadUrl.startsWith("/"))
                            loadUrl = urlEx + loadUrl;
                        else if (loadUrl.indexOf("://") == -1) {
                            loadUrl = urlHost + loadUrl;
                        }
                    }

                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (loadUrl != null && redirt)
            resultBody = doGetBody(loadUrl, null);
        return resultBody;
    }

    public static String doPostBody(String url, byte[] content,
                                    Header[] headers, String cookieStr, String encode, boolean redirt) {
        if (encode == null)
            encode = HTTP.UTF_8;

        String urlEx = url.substring(0, url.lastIndexOf("/"));
        String urlHost = url;
        try {
            urlHost = url.substring(0, url.indexOf("/", 9));
        } catch (Exception e) {
        }
        HttpClient httpclient = createHttpClient();
        HttpContext localContext = getHttpContext(urlHost, cookieStr);
        int _count = 0;
        String loadUrl = null;
        HttpPost httpost = null;
        String resultBody = null;

        while (_count++ < 5) {
            try {
                logger.info("提交(" + _count + "):" + url);
                httpost = new HttpPost(url);

                ByteArrayEntity byteArrayEntity = new ByteArrayEntity(content);
                httpost.setEntity(byteArrayEntity);

                httpost.setHeaders(headers);

                HttpResponse response = httpclient.execute(httpost,
                        localContext);

                String locationUrl = checkLocation(response);
                if (locationUrl != null) {
                    loadUrl = locationUrl;
                    if (!loadUrl.startsWith("/")
                            && loadUrl.indexOf("://") == -1)
                        loadUrl = urlEx + loadUrl;
                    else if (loadUrl.indexOf("://") == -1) {
                        loadUrl = urlHost + loadUrl;
                    }
                    break;
                }
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    continue;
                }
                HttpEntity entity = response.getEntity();
                // Consume response content
                if (entity != null) {
                    resultBody = EntityUtils.toString(entity);
                    entity.consumeContent();
                    locationUrl = checkLocation(resultBody);
                    if (resultBody == null) {
                    } else {
                        locationUrl = checkLocation(resultBody);
                        if (locationUrl != null) {
                            loadUrl = locationUrl;
                            if (!loadUrl.startsWith("/"))
                                loadUrl = urlEx + loadUrl;
                            else if (loadUrl.indexOf("://") == -1) {
                                loadUrl = urlHost + loadUrl;
                            }
                        } else
                            break;
                    }
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpost != null)
                    httpost.abort();
            }
        }
        if (loadUrl != null && redirt)
            resultBody = doGetBody(loadUrl, null);
        return resultBody;
    }


    public static String doPostBody(String url, Map<String,String> stringBody, Map<String,File> fileBody,
                                    Header[] headers, String cookieStr, String encode, boolean redirt) {
        if (encode == null)
            encode = HTTP.UTF_8;

        String urlEx = url.substring(0, url.lastIndexOf("/"));
        String urlHost = url;
        try {
            urlHost = url.substring(0, url.indexOf("/", 9));
        } catch (Exception e) {
        }
        HttpClient httpclient = createHttpClient();
        HttpContext localContext = getHttpContext(urlHost, cookieStr);
        int _count = 0;
        String loadUrl = null;
        HttpPost httpost = null;
        String resultBody = null;

        while (_count++ < 5) {
            try {
                logger.info("提交(" + _count + "):" + url);
                httpost = new HttpPost(url);
//				httpost.setHeaders(headers);

                MultipartEntity reqEntity = new MultipartEntity();
                for(Iterator<Entry<String, File>>it = fileBody.entrySet().iterator(); it.hasNext();){
                    Entry<String, File> fileEntry = it.next();
                    FileBody file = new FileBody(fileEntry.getValue());
                    reqEntity.addPart(fileEntry.getKey(), file);
                }

                for(Iterator<Entry<String, String>>it = stringBody.entrySet().iterator(); it.hasNext();){
                    Entry<String, String> stringEntry = it.next();
                    StringBody str = new StringBody(stringEntry.getValue());
                    reqEntity.addPart(stringEntry.getKey(), str);
                }
                //设置请求
                httpost.setEntity(reqEntity);

                HttpResponse response = httpclient.execute(httpost,
                        localContext);

                String locationUrl = checkLocation(response);
                if (locationUrl != null) {
                    loadUrl = locationUrl;
                    if (!loadUrl.startsWith("/")
                            && loadUrl.indexOf("://") == -1)
                        loadUrl = urlEx + loadUrl;
                    else if (loadUrl.indexOf("://") == -1) {
                        loadUrl = urlHost + loadUrl;
                    }
                    break;
                }
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    continue;
                }
                HttpEntity entity = response.getEntity();
                // Consume response content
                if (entity != null) {
                    resultBody = EntityUtils.toString(entity);
                    entity.consumeContent();
                    locationUrl = checkLocation(resultBody);
                    if (resultBody == null) {
                    } else {
                        locationUrl = checkLocation(resultBody);
                        if (locationUrl != null) {
                            loadUrl = locationUrl;
                            if (!loadUrl.startsWith("/"))
                                loadUrl = urlEx + loadUrl;
                            else if (loadUrl.indexOf("://") == -1) {
                                loadUrl = urlHost + loadUrl;
                            }
                        } else
                            break;
                    }
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpost != null)
                    httpost.abort();
            }
        }
        if (loadUrl != null && redirt)
            resultBody = doGetBody(loadUrl, null);
        return resultBody;
    }

    /**
     * 检查是否包含链接转向，3种方法<br>
     * <ol>
     * <li>头部包含“location:”或“content-location:”，返回代号302</li>
     * <li>内容部分包含“meta http-equiv=refresh content="2;URL=..."”</li>
     * <li>js脚本刷新，正则为："(?s)<script.{0,50}?>\\s*((document)|(window)|(this))\\.location(\\.href)?\\s*="</li>
     * </ol>
     */
    private static String checkLocation(HttpResponse response) {
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            if (header.getName().equalsIgnoreCase("location")
                    || header.getName().equalsIgnoreCase("content-location")) {
                return header.getValue();
            }
        }
        return null;
    }

    /**
     * 检查是否包含链接转向，3种方法<br>
     * <ol>
     * <li>内容部分包含“meta http-equiv=refresh content="2;URL=..."”</li>
     * <li>js脚本刷新，正则为："(?s)<script.{0,50}?>\\s*((document)|(window)|(this))\\.location(\\.href)?\\s*="</li>
     * </ol>
     */
    private static String checkLocation(String httpBody) {
        String locationUrl = null;
        // 2.
        String bodyLocationStr = "";
        if (httpBody.length() > 5120) {
            bodyLocationStr = httpBody.substring(0, 5120);// 太长则截取部分内容
        } else {
            bodyLocationStr = httpBody;
        }
        bodyLocationStr = bodyLocationStr.replaceAll("<!--(?s).*?-->", "")
                .replaceAll("['\"]", "");// 去除注释和引号部分

        int metaLocation = -1;
        metaLocation = bodyLocationStr.toLowerCase().indexOf("http-equiv=refresh");
        if (metaLocation != -1) {
            String locationPart = bodyLocationStr.substring(metaLocation,
                    bodyLocationStr.indexOf(">", metaLocation));
            metaLocation = locationPart.toLowerCase().indexOf("url");
            if (metaLocation != -1) {
                // 假定url=...是在 > 之前最后的部分
                locationUrl = locationPart.substring(metaLocation + 4,
                        locationPart.length()).replaceAll("\\s+[^>]*", "");
                return locationUrl;
            }
        }
        // 3.
        Matcher locationMath = Pattern
                .compile(
                        "(?s)<script.{0,50}?>\\s*((document)|(window)|(this))\\.location(\\.href)?\\s*=")
                .matcher(httpBody.toLowerCase());
        if (locationMath.find()) {
            String[] cs = httpBody.substring(locationMath.end()).trim().split(
                    "[> ;<]");
            locationUrl = cs[0];
            cs = null;
            return locationUrl;
        }
        // 没有转向
        return null;
    }
}