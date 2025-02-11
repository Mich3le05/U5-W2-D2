package it.epicode.blogging.post;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;
    private final PostMapper mapper;

    public List<PostResponse> findAll() {
        return mapper.toDto(repository.findAll());
    }

    public PostResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id " + id));
    }

    public PostResponse save(@Valid PostRequest request) {
        Post Post = mapper.toEntity(request);
        return mapper.toDto(repository.save(Post));
    }

    public PostResponse update(Long id, @Valid PostRequest request) {
        Post entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id " + id));
        return mapper.toDto(repository.save(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Post not found with id " + id);
        }
        repository.deleteById(id);
    }
}
