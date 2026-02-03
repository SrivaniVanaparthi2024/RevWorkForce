package com.revworkforce.dao;

import com.revworkforce.model.Designation;
import java.util.List;

public interface DesignationDAO {

    List<Designation> getAllDesignations();

    Designation getDesignationById(int id);
}