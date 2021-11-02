package core.utils;

public enum ApiConf {

    HTTP("http"),
    HTTPS("https"),
    POST("POST"),
    GET("GET"),
    CONTENT_TYPE("application/json;charset=utf8");

    private String value;
    ApiConf(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
