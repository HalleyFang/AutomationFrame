package api.apidata.jsonanalysis;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.api.interfaceservice.JsonAnalysis;
import core.api.apiutils.GsonChange;

import java.util.Map;

public class MLoginJsonAnalysis implements JsonAnalysis {

    @Override
    public String analysisJson(String body, Map<String, Object> bodyMap, GsonChange gsonChange) {
        JsonObject body_tmp = gsonChange.jsonStrToJsonObject(body);
        JsonArray selectedRows = body_tmp.getAsJsonArray("selectedRows");
        JsonObject data = (JsonObject) selectedRows.get(0);
        for (Map.Entry<String, Object> entry : bodyMap.entrySet()) {
            if (entry.getValue() instanceof Number) {
                data.addProperty(entry.getKey(), (Number) entry.getValue());
            } else if (entry.getValue() instanceof Boolean) {
                data.addProperty(entry.getKey(), (Boolean) entry.getValue());
            } else {
                data.addProperty(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        body = gsonChange.jsonToString(body_tmp);
        return body;
    }
}
