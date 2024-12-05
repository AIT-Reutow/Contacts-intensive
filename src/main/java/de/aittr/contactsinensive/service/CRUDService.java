package de.aittr.contactsinensive.service;

import de.aittr.contactsinensive.entity.BaseEntity;
import de.aittr.contactsinensive.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public abstract class CRUDService<E extends BaseEntity, CD, UD, RD> implements CRUDInterface<E, CD, UD, RD> {

    protected JpaRepository<E, Long> repository;
    protected EntityMapper<E, CD, UD, RD> entityMapper;

    protected CRUDService(JpaRepository<E, Long> repository, EntityMapper<E, CD, UD, RD> mapper) {
        this.repository = repository;
        this.entityMapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public RD getOrThrowById(long id) {
        return entityMapper.formEntityToReadDto(findEntityOrThrowById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<E> findEntityById(long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public E findEntityOrThrowById(long id) {
        return findEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity with id " + id + " not found"));
    }

    @Override
    @Transactional()
    public RD create(CD createDto) {
        E entityToSave = entityMapper.formCreateDtoToEntity(createDto);
        return entityMapper.formEntityToReadDto(save(entityToSave));
    }

    @Override
    public E save(E entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional()
    public RD update(long id, UD updateDto) {
        E entityToSave = entityMapper.formUpdateToEntity(updateDto);
        entityToSave.setId(id);
        return entityMapper.formEntityToReadDto(repository.save(entityToSave));
    }

    @Override
    @Transactional()
    public RD delete(long id) {
        E foundEntity = findEntityOrThrowById(id);
        repository.delete(foundEntity);
        return entityMapper.formEntityToReadDto(foundEntity);
    }
}
