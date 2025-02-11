package it.epicode.blogging.autore;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class AutoreService {
    private final AutoreRepository repository;
    private final AutoreMapper mapper;

    public List<AutoreResponse> findAll() {
        return mapper.toDto(repository.findAll());
    }

    public AutoreResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Autore not found with id " + id));
    }

    public AutoreResponse save(@Valid AutoreRequest request) {
        Autore Autore = mapper.toEntity(request);
        return mapper.toDto(repository.save(Autore));
    }

    public AutoreResponse update(Long id, @Valid AutoreRequest request) {
        Autore entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autore not found with id " + id));
        return mapper.toDto(repository.save(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Autore not found with id " + id);
        }
        repository.deleteById(id);
    }
}
