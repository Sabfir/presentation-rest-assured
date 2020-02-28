package com.adidas.presentationrestassured.service;

import com.adidas.presentationrestassured.dao.EmployeeRepository;
import com.adidas.presentationrestassured.dto.EmployeeDto;
import com.adidas.presentationrestassured.entity.Employee;
import com.adidas.presentationrestassured.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
  private EmployeeRepository employeeRepository;
  private EmployeeMapper employeeMapper;

  public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
    this.employeeRepository = employeeRepository;
    this.employeeMapper = employeeMapper;
  }

  public List<EmployeeDto> findAll() {
    List<Employee> employees = employeeRepository.findAll();
    return employeeMapper.toDto(employees);
  }

  public EmployeeDto findById(Long id) {
    Employee employee = employeeRepository.findById(id);
    return employeeMapper.toDto(employee);
  }

  public EmployeeDto create(EmployeeDto employeeDto) {
    Employee employee = employeeMapper.toEntity(employeeDto);
    Employee savedEmployee = employeeRepository.create(employee);
    return employeeMapper.toDto(savedEmployee);
  }

  public boolean delete(Long id) {
    return employeeRepository.delete(id);
  }
}
