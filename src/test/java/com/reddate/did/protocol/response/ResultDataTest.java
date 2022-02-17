package com.reddate.did.protocol.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.reddate.did.sdk.protocol.response.ResultData;

public class ResultDataTest {
    @Test
    public void testSuccess() {
        ResultData<Object> actualSuccessResult = ResultData.<Object>success("Data");
        assertEquals(200, actualSuccessResult.getCode().intValue());
        assertEquals("success", actualSuccessResult.getMsg());
        assertEquals("Data", actualSuccessResult.getData());
    }

    @Test
    public void testConstructor() {
        ResultData<Object> actualResultData = new ResultData<Object>();
        actualResultData.setCode(1);
        actualResultData.setData("Data");
        actualResultData.setMsg("Msg");
        assertEquals(1, actualResultData.getCode().intValue());
        assertEquals("Msg", actualResultData.getMsg());
    }

    @Test
    public void testError() {
        ResultData<Object> actualErrorResult = ResultData.<Object>error(1, "Not all who wander are lost", Object.class);
        assertEquals(1, actualErrorResult.getCode().intValue());
        assertEquals("Not all who wander are lost", actualErrorResult.getMsg());
    }

    @Test
    public void testError2() {
        ResultData<Object> actualErrorResult = ResultData.<Object>error(1,"Not all who wander are lost", Object.class);
        assertEquals(400, actualErrorResult.getCode().intValue());
        assertEquals("Not all who wander are lost", actualErrorResult.getMsg());
    }

    @Test
    public void testIsSuccess() {
        ResultData<Object> resultData = new ResultData<Object>();
        resultData.setCode(0);
        assertFalse(resultData.isSuccess());
    }

    @Test
    public void testIsSuccess2() {
        assertTrue(ResultData.<Object>success("Data").isSuccess());
    }
}

