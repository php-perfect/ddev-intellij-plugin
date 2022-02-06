package de.php_perfect.intellij.ddev.cmd.parser;

import java.util.Objects;

public class TestObject {
    public String foo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestObject that = (TestObject) o;
        return Objects.equals(foo, that.foo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foo);
    }
}
