package ftn.kts.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AccountDTO {
	
	private Long id;
	@NotBlank(message = "Name is required!")
    private String name;
    @NotBlank(message = "Surname is required")
    private String surname;
    @NotBlank(message = "Username is required!")
    @Email(message = "Username should be your email address!")
    private String username;
    
    public AccountDTO() {}
    
	public AccountDTO(Long id,String name, String surname, String username) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.username = username;
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
	
}
