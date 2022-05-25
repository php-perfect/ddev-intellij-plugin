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
import java.util.Scanner;

@Service(Service.Level.APP)
public final class JsonParser {

    public <T> @NotNull T parse(final String json, final Type typeOfT) throws JsonParserException {
        try (Scanner scanner = new Scanner(json)) {
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                final Result<T> result = parseJson(line, typeOfT);

                if (result != null && result.getRaw() != null) {
                    return result.getRaw();
                }
            }
        }

        throw new JsonParserException("Could not parse the ddev status output object");
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
