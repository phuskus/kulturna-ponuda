package ftn.kts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ftn.kts.model.Admin;
import ftn.kts.model.Category;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.Subcategory;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class AdminDTO {

    private Long id;
    @NotBlank(message = "Name is required!")
    private String name;
    @NotBlank(message = "Surname is required!")
    private String surname;
    @NotBlank(message = "Username is required!")
    private String username;
    @NotBlank(message = "Password is required!")
    private String password;


    public AdminDTO() {
    }

    public AdminDTO(String name, String surname, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
    }

    public AdminDTO(Long id, String name, String surname, String username, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof AdminDTO)) return false;

        AdminDTO admin = (AdminDTO) obj;
        return this.name.equals(admin.getName()) && this.surname.equals(admin.getSurname()) && this.username.equals(admin.getUsername());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
