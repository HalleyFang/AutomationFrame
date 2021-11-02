package api.config;

import api.apidata.PathData;
import api.apidata.jsonanalysis.DefaultJsonAnalysis;
import api.config.InterfaceConfig;
import core.api.apiutils.GeneraUri;
import core.api.interfaceservice.JsonAnalysis;
import core.api.interfaceservice.ResponseDetail;
import core.api.interfaceservice.SendRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * 公共操作
 */
@Component
public class ShareVoid {

    @Autowired
    SendRequest sendRequest;

    private ResponseDetail responseDetail;
    private URI uri;
    private Map<String, String> cookie;

    @Autowired
    InterfaceConfig interfaceConfig;

    /**
     * 登陆获取cookie（默认path）
     *
     * @param dataFile
     * @return
     * @throws URISyntaxException
     */
    public Map<String, String> loginGetCookie(String dataFile) throws URISyntaxException {
        responseDetail = post(PathData.LOGIN_PATH.getPath(), dataFile, new DefaultJsonAnalysis());
        cookie = responseDetail.getHeadersMap();
        return cookie;
    }

    /**
     * 登陆获取cookie（指定path）
     *
     * @param path
     * @param dataFile
     * @return
     * @throws URISyntaxException
     */
    public Map<String, String> loginGetCookie(String path, String dataFile) throws URISyntaxException {
        responseDetail = post(path, dataFile, new DefaultJsonAnalysis());
        cookie = responseDetail.getHeadersMap();
        return cookie;
    }

    /**
     * 生成url
     *
     * @param path
     * @return
     * @throws URISyntaxException
     */
    public URI getUri(String path) throws URISyntaxException {
        uri = GeneraUri.getUri(interfaceConfig.getIp(), interfaceConfig.getPort(), interfaceConfig.getContextPath() + path, null, false);
        return uri;
    }

    /**
     * Post数据文件
     *
     * @param path
     * @param dataFile
     * @return
     * @throws URISyntaxException
     */
    public ResponseDetail post(String path, String dataFile, JsonAnalysis jsonAnalysis) throws URISyntaxException {
        responseDetail = sendRequest.postRequest(getUri(path), null, dataFile, null, jsonAnalysis);
        return responseDetail;
    }

    /**
     * 修改请求头的Post
     *
     * @param path
     * @param mapPost
     * @param dataFile
     * @return
     * @throws URISyntaxException
     */
    public ResponseDetail post(String path, Map<String, String> mapPost, String dataFile, JsonAnalysis jsonAnalysis) throws URISyntaxException {
        responseDetail = sendRequest.postRequest(getUri(path), mapPost, dataFile, null, jsonAnalysis);
        return responseDetail;
    }

    /**
     * 修改请求头和请求体的Post
     *
     * @param path
     * @param mapPost
     * @param dataFile
     * @return
     * @throws URISyntaxException
     */
    public ResponseDetail post(String path, Map<String, String> mapPost, String dataFile, Map<String, Object> bodyMap, JsonAnalysis jsonAnalysis) throws URISyntaxException {
        responseDetail = sendRequest.postRequest(getUri(path), mapPost, dataFile, bodyMap, jsonAnalysis);
        return responseDetail;
    }

    /**
     * 修改请求体的Post
     *
     * @param path
     * @param dataFile
     * @return
     * @throws URISyntaxException
     */
    public ResponseDetail post(String path, String dataFile, Map<String, Object> bodyMap, JsonAnalysis jsonAnalysis) throws URISyntaxException {
        responseDetail = sendRequest.postRequest(getUri(path), null, dataFile, bodyMap, jsonAnalysis);
        return responseDetail;
    }

    /**
     * Get(带请求头)
     *
     * @param path
     * @param mapGet
     * @return
     * @throws URISyntaxException
     */
    public ResponseDetail get(String path, Map<String, String> mapGet) throws URISyntaxException {
        responseDetail = sendRequest.getRequest(getUri(path), mapGet);
        return responseDetail;
    }

    /**
     * Get
     *
     * @param path
     * @return
     * @throws URISyntaxException
     */
    public ResponseDetail get(String path) throws URISyntaxException {
        responseDetail = sendRequest.getRequest(getUri(path), null);
        return responseDetail;
    }
}
