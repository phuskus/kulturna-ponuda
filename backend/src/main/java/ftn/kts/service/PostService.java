package ftn.kts.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ftn.kts.dto.PostDTO;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.Post;
import ftn.kts.repository.PostRepository;

@Service
public class PostService {

    private PostRepository postRepository;
    private CulturalOfferService cultService;
    private PictureService pictureService;

    @Autowired
    public PostService(PostRepository postRepository, CulturalOfferService cultService, PictureService picService) {
        this.postRepository = postRepository;
        this.cultService = cultService;
        this.pictureService = picService;
    }

    public Page<PostDTO> getAllDTO(Pageable pageable) {
        return postRepository.findAll(pageable).map(this::toDTO);
    }

    public PostDTO getOneDTO(long id) {
        Post post = getOne(id);
        PostDTO dto = toDTO(post);
        return dto;
    }

    public void create(PostDTO dto) {
        Post post = toEntity(dto);
        postRepository.save(post);
    }

    public PostDTO update(PostDTO dto, Long id) {
        Post post = getOne(id);
        updatePost(post, dto);
        postRepository.save(post);
        return toDTO(post);
    }

    public void delete(Long id) {
        Post post = getOne(id);
        postRepository.delete(post);
    }
    
	public Post getOne(long id) {
		return postRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Post with id " + id + " doesn't exist!"));
	}

	public List<Post> getAll(long id) {
		return postRepository.findAll();
	}

    private void updatePost(Post post, PostDTO dto) {
        CulturalOffer offer = cultService.getOne(dto.getCulturalOffer());
        post.setContent(dto.getContent());
        post.setCulturalOffer(offer);
        //TODO: pictures later!
    }

    private PostDTO toDTO(Post entity) {
        PostDTO dto = new PostDTO(entity.getId(), entity.getContent(), entity.getCulturalOffer().getId());
        dto.setPictures(pictureService.convertToDTO(entity.getPictures()));
        return dto;
    }

    public Set<PostDTO> convertToDTO(Set<Post> posts) {
        return posts.stream().map(this::toDTO).collect(Collectors.toSet());
    }

    private Post toEntity(PostDTO dto) {
        CulturalOffer offer = cultService.getOne(dto.getCulturalOffer());
        Post post = new Post(dto.getId(), dto.getContent(), offer);
        return post;
    }

}
