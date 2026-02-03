package com.revworkforce.dao.impl;

import com.revworkforce.model.Holiday;
import com.revworkforce.util.DBUtil;
import com.revworkforce.dao.HolidayDAO;
import com.revworkforce.exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HolidayDAOImpl implements HolidayDAO{

    public List<Holiday> getAllHolidays() throws DatabaseException {

        List<Holiday> list = new ArrayList<Holiday>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
            "SELECT holiday_id, holiday_date, holiday_name, description " +
            "FROM holiday ORDER BY holiday_date";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Holiday h = new Holiday();
                h.setHolidayId(rs.getInt("holiday_id"));
                h.setHolidayDate(rs.getDate("holiday_date"));
                h.setHolidayName(rs.getString("holiday_name"));
                h.setDescription(rs.getString("description"));
                list.add(h);
            }

        } catch (Exception e) {
            throw new DatabaseException("Error fetching holidays", e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return list;
    }
}