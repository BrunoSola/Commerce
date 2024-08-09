package com.brunosola.commerce.dto;

import com.brunosola.commerce.entities.Role;

public class RoleDTO {

    private Long id;
    private String authority;

    RoleDTO(){}

    RoleDTO(Long id, String authority){
        this.id = id;
        this.authority = authority;
    }

    RoleDTO(Role entity){
        id = entity.getId();
        authority = entity.getAuthority();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
