package ftn.kts.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ResetPasswordDTO {
		
    @NotBlank(message = "Password is required!")
    @Size(min = 5, message = "Password must be at least 5 characters!")
    private String newPassword;
    
    @NotBlank(message = "Reset key is required!")
    private String resetKey;
    
    public ResetPasswordDTO() {}

	public ResetPasswordDTO(String newPassword, String resetKey) {
		this.newPassword = newPassword;
		this.resetKey = resetKey;
	}

	public String getNewPassword() {
		return this.newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getResetKey() {
		return this.resetKey;
	}

	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}

}
