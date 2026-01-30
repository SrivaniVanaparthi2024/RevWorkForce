package com.revworkforce.service.test;

import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;
import com.revworkforce.model.Notification;
import com.revworkforce.service.NotificationService;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit / Integration test for NotificationService
 */
public class NotificationServiceTest {

    private NotificationService notificationService;

    @Before
    public void setUp() {
        notificationService = new NotificationService();
    }

    // ---------- VIEW NOTIFICATIONS SUCCESS ----------
//    @Test
//    public void testViewNotificationsSuccess()
//            throws InvalidInputException, DatabaseException {
//
//        List<Notification> list = notificationService.viewNotifications(1);
//
//        assertNotNull(list);
//    }

    // ---------- INVALID EMPLOYEE ID ----------
    @Test(expected = InvalidInputException.class)
    public void testViewNotificationsInvalidEmpId()
            throws InvalidInputException, DatabaseException {

        notificationService.viewNotifications(0);
    }

    // ---------- NEGATIVE EMPLOYEE ID ----------
    @Test(expected = InvalidInputException.class)
    public void testViewNotificationsNegativeEmpId()
            throws InvalidInputException, DatabaseException {

        notificationService.viewNotifications(-5);
    }
}