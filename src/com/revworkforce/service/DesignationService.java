package com.revworkforce.service;

import com.revworkforce.dao.DesignationDAO;
import com.revworkforce.model.Designation;
import java.util.List;

public class DesignationService {
    private DesignationDAO dao = new DesignationDAO();

    public List<Designation> viewAllDesignations() {
        return dao.getAllDesignations();
    }

    public Designation getDesignation(int id) {
        return dao.getDesignationById(id);
    }
}
