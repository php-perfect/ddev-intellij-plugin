package de.php_perfect.intellij.ddev.cmd.parser;

import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.cmd.Versions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;

public class JsonParserTest {

    @Test
    public void testParseValidJson() {
        String json = "{\n" + "    \"level\": \"info\",\n" + "    \"msg\": \"Abc\",\n" + "    \"raw\": {\n" + "        \"foo\": \"Bar\"\n" + "    },\n" + "    \"time\": \"2022-02-05T14:11:53+01:00\"\n" + "}";

        TestObject expected = new TestObject();
        expected.foo = "Bar";

        Reader reader = new StringReader(json);
        TestObject actual = new JsonParser().parse(reader, TestObject.class);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testParseInvalidJson() {
        String json = "{]";
        Reader reader = new StringReader(json);

        Assertions.assertThrows(JsonParserException.class, () -> new JsonParser().parse(reader, TestObject.class));
    }

    @Test
    public void testParseValidButEmptyJson() {
        String json = "{}";
        Reader reader = new StringReader(json);

        Assertions.assertThrows(JsonParserException.class, () -> new JsonParser().parse(reader, TestObject.class));
    }

    @Test
    public void testParseValidJsonWithoutRawProperty() {
        String json = "{\n" + "    \"level\": \"info\",\n" + "    \"msg\": \"Abc\",\n" + "    \"time\": \"2022-02-05T14:11:53+01:00\"\n" + "}";
        Reader reader = new StringReader(json);
        Assertions.assertThrows(JsonParserException.class, () -> new JsonParser().parse(reader, TestObject.class));
    }

    @Test
    public void parseStatusSuccessfully() {
        Reader reader = null;
        try {
            reader = new FileReader("src/test/resources/ddev_describe.json");
        } catch (FileNotFoundException e) {
            Assertions.fail("Test file not found");
        }

        Description actual = new JsonParser().parse(reader, Description.class);

        Assertions.assertEquals("8.1", actual.getPhpVersion());
    }

    @Test
    public void parseVersionSuccessfully() {
        Reader reader = null;
        try {
            reader = new FileReader("src/test/resources/ddev_version.json");
        } catch (FileNotFoundException e) {
            Assertions.fail("Test file not found");
        }

        Versions actual = new JsonParser().parse(reader, Versions.class);

        Assertions.assertEquals("v1.19.0-alpha3-12-gdf44295f", actual.getDdevVersion());
    }
}
