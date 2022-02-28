package com.reddate.did.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import com.reddate.did.sdk.util.Signatures;

public class SignaturesTest {
    @Test
    public void testGet() {
        assertEquals("nullnullnull", Signatures.get().getSignStr());
    }

    @Test
    public void testSetInfo() {
        Signatures getResult = Signatures.get();
        Signatures actualSetInfoResult = getResult.setInfo("myproject", "Did");
        assertSame(getResult, actualSetInfoResult);
        assertEquals("ABC123myprojectDid", actualSetInfoResult.getSignStr());
    }

    @Test
    public void testSetInfo2() {
        assertThrows(RuntimeException.class, () -> Signatures.get().setInfo( "myproject", "Did"));
    }

    @Test
    public void testSetInfo3() {
        assertThrows(RuntimeException.class, () -> Signatures.get().setInfo("myproject", "Did"));
    }

    @Test
    public void testSetInfo4() {
        assertThrows(RuntimeException.class, () -> Signatures.get().setInfo(null, "Did"));
    }

    @Test
    public void testSetInfo5() {
        assertThrows(RuntimeException.class, () -> Signatures.get().setInfo("", "Did"));
    }

    @Test
    public void testSetInfo6() {
        assertThrows(RuntimeException.class, () -> Signatures.get().setInfo("myproject", null));
    }

    @Test
    public void testSetInfo7() {
        assertThrows(RuntimeException.class, () -> Signatures.get().setInfo("myproject", ""));
    }

    @Test
    public void testAdd() {
        Signatures getResult = Signatures.get();
        assertSame(getResult, getResult.add("Name", "Value"));
    }

    @Test
    public void testAdd2() {
        assertThrows(RuntimeException.class, () -> Signatures.get().add(null, "Value"));
    }

    @Test
    public void testAdd3() {
        assertThrows(RuntimeException.class, () -> Signatures.get().add("", "Value"));
    }

    @Test
    public void testAdd4() {
        Signatures getResult = Signatures.get();
        assertSame(getResult, getResult.add("Name", null));
    }

    @Test
    public void testSign() {
        assertThrows(RuntimeException.class, () -> Signatures.get().sign("Private Key"));
        assertThrows(RuntimeException.class, () -> Signatures.get().sign(null));
        assertEquals("ZnCDCpDh0/PejxQvEH8e7dR2G4xKYVTh5xrQdo265m9O7gWj52SEa0pk59NZiCZAxNF86a5Js0R9siIsSLv0cwA=",
                Signatures.get().sign("42"));
        assertThrows(RuntimeException.class, () -> Signatures.get().sign(""));
    }

    @Test
    public void testSign2() {
        Signatures getResult = Signatures.get();
        getResult.add("sign string =", "Value");
        assertThrows(RuntimeException.class, () -> getResult.sign("Private Key"));
    }

    @Test
    public void testSign3() {
        Signatures getResult = Signatures.get();
        getResult.add("sign string =", "Value");
        assertEquals("9KgsyB87v8G5gndNgMDQk2q5nD/BSMYmQxu0VxNqTbBbk+ug+qKFELked2SjQxc3SXmXFWmsSzjIwJzXS1pEsgE=",
                getResult.sign("42"));
    }

    @Test
    public void testVerify() {
        assertThrows(RuntimeException.class, () -> Signatures.get().verify("Public Key", "42"));
        assertThrows(RuntimeException.class, () -> Signatures.get().verify(null, "42"));
        assertThrows(RuntimeException.class, () -> Signatures.get().verify("", "42"));
        assertThrows(RuntimeException.class, () -> Signatures.get().verify("Public Key", null));
        assertThrows(RuntimeException.class, () -> Signatures.get().verify("Public Key", ""));
    }

    @Test
    public void testVerify2() {
        Signatures getResult = Signatures.get();
        getResult.add("sign string =", "Value");
        assertThrows(RuntimeException.class, () -> getResult.verify("Public Key", "42"));
    }

    @Test
    public void testGetSignStr() {
        assertEquals("nullnullnull", Signatures.get().getSignStr());
    }

    @Test
    public void testGetSignStr2() {
        Signatures getResult = Signatures.get();
        getResult.add("sign string =", "Value");
        assertEquals("nullnullnull\"Value\"", getResult.getSignStr());
    }
}

