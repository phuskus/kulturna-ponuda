package ftn.kts.model;

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
	
	public RegisteredUser() {
		
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

}