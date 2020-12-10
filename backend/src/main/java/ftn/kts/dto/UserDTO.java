package ftn.kts.dto;

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
    private String password;

    public UserDTO() {}

    public UserDTO(Long id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
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
