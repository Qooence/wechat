package com.qooems.wechat.common.base;


import com.alibaba.fastjson.JSONObject;
import com.qooems.wechat.common.response.Response;
import com.qooems.wechat.common.response.ResponseCode;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;

/**
 * controller通用数据
 * Created by Administrator on 2018-02-04.
 */
public class BaseController {
    protected static Logger logger = LoggerFactory.getLogger(BaseController.class);

    private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();

    private ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();

    public HttpServletRequest getRequest() {
        return request.get();
    }

    public HttpServletResponse getResponse() {
        return response.get();
    }


    @ModelAttribute
    public void setReqAndResp(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        this.request.set(request);
        this.response.set(response);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ModelAndView handleMethodArgumentNotValidException(BindException e) {
//		response.get().setContentType("application/json;charset=utf-8");
        response.get().setCharacterEncoding("utf-8");
        response.get().setContentType("text/html");
        BindingResult result = e.getBindingResult();
        String message = null;
        if(result.getErrorCount()>0){
            ObjectError error = result.getAllErrors().get(0);
            message = error.getDefaultMessage();
        }
        logger.debug("参数验证失败:"+message);

        JSONObject jsonResult = (JSONObject) JSONObject.toJSON(Response.error(message,ResponseCode.PARAMS_ERROR));
        jsonResult.put("errors",message);

        PrintWriter printWriter = null;
        try {
            printWriter = response.get().getWriter();
            printWriter.write(jsonResult.toJSONString());
            printWriter.flush();
        } catch (IOException e1) {
            logger.error("发生异常：",e1);
        } finally {
            if(printWriter != null){
                printWriter.close();
            }
        }
        return null;
    }

    @ExceptionHandler
    public ModelAndView exceptionImpl(Exception e){
//		response.get().setContentType("application/json;charset=utf-8");
        response.get().setCharacterEncoding("utf-8");
        response.get().setContentType("text/html");
        String errorMsg = null;

        ResponseCode responseCode;
        if(e instanceof MaxUploadSizeExceededException){//spring上传
            errorMsg = "上传文件过大";
            logger.info(errorMsg);
            responseCode = ResponseCode.PARAMS_ERROR;
        }else {
            errorMsg = "系统异常！";
            response.get().setStatus(500);
            logger.error(errorMsg, e);
            responseCode = ResponseCode.SYSTEM_ERROR;
        }

        JSONObject result = (JSONObject) JSONObject.toJSON(Response.error(errorMsg,responseCode));
        result.put("errors",errorMsg);

        PrintWriter printWriter = null;
        try {
            printWriter = response.get().getWriter();
            printWriter.write(result.toJSONString());
            printWriter.flush();
        } catch (IOException e1) {
            logger.error("发生异常：",e1);
        } finally {
            if(printWriter != null){
                printWriter.close();
            }
        }
        return null;
    }


    public int pageInfoHandler(String pageInfo){
        if(StringUtils.isBlank(pageInfo)){
            return 0;
        } else {
            return Integer.valueOf(pageInfo);
        }
    }

    public int getPageSize(){
        String limit = getRequest().getParameter("limit");
        return pageInfoHandler(limit);
    }

    public int getPageNum(int pageSize){
        String start = getRequest().getParameter("start");
        int startIndex = pageInfoHandler(start);
        return pageSize == 0 ? 0 : startIndex;
    }

    /**
     * 获取ip
     * @param httpservletrequest
     * @return
     */
    public static String getClientIP(HttpServletRequest httpservletrequest) {
        if (httpservletrequest == null)
            return null;
        String s = httpservletrequest.getHeader("X-Forwarded-For");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
            s = httpservletrequest.getHeader("Proxy-Client-IP");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
            s = httpservletrequest.getHeader("WL-Proxy-Client-IP");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
            s = httpservletrequest.getHeader("HTTP_CLIENT_IP");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
            s = httpservletrequest.getHeader("HTTP_X_FORWARDED_FOR");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
            s = httpservletrequest.getRemoteAddr();
        if ("127.0.0.1".equals(s) || "0:0:0:0:0:0:0:1".equals(s))
            try {
                s = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception unknownhostexception) {
                unknownhostexception.printStackTrace();
            }
        return s.split(",")[0];
    }

}
