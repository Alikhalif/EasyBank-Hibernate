package org.youcode.easybank_hibernate.servlets;

import org.youcode.easybank_hibernate.entities.Employee;
import org.youcode.easybank_hibernate.services.EmployeeService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "EmployeeServlet", value = "/employees")
public class EmployeeServlet extends HttpServlet {

    @Inject
    private EmployeeService employeeService;


    private void insertEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDateStr = request.getParameter("birthdate");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String recruitmentDateStr = request.getParameter("recruitmentDate");

        LocalDate birthDate = LocalDate.parse(birthDateStr);
        LocalDate recruitmentDate = LocalDate.parse(recruitmentDateStr);

        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setBirthDate(birthDate);
        employee.setPhone(phone);
        employee.setAddress(address);
        employee.setEmail(email);
        employee.setRecruitmentDate(recruitmentDate);

        try {
            employeeService.createEmployee(employee);
            response.sendRedirect(request.getContextPath() + "/employees");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("employeeId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDateStr = request.getParameter("birthdate");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String recruitmentDateStr = request.getParameter("recruitmentDate");

        LocalDate birthDate = LocalDate.parse(birthDateStr);
        LocalDate recruitmentDate = LocalDate.parse(recruitmentDateStr);

        Employee updatedEmployee = new Employee();
        updatedEmployee.setMatricule(id);
        updatedEmployee.setFirstName(firstName);
        updatedEmployee.setLastName(lastName);
        updatedEmployee.setBirthDate(birthDate);
        updatedEmployee.setPhone(phone);
        updatedEmployee.setAddress(address);
        updatedEmployee.setEmail(email);
        updatedEmployee.setRecruitmentDate(recruitmentDate);

        try {
            employeeService.updateEmployee(updatedEmployee);
            response.sendRedirect(request.getContextPath() + "/employees");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Employee> employees = employeeService.getAllEmployees();
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("/employees.jsp").forward(request, response);
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("employeeId"));
        boolean isDeleted = employeeService.deleteEmployee(id);
        if (isDeleted) {
            response.sendRedirect(request.getContextPath() + "/employees");
        }
    }

    private void findEmployeeByID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String matriculeParam = request.getParameter("matricule");
        if (matriculeParam != null && !matriculeParam.isEmpty()) {
            try {
                int matricule = Integer.parseInt(matriculeParam);
                Employee employee = employeeService.findEmployeeByID(matricule);
                if (employee != null) {
                    List<Employee> employees = new ArrayList<>();
                    employees.add(employee);
                    request.setAttribute("employees", employees);
                    request.getRequestDispatcher("/employees.jsp").forward(request, response);
                } else {
                    request.setAttribute("message", "No employee found with the given matricule.");
                    request.getRequestDispatcher("/employees.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid matricule format.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getEmployees(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "insert" :
                insertEmployee(request, response);
                break;
            case "update" :
                updateEmployee(request, response);
                break;
            case "delete" :
                deleteEmployee(request, response);
                break;
            case "search" :
                findEmployeeByID(request, response);
                break;
        }

    }

}