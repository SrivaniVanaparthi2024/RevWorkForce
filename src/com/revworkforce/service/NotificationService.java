package com.revworkforce.service;

import com.revworkforce.dao.NotificationDAO;
import com.revworkforce.dao.impl.NotificationDAOImpl;
import com.revworkforce.model.Notification;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;

import java.util.List;

public class NotificationService {

    private NotificationDAO notificationDAO = new NotificationDAOImpl();

    public List<Notification> viewNotifications(int empId) throws InvalidInputException, DatabaseException {
        if (empId <= 0) {
            throw new InvalidInputException("Invalid employee id");
        }

        return notificationDAO.getNotificationsByEmployeeId(empId);
    }
}
