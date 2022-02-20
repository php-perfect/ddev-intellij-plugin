package de.php_perfect.intellij.ddev.cmd.parser;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

@Service(Service.Level.APP)
public final class JsonParser {
    @NotNull
    public <T> T parse(String json, Type typeOfT) throws JsonParserException {
        Result<T> result = parseJson(json, typeOfT);

        if (result == null) {
            throw new JsonParserException("Could not parse the ddev status output object");
        }

        T data = result.getRaw();

        if (data == null) {
            throw new JsonParserException("Could not parse the required type from output");
        }

        return data;
    }

    @Nullable
    private <T> Result<T> parseJson(String json, Type typeOfT) throws JsonParserException {
        Type typeToken = TypeToken.getParameterized(Result.class, typeOfT).getType();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        try {
            return gson.fromJson(json, typeToken);
        } catch (JsonSyntaxException exception) {
            throw new JsonParserException(String.format("Encountered invalid JSON '%s'", json), exception);
        }
    }

    @NotNull
    public static JsonParser getInstance() {
        return ApplicationManager.getApplication().getService(JsonParser.class);
    }
}
