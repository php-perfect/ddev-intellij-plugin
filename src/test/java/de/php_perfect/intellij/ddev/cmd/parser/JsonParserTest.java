package de.php_perfect.intellij.ddev.cmd.parser;

import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.cmd.Versions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

final class JsonParserTest {
    @Test
    public void testParseValidJson() throws JsonParserException {
        TestObject expected = new TestObject();
        expected.foo = "Bar";

        String json = "{\n" + "    \"level\": \"info\",\n" + "    \"msg\": \"Abc\",\n" + "    \"raw\": {\n" + "        \"foo\": \"Bar\"\n" + "    },\n" + "    \"time\": \"2022-02-05T14:11:53+01:00\"\n" + "}";

        Assertions.assertEquals(expected, new JsonParser().parse(json, TestObject.class));
    }

    @Test
    public void testParseInvalidJson() {
        String json = "{]";

        Assertions.assertThrows(JsonParserException.class, () -> new JsonParser().parse(json, TestObject.class));
    }

    @Test
    public void testParseValidButEmptyJson() {
        String json = "{}";

        Assertions.assertThrows(JsonParserException.class, () -> new JsonParser().parse(json, TestObject.class));
    }

    @Test
    public void testParseValidJsonWithoutRawProperty() {
        String json = "{\n" + "    \"level\": \"info\",\n" + "    \"msg\": \"Abc\",\n" + "    \"time\": \"2022-02-05T14:11:53+01:00\"\n" + "}";

        Assertions.assertThrows(JsonParserException.class, () -> new JsonParser().parse(json, TestObject.class));
    }

    @Test
    public void parseStatusSuccessfully() throws JsonParserException, IOException {
        String json = Files.readString(Path.of("src/test/resources/ddev_describe.json"));
        Description actual = new JsonParser().parse(json, Description.class);

        Assertions.assertEquals("8.1", actual.getPhpVersion());
    }

    @Test
    public void parseVersionSuccessfully() throws JsonParserException, IOException {
        String json = Files.readString(Path.of("src/test/resources/ddev_version.json"));
        Versions actual = new JsonParser().parse(json, Versions.class);

        Assertions.assertEquals("v1.19.0", actual.getDdevVersion());
    }
}
