package ftn.kts.dto;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class RegisteredUserDTO {

    @NotNull(message = "Name is required!")
    private String name;
    @NotNull(message = "Username is required!")
    private String username;
    @NotNull(message = "Password is required!")
    private String password;

    private Set<ReviewDTO> reviews;
    private Set<SubscriptionDTO> subscriptions;

    public RegisteredUserDTO() {}

    public RegisteredUserDTO(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.reviews = new HashSet<>();
        this.subscriptions = new HashSet<>();
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

    public Set<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(Set<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

    public Set<SubscriptionDTO> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<SubscriptionDTO> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
