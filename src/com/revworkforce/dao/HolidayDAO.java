package com.revworkforce.dao;

import com.revworkforce.exception.DatabaseException;
import com.revworkforce.model.Holiday;
import java.util.List;

public interface HolidayDAO {

    List<Holiday> getAllHolidays() throws DatabaseException;
}