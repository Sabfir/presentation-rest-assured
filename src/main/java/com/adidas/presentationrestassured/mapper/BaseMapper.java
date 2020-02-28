package com.adidas.presentationrestassured.mapper;

import org.mapstruct.InheritInverseConfiguration;

import java.util.List;

/**
 * Base dto mapper
 *
 * @param <Dto>    type of Dto
 * @param <Entity> type of Entity
 */
public interface BaseMapper<Dto, Entity> {

    Dto toDto(Entity entity);

    List<Dto> toDto(List<Entity> entities);

    @InheritInverseConfiguration
    Entity toEntity(Dto dto);

    List<Entity> toEntity(List<Dto> dtos);
}
