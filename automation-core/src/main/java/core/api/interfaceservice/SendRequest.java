package core.api.interfaceservice;

import core.api.apiutils.GsonChange;
import core.api.apiutils.ReadFile;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * 发送http请求
 */
@Service
public class SendRequest {

    public ResponseDetail postRequest(URI uri, Map<String, String> mapPost, String dataFilePath, Map<String, Object> bodyMap, JsonAnalysis jsonAnalysis) throws URISyntaxException {
        ReadFile readFile = new ReadFile();
        GsonChange gsonChange = new GsonChange();
        DoHttp doHttp = new DoHttp();
        if (uri == null) {
            return null;
        }
        doHttp.setUrl(uri);
        if (mapPost != null && !mapPost.isEmpty()) {
            doHttp.setMapPost(mapPost);
        }
        if (dataFilePath != null) {
//            File file = new File(dataFilePath);
            String body = readFile.readFile(dataFilePath);
            if (bodyMap != null && !bodyMap.isEmpty()) {
                if (jsonAnalysis instanceof JsonAnalysis) {
                    body = jsonAnalysis.analysisJson(body, bodyMap, gsonChange);
                }
            }
            doHttp.setRequestBody(body);
        }
        ResponseDetail responseDetail = doHttp.doPost();
        return responseDetail;
    }

    public ResponseDetail postRequest(URI uri, Map<String, String> mapPost, String body) {
        DoHttp doHttp = new DoHttp();
        if (uri == null) {
            return null;
        }
        doHttp.setUrl(uri);
        if (mapPost != null && !mapPost.isEmpty()) {
            doHttp.setMapPost(mapPost);
        }
        if (body != null) {
            doHttp.setRequestBody(body);
        }
        ResponseDetail responseDetail = doHttp.doPost();
        return responseDetail;
    }

    public ResponseDetail getRequest(URI uri, Map<String, String> mapGet) {
        DoHttp doHttp = new DoHttp();
        if (uri == null) {
            return null;
        }
        doHttp.setUrl(uri);
        if (mapGet != null && !mapGet.isEmpty()) {
            doHttp.setMapGet(mapGet);
        }
        ResponseDetail responseDetail = doHttp.doGet();
        return responseDetail;
    }
}
