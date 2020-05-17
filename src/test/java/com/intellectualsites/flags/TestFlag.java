package com.intellectualsites.flags;

import org.jetbrains.annotations.NotNull;

public class TestFlag extends AbstractFlag<String, TestFlag> {

    public TestFlag(String value) {
        super(value);
    }

    @Override public TestFlag parse(@NotNull String input) throws FlagParseException {
        return flagOf(input);
    }

    @Override public TestFlag merge(@NotNull String newValue) {
        return flagOf(this.getValue() + newValue);
    }

    @Override public String toString() {
        return this.getValue();
    }

    @Override public String getExample() {
        return "";
    }

    @Override protected TestFlag flagOf(@NotNull String value) {
        return new TestFlag(value);
    }
}
