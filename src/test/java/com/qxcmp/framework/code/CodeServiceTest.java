package com.qxcmp.framework.code;

import com.qxcmp.framework.domain.Code;
import com.qxcmp.framework.domain.CodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeServiceTest {

    @Autowired
    private CodeService codeService;

    @Test
    public void testValidCode() throws Exception {
        Code code = codeService.nextActivateCode("");
        assertFalse(codeService.isInvalidCode(code.getId()));
    }

    @Test
    public void testActivateCode() throws Exception {
        for (int i = 0; i < 10; i++) {
            Code code = codeService.nextActivateCode("");
            assertEquals(32, code.getId().length());
            assertEquals(Code.Type.ACTIVATE, code.getType());
        }
    }

    @Test
    public void testPasswordCode() throws Exception {
        for (int i = 0; i < 10; i++) {
            Code code = codeService.nextPasswordCode("");
            assertEquals(32, code.getId().length());
            assertEquals(Code.Type.PASSWORD, code.getType());
        }
    }
}