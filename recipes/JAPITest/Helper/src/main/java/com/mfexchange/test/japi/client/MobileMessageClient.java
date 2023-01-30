package com.elong.test.japi.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.elong.test.japi.utils.AESUtil;
import com.elong.test.japi.utils.Props;
import com.elong.test.japi.verify.IVerify;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

public class MobileMessageClient extends IMessageClient {
    public MobileMessageClient() {
        httpclient = HttpClients.createDefault();
        inKvs = new HashMap<String, String>();
    }

    public String assemble(Map<String, String> kvs) throws Exception {
        inKvs.putAll(kvs);
        String url = kvs.remove("http_url");
        String method = kvs.remove("http_method");

        Props props = new Props();
        props.loadFrom(getClass().getResourceAsStream("/helper.properties"));
        Map<String, String> headers = json2Map(props.get("headers"));

        kvs.remove("type");
        kvs.remove("expected");
        String encryption = kvs.remove("encryption");
        if (encryption.startsWith("AES")) {
            kvs.remove("action");
            String secret = encryption.substring(3);
            kvs.put("req", AESUtil.encryptByKey(kvs.get("req"), secret));
        }

        if (method.equalsIgnoreCase("GET")) {
            URI uri = getURI(url, kvs);
            requestBase = new HttpGet(uri);
        } else if (method.equalsIgnoreCase("POST")) {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (String key : kvs.keySet()) {
                String value = kvs.get(key);
                nameValuePairs.add(new BasicNameValuePair(key, value));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            requestBase = httpPost;
        } else if (method.equals("DELETE")) {
            URI uri = getURI(url, kvs);
            requestBase = new HttpDelete(uri);
        } else if (method.equals("PUT")) {
            URI uri = getURI(url, kvs);
            requestBase = new HttpPut(uri);
        } else {
            throw new IllegalArgumentException(String.format("Unsupported http method %s!", method));
        }

        for (String key : headers.keySet()) {
            String value = headers.get(key);
            requestBase.setHeader(key, value);
        }
        return requestBase.getURI().toString();
    }

    public String send() throws Exception {
        response = httpclient.execute(requestBase);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while (null != (line = br.readLine())) {
            sb.append(line);
            sb.append("\n");
        }
        response.close();
        content = sb.toString();
        return content;
    }

    public void verify() throws Exception {
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        String expected = inKvs.get("expected");
        JSONObject jsonObj = new JSONObject(expected);
        String verifyClassName = jsonObj.getString("VerifyClass");
        JSONArray verifySteps = jsonObj.getJSONArray("VerifySteps");

        IVerify verify = new IVerify(verifyClassName, inKvs, content);
        for (int i = 0; i < verifySteps.length(); ++i) {
            JSONArray verifyStep = verifySteps.getJSONArray(i);
            String verifyMethod = verifyStep.getString(0);
            String[] args = new String[verifyStep.length() - 1];
            for (int j = 1; j < verifyStep.length(); ++j) {
                args[j - 1] = verifyStep.getString(j);
            }
            verify.verify(verifyMethod, args);
        }
    }

    public void close() throws Exception {
        httpclient.close();
    }

    protected URI getURI(String url, Map<String, String> kvs) throws Exception {
        String hostPort = url;
        if (url.startsWith("http://")) {
            hostPort = url.substring("http://".length());
        }
        String path = "/";
        int index = hostPort.indexOf("/");
        if (-1 != index) {
            path = hostPort.substring(index);
            hostPort = hostPort.substring(0, index);
        }
        String[] hostPortSplit = hostPort.split(":");
        String host = hostPort;
        int port = 80;
        if (2 == hostPortSplit.length) {
            host = hostPortSplit[0];
            port = Integer.parseInt(hostPortSplit[1]);
        }

        URIBuilder builder = new URIBuilder();
        builder.setScheme("http")
                .setHost(host)
                .setPort(port)
                .setPath(path);
        for (String key : kvs.keySet()) {
            String value = kvs.get(key);
            if (null != value) {
                builder.addParameter(key, value);
            }
        }
        URI uri = builder.build();
        return uri;
    }

    protected Map<String, String> json2Map(String jsonData) throws Exception {
        Map<String, String> m = new HashMap<String, String>();
        JSONObject jsonObj = new JSONObject(jsonData);
        Iterator<?> iter = jsonObj.keys();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            Object valueObj = jsonObj.get(key);
            String value = null;
            if (null != valueObj) {
                value = valueObj.toString();
            }
            m.put(key, value);
        }
        return m;
    }

    protected CloseableHttpClient httpclient;
    protected HttpRequestBase requestBase;
    protected CloseableHttpResponse response;
    protected Map<String, String> inKvs;
    protected String content;
}
