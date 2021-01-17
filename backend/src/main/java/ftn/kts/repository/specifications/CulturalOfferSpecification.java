package ftn.kts.repository.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import ftn.kts.model.CulturalOffer;

public class CulturalOfferSpecification implements Specification<CulturalOffer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String query;
	
	public CulturalOfferSpecification(String query) {
		super();
		this.query = query.toLowerCase();
	}
	
	@Override
	public Predicate toPredicate(Root<CulturalOffer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate p = cb.disjunction();
		cb.locate(cb.lower(root.get("name")), this.query);
		p.getExpressions().add(cb.notEqual(cb.locate(cb.lower(root.get("name")), this.query), 0));
		p.getExpressions().add(cb.notEqual(cb.locate(cb.lower(root.get("description")), this.query), 0));
		p.getExpressions().add(cb.notEqual(cb.locate(cb.lower(root.get("address")), this.query), 0));
		p.getExpressions().add(cb.notEqual(cb.locate(cb.lower(root.get("city")), this.query), 0));
		p.getExpressions().add(cb.notEqual(cb.locate(cb.lower(root.get("region")), this.query), 0));
		return p;
	}
}
