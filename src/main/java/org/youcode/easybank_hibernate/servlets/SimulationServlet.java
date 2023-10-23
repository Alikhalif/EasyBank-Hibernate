package org.youcode.easybank_hibernate.servlets;

import org.youcode.easybank_hibernate.entities.*;
import org.youcode.easybank_hibernate.enums.STATE;
import org.youcode.easybank_hibernate.services.ClientService;
import org.youcode.easybank_hibernate.services.EmployeeService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.youcode.easybank_hibernate.services.RequestService;
import org.youcode.easybank_hibernate.services.SimulationService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "SimulationServlet", value = "/simulations")
public class SimulationServlet extends HttpServlet {

    @Inject
    private ClientService clientService;

    @Inject
    private EmployeeService employeeService;

    @Inject
    private SimulationService simulationService;

    @Inject
    private RequestService requestService;




    private void insertRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Double amount = Double.parseDouble(request.getParameter("amount"));
        Integer months = Integer.parseInt(request.getParameter("months"));
        Double monthly_payment = Double.parseDouble(request.getParameter("monthly_payment"));
        Integer employee_Id = Integer.parseInt(request.getParameter("employee"));
        Integer client_Id = Integer.parseInt(request.getParameter("client"));


        Simulation simulation = new Simulation();
        simulation.setBorrowed_capital(amount);
        simulation.setMonthly_payment_num(months);
        //result
        simulation.setMonthly_payment(monthly_payment);

        Client client = clientService.findClientByID(client_Id);
        simulation.setClient(client);

        Employee employee = employeeService.findEmployeeByID(employee_Id);
        simulation.setEmployee(employee);


        try {
            if(simulationService.createSimulation(simulation) != 0){
                Request request1 = new Request();
                request1.setCredit_date(LocalDate.now());
                request1.setAmount(simulation.getBorrowed_capital());
                request1.setState(STATE.PENDING);
                request1.setDuration(simulation.getMonthly_payment_num());
                request1.setClient(simulation.getClient());
                request1.setSimulation(simulation);

                requestService.createRequest(request1);
            }
            response.sendRedirect(request.getContextPath() + "/employees");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getEmployeesAndClients(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Client> clients = clientService.getAllClients();
        request.setAttribute("clients", clients);

        List<Employee> employees = employeeService.getAllEmployees();
        request.setAttribute("employees", employees);

        request.getRequestDispatcher("/simulation.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getEmployeesAndClients(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "insert" :
                try {
                    insertRequest(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "update" :
                //updateEmployee(request, response);
                break;
            case "delete" :
                //deleteEmployee(request, response);
                break;
            case "search" :
                //findEmployeeByID(request, response);
                break;
        }
    }
}