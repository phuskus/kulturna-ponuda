package ftn.kts.repository.specifications;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
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
	private String categoryName;
	private List<String> regionNames;
	private List<String> cityNames;
	
	public CulturalOfferSpecification(String query, String categoryName, List<String> regionNames, List<String> cityNames) {
		super();
		this.query = query.toLowerCase();
		this.categoryName = categoryName.toLowerCase();
		this.regionNames = regionNames;
		this.cityNames = cityNames;
	}
	
	@Override
	public Predicate toPredicate(Root<CulturalOffer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate p = cb.conjunction();
		if (!this.query.equals("")) {
			Predicate queryPredicate = cb.disjunction();
			queryPredicate.getExpressions().add(cb.notEqual(cb.locate(cb.lower(root.get("name")), this.query), 0));
			queryPredicate.getExpressions().add(cb.notEqual(cb.locate(cb.lower(root.get("description")), this.query), 0));
			queryPredicate.getExpressions().add(cb.notEqual(cb.locate(cb.lower(root.get("address")), this.query), 0));
			queryPredicate.getExpressions().add(cb.notEqual(cb.locate(cb.lower(root.get("city")), this.query), 0));
			queryPredicate.getExpressions().add(cb.notEqual(cb.locate(cb.lower(root.get("region")), this.query), 0));			
			p = cb.and(p, queryPredicate);
		}
		if (!this.categoryName.equals("")) {
			Predicate catPredicate = cb.disjunction();
			catPredicate.getExpressions().add(cb.notEqual(cb.locate(cb.lower(root.get("category").get("name")), this.categoryName), 0));
			p = cb.and(p, catPredicate);
		}
		if (this.regionNames.size() != 0) {
			Predicate regionPredicate = cb.disjunction();
			regionPredicate.getExpressions().add(cb.lower(root.get("region")).in(this.regionNames));
			p = cb.and(p, regionPredicate);
		}
		if (this.cityNames.size() != 0) {
			Predicate cityPredicate = cb.disjunction();
			cityPredicate.getExpressions().add(cb.lower(root.get("city")).in(this.cityNames));
			p = cb.and(p, cityPredicate);
		}
		return p;
	}
}
