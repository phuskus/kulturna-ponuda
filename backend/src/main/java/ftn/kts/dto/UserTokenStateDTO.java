package ftn.kts.dto;

import org.aspectj.bridge.IMessage;

import javax.validation.constraints.NotBlank;

public class UserTokenStateDTO {

    @NotBlank(message = "Access token is required!")
    private String accessToken;
    private Long expiresIn;
    @NotBlank(message = "User role is required!")
    private String userRole;

    public UserTokenStateDTO() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public UserTokenStateDTO(String accessToken, long expiresIn, String role) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.setUserRole(role);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
