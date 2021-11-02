package api.apidata;

/**
 * 接口数据文件枚举
 */
public enum DataFilePathData {

    LOGIN_FILE_PATH("api/login.json");

    private String dataFilePathData;

    DataFilePathData(String dataFilePathData) {
        this.dataFilePathData = dataFilePathData;
    }

    public String getDataFilePathData() {
        return dataFilePathData;
    }
}
