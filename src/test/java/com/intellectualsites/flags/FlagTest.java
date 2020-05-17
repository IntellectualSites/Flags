package com.intellectualsites.flags;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlagTest {

    @BeforeAll public static void setupGlobalFlagContainer() {
        GlobalFlagContainer.getInstance().addFlag(new TestFlag(""));
    }

    @Test public void testGetFlag() {
        GlobalFlagContainer container = GlobalFlagContainer.getInstance();
        assertNotNull(container.getFlagClassFromString("test"));
        assertNotNull(container.getFlag(TestFlag.class));
        assertEquals(container.getFlag(TestFlag.class).getValue(), "");
        TestFlag testFlag = container.getFlag(TestFlag.class);
        assertDoesNotThrow(() -> {
           testFlag.parse("Hello World");
        });
        TestFlag newFlag = testFlag;
        try {
            newFlag = testFlag.parse("Hello World");
        } catch (final Exception ignored) { /* Covered above */ }
        assertEquals(newFlag.getValue(), "Hello World");
        container.addFlag(newFlag);
        assertEquals(container.getFlag(TestFlag.class).getValue(), "Hello World");
    }


}
