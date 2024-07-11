package com.ibm3.model.role_dto;


public class RoleDto {

	
	private int id;
	
	private String roleName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "RoleDto [id=" + id + ", roleName=" + roleName + "]";
	}
	
	
}
