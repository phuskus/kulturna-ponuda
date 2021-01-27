package ftn.kts.pages;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.databind.JsonNode;

import ftn.kts.dto.PostDTO;

public class PagePosts extends PageImpl<PostDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonCreator(mode = Mode.PROPERTIES)
	@JsonIgnoreProperties(ignoreUnknown = true)
	public PagePosts(@JsonProperty("content") List<PostDTO> content, @JsonProperty("number") int number, @JsonProperty("size") int size,
            @JsonProperty("totalElements") Long totalElements, @JsonProperty("pageable") JsonNode pageable, @JsonProperty("last") boolean last,
            @JsonProperty("totalPages") int totalPages, @JsonProperty("sort") JsonNode sort, @JsonProperty("first") boolean first,
            @JsonProperty("numberOfElements") int numberOfElements) {
		super(content, PageRequest.of(number, size), totalElements);
	}
	
}
