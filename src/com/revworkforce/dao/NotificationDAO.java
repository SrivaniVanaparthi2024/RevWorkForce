package com.revworkforce.dao;

import com.revworkforce.model.Notification;
import com.revworkforce.exception.DatabaseException;

import java.util.List;

public interface NotificationDAO {

    List<Notification> getNotificationsByEmployeeId(int empId)
            throws DatabaseException;
}