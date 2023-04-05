package de.php_perfect.intellij.ddev.cmd.parser;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.Scanner;

public final class JsonParserImpl implements JsonParser {

    public <T> @NotNull T parse(final String json, final Type typeOfT) throws JsonParserException {
        try (Scanner scanner = new Scanner(json)) {
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                final LogLine<T> logLine = parseJson(line, typeOfT);

                if (logLine != null && logLine.raw() != null) {
                    return logLine.raw();
                }
            }
        }

        throw new JsonParserException("Could not parse the ddev describe output");
    }

    private <T> @Nullable LogLine<T> parseJson(final String json, final Type typeOfT) throws JsonParserException {
        final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Type typeToken = TypeToken.getParameterized(LogLine.class, typeOfT).getType();

        try {
            return gson.fromJson(json, typeToken);
        } catch (JsonSyntaxException exception) {
            throw new JsonParserException(String.format("Encountered invalid JSON '%s'", json), exception);
        }
    }
}
