package com.fengjr.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
//import java.util.Iterator;
import java.util.Map;

//import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
//import net.sf.json.JSONSerializer;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.fengjr.util.HttpUtils;
/**
 * Created with IntelliJ IDEA.
 * User: frankqi
 * Date: 14-4-8
 * Time: 下午6:52
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CallInterface  {
    @ResponseBody
    @RequestMapping(value = "/callApi")
    public void callApi(HttpServletRequest request,HttpServletResponse response) {
        HttpSession session=request.getSession();
        String callUrl=request.getParameter("callUrl");
        String isLogininfo=request.getParameter("info");
        String info="info=";
        if (isLogininfo.indexOf("userSession")!=-1){
            //{'openId':'oLRJfuKqsku1OdTgeGFkjwtkrlOI'}
            JSONObject json = JSONObject.fromObject(isLogininfo);
            Object flagUid=json.get("userSession");
            if (null!=flagUid){
                Object sesessionUid= session.getAttribute("flagUid");
                if (sesessionUid==null){
                    PrintWriter out = null;
                    try {
                        out = response.getWriter();
                        out.write("{\"respcode\":\"-1\",\"message\":\"登录超时\",\"result\":{}}");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }else{
                    if (flagUid.equals(sesessionUid)){
                        Object userSession=session.getAttribute("userSession");
                        if (null!=userSession){
                            String clientIp = null;
                            if (request.getHeader("X-Forwarded-For") == null) {
                                clientIp = request.getRemoteAddr();
                            } else {
                                clientIp = request.getHeader("X-Forwarded-For");
                            }
                            json.put("userSession",userSession);
                            json.put("clientIp",clientIp);
                            info=info+json.toString();
                        }else{ //
                            PrintWriter out = null;
                            try {
                                out = response.getWriter();
                                out.write("{\"respcode\":\"9003\",\"message\":\"登录超时\",\"result\":{}}");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                    }else {// session异常
                        PrintWriter out = null;
                        try {
                            out = response.getWriter();
                            out.write("{\"respcode\":\"-3\",\"message\":\"登录超时\",\"result\":{}}");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
            }
        } else {
            info=info+isLogininfo;
        }

        String auth="&auth="+request.getParameter("auth");
        StringBuffer sb=new StringBuffer();
        //sb.append("http://192.168.1.5  9:8080/API2.0/").append(callUrl).append("?");
        //sb.append("https://localhost:8081/").append(callUrl).append("?");
        sb.append("http://115.28.36.45:85/").append(callUrl).append("?");
        String result="";
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if(isMultipart){
                MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest)request;
                Map<String, String> stringBodyMap = HttpUtils.asMap(info + auth);
                Map<String,File> fileBodyMap = new HashMap<String, File>();
                Map<String,MultipartFile> mfilePart= mrequest.getFileMap();
                try{
                    for(String fileName : mfilePart.keySet()){
                        MultipartFile mf = mfilePart.get(fileName);
                        File f = File.createTempFile(UUID.randomUUID().toString(),".wjs");
                        mf.transferTo(f);
                        fileBodyMap.put(fileName,f);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

                result = HttpUtils.doPostBody(sb.toString(), stringBodyMap, fileBodyMap, null, null, "utf-8", false);
            }else{
                result= HttpUtils.doPostBody(sb.toString(), info + auth,null, "utf-8",false);
            }

            if(callUrl.indexOf("loginUser.do")!=-1||callUrl.indexOf("addUser.do")!=-1){
                JSONArray json = JSONArray.fromObject("["+result+"]");
                JSONObject jsonObject=(JSONObject)json.get(0);
                Object results1tr=jsonObject.get("result");
                if (null==results1tr||results1tr.equals("")){
                }else {
                    JSONObject jsonResult=(JSONObject)results1tr;
                    Object userSession=jsonResult.get("userSession");
                    session.setAttribute("userSession",userSession);
                    String uid= UUID.randomUUID().toString();
                    jsonResult.put("userSession",uid);
                    session.setAttribute("flagUid",uid);
                    jsonObject.put("result",jsonResult);
                    json.set(0,jsonObject);
                    String jsstr=json.toString();
                    result=json.toString().substring(1,jsstr.length()-1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  static  void main(String strp[]){
//      String callUrl="getLoanList.do";
//      String auth="info={'pageIndex':'0','pageSize':'10'}&auth={'source':'100001','vendor':'100000','osver':'1','appver':'1','version':'20'}";
//        String callUrl="loginUser.do";
//    	String auth="info={'username':'15811297594','loginPwd':'666666'}&auth=" + "{'source':'7','vendor':'100000','osver':'1','appver':'1','version':'20'}";
//
//        StringBuffer sb=new StringBuffer();
//        sb.append("http://115.28.36.45:85/").append(callUrl).append("?");
//        String results1= HttpUtils.doPostBody(sb.toString(), auth,null, "utf-8",false);
//        
//        System.out.println("未替换之前"+results1);
        
        String callUrls="getInvestmentLog.do";
    	String auths="info={'loanId':'1660','userSession':'MjMzNSo3KjEwMDAwMCoyMDE0LTA5LTE2IDEzOjQzOjU0'}&auth=" + "{'source':'7','vendor':'100000','osver':'1','appver':'1','version':'20'}";

        StringBuffer sbs=new StringBuffer();
        sbs.append("http://115.28.36.45:85/callApi.do?").append(callUrls).append("?");
        String results2= HttpUtils.doPostBody(sbs.toString(), auths,null, "utf-8",false);
        
        System.out.println("未替换之前"+results2);

		System.out.println(results2 = results2.replace('\"', '\''));
		System.out.println(results2 = results2.replace("{\'r", "[{\'r"));
		System.out.println(results2 = results2.replace("[]}}", "[]}}]"));
		System.out.println("替换之后" + results2);

    }
}
