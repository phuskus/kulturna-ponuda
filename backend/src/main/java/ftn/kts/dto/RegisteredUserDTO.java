package ftn.kts.dto;

import javax.validation.constraints.NotNull;

public class RegisteredUserDTO {

    @NotNull(message = "Name is required!")
    private String name;
    @NotNull(message = "Username is required!")
    private String username;
    @NotNull(message = "Password is required!")
    private String password;
    //private List<ReviewDTO> reviews;

    public RegisteredUserDTO() {}

    public RegisteredUserDTO(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
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
