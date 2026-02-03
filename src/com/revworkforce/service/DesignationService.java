package com.revworkforce.service;

import com.revworkforce.dao.DesignationDAO;
import com.revworkforce.dao.impl.DesignationDAOImpl;
import com.revworkforce.model.Designation;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DesignationService {
    private static final Logger logger = LoggerFactory.getLogger(DesignationService.class);

    private DesignationDAO dao = new DesignationDAOImpl();

    public List<Designation> viewAllDesignations() {
        try {
            List<Designation> list = dao.getAllDesignations();
            logger.info("Viewed all designations, count={}", list.size());
            return list;
        } catch (Exception e) {
            logger.error("Error fetching all designations", e);
            return null;
        }
    }

    public Designation getDesignation(int id) {
        try {
            Designation des = dao.getDesignationById(id);
            if(des != null) {
                logger.info("Fetched designation: ID={} Name={}", des.getDesignationId(), des.getDesignationName());
            } else {
                logger.warn("Designation not found for ID={}", id);
            }
            return des;
        } catch (Exception e) {
            logger.error("Error fetching designation for ID={}", id, e);
            return null;
        }
    }
}