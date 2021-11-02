package core.api.apiutils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

@Component
public class GsonChange {
    public Gson getGson() {
        return gson;
    }

    private Gson gson = new Gson();

    public String jsonToString(JsonObject jsonObject) {
        return gson.toJson(jsonObject);
    }

    public JsonObject jsonStrToJsonObject(String jsonStr) {
        return gson.fromJson(jsonStr, JsonObject.class);
    }
}
