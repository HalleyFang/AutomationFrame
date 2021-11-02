package core.api.interfaceservice;

import com.google.gson.JsonObject;
import core.api.apiutils.GsonChange;

import java.util.Map;

public interface JsonAnalysis {

    default String analysisJson(String body, Map<String,Object> bodyMap, GsonChange gsonChange){
        JsonObject body_tmp = gsonChange.jsonStrToJsonObject(body);
        for (Map.Entry<String, Object> entry : bodyMap.entrySet()) {
            if (entry.getValue() instanceof Number) {
                body_tmp.addProperty(entry.getKey(), (Number) entry.getValue());
            } else if (entry.getValue() instanceof Boolean) {
                body_tmp.addProperty(entry.getKey(), (Boolean) entry.getValue());
            } else {
                body_tmp.addProperty(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        body = gsonChange.jsonToString(body_tmp);
        return body;
    }
}
