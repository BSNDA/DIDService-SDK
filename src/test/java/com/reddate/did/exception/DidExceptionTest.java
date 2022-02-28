package com.reddate.did.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.reddate.did.sdk.exception.DidException;

public class DidExceptionTest {
    @Test
    public void testConstructor() {
        DidException actualDidException = new DidException(1, "An error occurred");

        assertNull(actualDidException.getCause());
        assertEquals("com.did.exception.DidException: An error occurred", actualDidException.toString());
        assertEquals(0, actualDidException.getSuppressed().length);
        assertEquals("An error occurred", actualDidException.getMessage());
        assertEquals("An error occurred", actualDidException.getLocalizedMessage());
    }

    @Test
    public void testConstructor2() {
        DidException actualDidException = new DidException(9999,"An error occurred");
        assertNull(actualDidException.getCause());
        assertEquals("com.did.exception.DidException: An error occurred", actualDidException.toString());
        assertEquals(0, actualDidException.getSuppressed().length);
        assertEquals("An error occurred", actualDidException.getMessage());
        assertEquals("An error occurred", actualDidException.getLocalizedMessage());
    }
}

