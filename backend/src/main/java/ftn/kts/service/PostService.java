package ftn.kts.service;

import ftn.kts.dto.PostDTO;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.Post;
import ftn.kts.repository.CulturalOfferRepository;
import ftn.kts.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {

    private PostRepository postRepository;
    private CulturalOfferRepository cultRepository;
    //private PictureRepository picRepository;

    @Autowired
    public PostService(PostRepository postRepository, CulturalOfferRepository cultRepository) {
        this.postRepository = postRepository;
        this.cultRepository = cultRepository;
    }

    public List<PostDTO> getAllDTO() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> dtoList = new ArrayList<>();
        for (Post p : posts) {
            dtoList.add(toDTO(p));
        }
        return dtoList;
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
        CulturalOffer offer = cultRepository.getOne(dto.getCulturalOffer());
        post.setContent(dto.getContent());
        post.setCulturalOffer(offer);
        //TODO: pictures later!
    }

    private PostDTO toDTO(Post entity) {
        PostDTO dto = new PostDTO(entity.getId(), entity.getContent(), entity.getCulturalOffer().getId());
        return dto;
    }

    private Post toEntity(PostDTO dto) {
        CulturalOffer offer = cultRepository.getOne(dto.getCulturalOffer());
        Post post = new Post(dto.getId(), dto.getContent(), offer);
        return post;
    }


}
