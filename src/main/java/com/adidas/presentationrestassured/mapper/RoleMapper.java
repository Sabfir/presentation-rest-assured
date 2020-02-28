package com.adidas.presentationrestassured.mapper;

import com.adidas.presentationrestassured.dto.RoleDto;
import com.adidas.presentationrestassured.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper<RoleDto, Role> {
}
