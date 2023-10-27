package org.youcode.easybank_hibernate.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.youcode.easybank_hibernate.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank_hibernate.entities.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeDaoImpl employeeDao;
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeService();
        employeeService.setEmployeeDao(employeeDao);
    }

    @Test
    public void testCreateEmployee(){
        Employee employee = new Employee();
        when(employeeDao.create(employee)).thenReturn(Optional.of(employee));

        try{
            Employee createEmployee = employeeService.createEmployee(employee);
            assertSame(employee, createEmployee);
        }catch (Exception e){
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testCreateEmployeeNull(){
        try{
            employeeService.createEmployee(null);
            fail("Exception should be thrown for a null employee");
        }catch (Exception e){
            assertEquals("Employee cannot be null", e.getMessage());
        }
    }

    @Test
    public void testFindEmployeeById(){
        Employee employee = new Employee();
        int id = 1;

        when(employeeDao.findByID(id)).thenReturn(Optional.of(employee));
        try{
            Employee findEmployee = employeeService.findEmployeeByID(id);
            assertSame(employee, findEmployee);
        }catch (Exception e){
            fail("Exception should not be thrown");
        }

        verify(employeeDao).findByID(id);
    }


    @Test
    public void testFindEmployeeByIdInvalid(){
        int id = -1;

        try{
            employeeService.findEmployeeByID(id);
            fail("Invalid ID exception");
        }catch (Exception e){
            assertEquals("Employee id cannot be empty or less than zero", e.getMessage());
        }

        verifyNoInteractions(employeeDao);
    }

    @Test
    public void testGetAllEmployee(){
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        employees.add(new Employee());

        when(employeeDao.getAll()).thenReturn(employees);
        List<Employee> employeeList = employeeService.getAllEmployees();

        assertEquals(employees, employeeList);
    }

    //@Test
    //public void testDeleteEmployee(){
        //int id = 1;
        //when(employeeDao.delete(id)).thenReturn(true);

        //Boolean result = employeeService.deleteEmployee(id);

        //assertTrue(result);
        //verify(employeeDao).delete(id);
    //}



}
