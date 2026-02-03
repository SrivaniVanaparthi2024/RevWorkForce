package com.revworkforce.dao;

import com.revworkforce.model.Attendance;
import java.util.List;

public interface AttendanceDAO {


    boolean addAttendance(Attendance attendance);

    List<Attendance> getAttendanceByEmpId(int empId);

    List<Attendance> getAllAttendance();
}