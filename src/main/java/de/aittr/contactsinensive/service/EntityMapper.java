package de.aittr.contactsinensive.service;

public interface EntityMapper<E, CD, UD, RD> {

    E formCreateDtoToEntity(CD createDto);

    E formUpdateToEntity(UD updateDto);

    RD formEntityToReadDto(E entity);
}
