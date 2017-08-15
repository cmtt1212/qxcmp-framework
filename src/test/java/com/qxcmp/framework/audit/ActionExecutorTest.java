package com.qxcmp.framework.audit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionExecutorTest {

    @Autowired
    private ActionExecutor actionExecutor;

    @Test
    public void testNormalAction() throws Exception {
        Optional<AuditLog> auditLog = actionExecutor.execute("testAction", "/", "", null, context -> {
            System.out.println("Test Action Step");
        });

        assertTrue(auditLog.isPresent());
        assertEquals("testAction", auditLog.get().getTitle());
        assertEquals(AuditLog.Status.SUCCESS, auditLog.get().getStatus());
    }

    @Test
    public void testFailedAction() throws Exception {
        Optional<AuditLog> actionHistory = actionExecutor.execute("testAction2", "/", "", null, context -> {
            throw new ActionException("Action Exception");
        });

        assertTrue(actionHistory.isPresent());
        assertEquals("testAction2", actionHistory.get().getTitle());
        assertEquals(AuditLog.Status.FAILURE, actionHistory.get().getStatus());
    }
}