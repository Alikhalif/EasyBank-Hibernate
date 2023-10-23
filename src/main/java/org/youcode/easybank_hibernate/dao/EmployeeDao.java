package org.youcode.easybank_hibernate.dao;

import org.youcode.easybank_hibernate.entities.Client;
import org.youcode.easybank_hibernate.entities.Employee;

import java.util.List;

public interface EmployeeDao extends IData<Employee, Integer>{

    public List<Client> findByAnyAttribute(String attribute);

}