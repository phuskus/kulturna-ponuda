package ftn.kts.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("USER")
public class RegisteredUser extends User {
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Review> reviews;
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Subscription> subscriptions;
	
	public RegisteredUser() {
	}

	public RegisteredUser(String name, String username, String password) {
		super(name, username, password);
		this.reviews = new HashSet<>();
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public Set<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(Set<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}
}