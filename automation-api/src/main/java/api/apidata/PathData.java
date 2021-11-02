package api.apidata;

/**
 * URL path 数据枚举
 */
public enum PathData {

    LOGIN_PATH("/api/auth/login");


    private String path;

    PathData(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
