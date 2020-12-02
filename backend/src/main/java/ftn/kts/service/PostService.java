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

    public List<PostDTO> getAll() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> dtoList = new ArrayList<>();
        for (Post p : posts) {
            dtoList.add(toDTO(p));
        }
        return dtoList;
    }

    public PostDTO getOne(long id) {
        Post post = postRepository.findById(id).get();
        PostDTO dto = toDTO(post);
        return dto;
    }

    public void create(PostDTO dto) {
        Post post = toEntity(dto);
        postRepository.save(post);
    }

    public PostDTO update(PostDTO dto, Long id) {
        Post post = postRepository.findById(id).get();
        updatePost(post, dto);
        postRepository.save(post);
        return toDTO(post);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
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
