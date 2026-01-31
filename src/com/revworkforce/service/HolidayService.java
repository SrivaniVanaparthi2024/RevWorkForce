package com.revworkforce.service;

import com.revworkforce.dao.HolidayDAO;
import com.revworkforce.model.Holiday;
import com.revworkforce.exception.DatabaseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HolidayService {

    private static final Logger logger = LoggerFactory.getLogger(HolidayService.class);

    private HolidayDAO holidayDAO = new HolidayDAO();

    // Called directly from Admin / Manager / Employee menus
    public void viewHolidays() {

        try {
            List<Holiday> holidays = holidayDAO.getAllHolidays();
            logger.info("Fetching all holidays, count={}", (holidays != null ? holidays.size() : 0));

            if (holidays == null || holidays.isEmpty()) {
                System.out.println("⚠ No holidays available");
                logger.info("No holidays found");
                return;
            }

            System.out.println("\nID | DATE | NAME | DESCRIPTION");
            for (Holiday h : holidays) {
                System.out.println(
                    h.getHolidayId() + " | " +
                    h.getHolidayDate() + " | " +
                    h.getHolidayName() + " | " +
                    h.getDescription()
                );
                logger.info("Holiday displayed: ID={} Name={} Date={}", h.getHolidayId(), h.getHolidayName(), h.getHolidayDate());
            }

        } catch (DatabaseException e) {
            System.out.println("❌ Unable to fetch holidays due to database issue");
            logger.error("DatabaseException while fetching holidays: ", e);
        } catch (Exception e) {
            System.out.println("❌ An unexpected error occurred while fetching holidays");
            logger.error("Exception in viewHolidays(): ", e);
        }
    }
}