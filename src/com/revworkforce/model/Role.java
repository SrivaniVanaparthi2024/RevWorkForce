package com.revworkforce.model;



public class Role {
    private int roleId;
    private String roleName;
    private String description;

    public Role(int roleId, String roleName, String description) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.description = description;
    }

    public int getRoleId() { return roleId; }
    public String getRoleName() { return roleName; }
    public String getDescription() { return description; }

    public void setRoleName(String roleName) { this.roleName = roleName; }
    public void setDescription(String description) { this.description = description; }
}

