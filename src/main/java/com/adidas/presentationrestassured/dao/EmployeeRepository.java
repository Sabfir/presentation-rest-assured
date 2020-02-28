package com.adidas.presentationrestassured.dao;

import com.adidas.presentationrestassured.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EmployeeRepository {
  private AtomicLong idSequence = new AtomicLong(0);
  private Map<Long, Employee> db = new HashMap<>();

  public List<Employee> findAll() {
    return new ArrayList<>(db.values());
  }

  public Employee findById(Long id) {
    return db.get(id);
  }

  public Employee create(Employee employee) {
    Long id = idSequence.incrementAndGet();
    employee.setId(id);
    db.putIfAbsent(id, employee);
    return employee;
  }

  public boolean delete(Long id) {
    return db.remove(id) != null;
  }
}
