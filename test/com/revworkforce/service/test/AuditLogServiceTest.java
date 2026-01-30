package com.revworkforce.service.test;

import com.revworkforce.dao.AuditLogDAO;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.model.AuditLog;
import com.revworkforce.service.AuditLogService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AuditLogServiceTest {

    @Mock
    private AuditLogDAO auditLogDAO;

    @InjectMocks
    private AuditLogService auditLogService;

    private AuditLog log1;
    private AuditLog log2;

    @Before
    public void setUp() {
        log1 = new AuditLog();
        log1.setEmpId(101);
        log1.setAction("LOGIN");
        log1.setModule("AUTH");

        log2 = new AuditLog();
        log2.setEmpId(102);
        log2.setAction("CREATE_EMPLOYEE");
        log2.setModule("ADMIN");
    }

    @Test
    public void testRecordAction() throws DatabaseException {
        // DAO method is void → use doNothing
        doNothing().when(auditLogDAO).logAction(any(AuditLog.class));

        auditLogService.recordAction(101, "LOGIN", "AUTH");

        // Verify DAO interaction
        verify(auditLogDAO, times(1)).logAction(any(AuditLog.class));
    }

    @Test
    public void testViewAuditLogs() throws DatabaseException {
        List<AuditLog> mockLogs = Arrays.asList(log1, log2);

        when(auditLogDAO.getLogs()).thenReturn(mockLogs);

        List<AuditLog> result = auditLogService.viewAuditLogs();

        verify(auditLogDAO, times(1)).getLogs();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testAuditLogDataIntegrity() throws DatabaseException {
        when(auditLogDAO.getLogs()).thenReturn(Arrays.asList(log1));

        List<AuditLog> result = auditLogService.viewAuditLogs();
        AuditLog log = result.get(0);

        assertEquals(101, log.getEmpId());
        assertEquals("LOGIN", log.getAction());
        assertEquals("AUTH", log.getModule());
    }

    @Test(expected = DatabaseException.class)
    public void testViewAuditLogsThrowsException() throws DatabaseException {
        when(auditLogDAO.getLogs()).thenThrow(new DatabaseException("DB error"));

        auditLogService.viewAuditLogs();
    }
}
