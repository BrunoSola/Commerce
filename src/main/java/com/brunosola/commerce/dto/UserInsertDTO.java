package com.brunosola.commerce.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserInsertDTO extends UserDTO {

    @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=!]).{8,20}$",
            message = "A senha deve conter ao menos uma letra minúscula, um número e um caractere especial (@#$%^&+=!)"
    )
    private String password;

    public UserInsertDTO(){
        super();
    }

    public UserInsertDTO(String password) {
        super();
        this.password = password;
    }

    public String getPassword() {        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
