package com.revworkforce.service.test;

import com.revworkforce.dao.AttendanceDAO;
import com.revworkforce.model.Attendance;
import com.revworkforce.service.AttendanceService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AttendanceServiceTest {

    @Mock
    private AttendanceDAO attendanceDAO;

    @InjectMocks
    private AttendanceService attendanceService;

    private Attendance att1;
    private Attendance att2;

    @Before
    public void setUp() {
        att1 = new Attendance(1, 101, Date.valueOf("2026-01-01"), "PRESENT");
        att2 = new Attendance(2, 101, Date.valueOf("2026-01-02"), "ABSENT");
    }

    @Test
    public void testViewAttendanceForEmployee() {
        List<Attendance> mockList = Arrays.asList(att1, att2);

        when(attendanceDAO.getAttendanceByEmpId(101)).thenReturn(mockList);

        attendanceService.viewAttendanceForEmployee(101);

        verify(attendanceDAO, times(1)).getAttendanceByEmpId(101);
        assertEquals(2, mockList.size());
    }

    @Test
    public void testViewAttendanceForManager() {
        List<Attendance> mockList = Arrays.asList(att1, att2);

        when(attendanceDAO.getAllAttendance()).thenReturn(mockList);

        attendanceService.viewAttendanceForManager(5001);

        verify(attendanceDAO, times(1)).getAllAttendance();
        assertNotNull(mockList);
        assertEquals(2, mockList.size());
    }

    @Test
    public void testGetAttendanceDataIntegrity() {
        when(attendanceDAO.getAttendanceByEmpId(101))
                .thenReturn(Arrays.asList(att1));

        List<Attendance> list = attendanceDAO.getAttendanceByEmpId(101);

        Attendance a = list.get(0);
        assertEquals(101, a.getEmpId());
        assertEquals("PRESENT", a.getStatus());
        assertEquals(Date.valueOf("2026-01-01"), a.getDate());
    }
}