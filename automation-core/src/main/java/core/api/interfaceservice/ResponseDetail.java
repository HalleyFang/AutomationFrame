package core.api.interfaceservice;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应消息
 */
public class ResponseDetail {

    private int status;
    private String response_msg;
    private Header[] headers;
    private String cookie = "";
    private String xsrf;

    /**
     * 获取xsrf
     * @return
     */
    public String getXsrf() {
        for (Header header : headers){
            if(header.getName().equalsIgnoreCase("set-cookie")){
                if(header.getValue().contains("XSRF-TOKEN")){
                    String[] tmp = header.getValue().split(";");
                    String[] tmp2 = tmp[0].split("=");
                    xsrf = tmp2[1];
                }
            }
        }
        return xsrf;
    }

    /**
     * 获取cookie
     * @return
     */
    public String getCookie() {
        StringBuffer params = new StringBuffer();
        for (Header header : headers){
            if(header.getName().equalsIgnoreCase("set-cookie")){
                params.append(header.getValue());
            }
        }
        String[] paramsArry = params.toString().replace("Path=/; HttpOnly","").split(";");
        for(int i=0;i<paramsArry.length;i++){
//            if(!paramsArry[i].trim().equalsIgnoreCase("Path=/") && !paramsArry[i].trim().equalsIgnoreCase("HttpOnly")){
                if(paramsArry[i].trim().startsWith("XSRF-TOKEN")){
                    cookie += paramsArry[i] + ";";
                }else if(paramsArry[i].trim().startsWith("Path")){
                    String cookieTmp = paramsArry[i].substring(7);
                    cookie += cookieTmp + ";";
                }else if(StringUtils.isBlank(paramsArry[i])){
                    continue;
                } else {
                    cookie += paramsArry[i] + ";";
                }
            //}
        }
        return cookie;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResponse_msg() {
        return response_msg;
    }

    public void setResponse_msg(String response_msg) {
        this.response_msg = response_msg;
    }

    public Map<String,String> getHeadersMap(){
        Map<String,String> map = new HashMap<>();
        map.put("Cookie",this.getCookie());
        map.put("X-XSRF-TOKEN",this.getXsrf());
        return map;
    }

}
