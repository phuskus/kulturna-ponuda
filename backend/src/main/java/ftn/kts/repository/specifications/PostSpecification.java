package ftn.kts.repository.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import ftn.kts.model.Post;


public class PostSpecification implements Specification<Post> {
	
	private String query;
	
	public PostSpecification(String query) {
		this.query = query;
	}
	
	@Override
	public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate p = cb.disjunction();
		p.getExpressions().add(cb.notEqual(cb.locate(cb.lower(root.get("title")), this.query), 0));
		p.getExpressions().add(cb.notEqual(cb.locate(cb.lower(root.get("content")), this.query), 0));
		p.getExpressions().add(cb.notEqual(cb.locate(cb.lower(root.get("culturalOffer").get("name")), this.query), 0));			
		
		return p;
	}

}
