package ftn.kts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDTO {

    private Long id;
    @NotBlank(message = "Name is required!")
    private String name;
    @NotBlank(message = "Username is required!")
    @Email(message = "Username should be your email address!")
    private String username;
    @NotBlank(message = "Password is required!")
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public UserDTO() {}

    public UserDTO(Long id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }
    
    public UserDTO(String name, String username, String password) {
    	this.name = name;
        this.username = username;
        this.password = password;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof UserDTO)) return false;

        UserDTO user = (UserDTO) obj;
        return user.getId().equals(this.id) && user.getUsername().equals(this.getUsername());
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
