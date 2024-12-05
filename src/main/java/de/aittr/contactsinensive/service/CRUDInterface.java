package de.aittr.contactsinensive.service;

import java.util.Optional;

public interface CRUDInterface<E, CD, UD, RD> {


    RD getOrThrowById(long id);

    Optional<E> findEntityById(long id);

    E findEntityOrThrowById(long id);

    RD create(CD createDto);

    E save(E entity);

    RD update(long contactId, UD updateDto);

    RD delete(long id);
}
