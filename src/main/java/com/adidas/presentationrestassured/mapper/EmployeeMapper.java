package com.adidas.presentationrestassured.mapper;

import com.adidas.presentationrestassured.dto.EmployeeDto;
import com.adidas.presentationrestassured.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface EmployeeMapper extends BaseMapper<EmployeeDto, Employee> {
}
