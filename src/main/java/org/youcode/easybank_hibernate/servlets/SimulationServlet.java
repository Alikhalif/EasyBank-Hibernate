package org.youcode.easybank_hibernate.servlets;

import lombok.SneakyThrows;
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


    private void insertRequests(HttpServletRequest request, HttpServletResponse response) throws Exception {
            String amountParam = request.getParameter("amount");
            String monthsParam = request.getParameter("months");
            String monthlyPaymentParam = request.getParameter("monthly_payment");
            String employeeIdParam = request.getParameter("employee");
            String clientIdParam = request.getParameter("client");

            double amount = 0;
            if (amountParam != null && !amountParam.isEmpty()) {
                try {
                    amount = Double.parseDouble(amountParam);
                } catch (NumberFormatException e) {
                }
            }

            int months = 0;
            if (monthsParam != null && !monthsParam.isEmpty()) {
                try {
                    months = Integer.parseInt(monthsParam);
                } catch (NumberFormatException e) {
                }
            }

            double monthly_payment = 0;
            if (monthlyPaymentParam != null && !monthlyPaymentParam.isEmpty()) {
                try {
                    monthly_payment = Double.parseDouble(monthlyPaymentParam);
                } catch (NumberFormatException e) {
                }
            }

            int employee_Id = 0;
            if (employeeIdParam != null && !employeeIdParam.isEmpty()) {
                try {
                    employee_Id = Integer.parseInt(employeeIdParam);
                } catch (NumberFormatException e) {
                }
            }

            int client_Id = 0;
            if (clientIdParam != null && !clientIdParam.isEmpty()) {
                try {
                    client_Id = Integer.parseInt(clientIdParam);
                } catch (NumberFormatException e) {

                }
            }
            //////////////////////////

            System.out.println(amount);

            Simulation simulation = new Simulation();
            simulation.setBorrowed_capital(amount);
            simulation.setMonthly_payment_num(months);
            //result
            System.out.println("f mp" + monthly_payment);
            simulation.setMonthly_payment(monthly_payment);

            Client client = clientService.findClientByID(client_Id);
            simulation.setClient(client);

            Employee employee = employeeService.findEmployeeByID(employee_Id);
            simulation.setEmployee(employee);

            System.out.println(simulation.getClient().getCode());

            try {
                if (simulationService.createSimulation(simulation) != 0) {
                    Request request1 = new Request();
                    request1.setCredit_date(LocalDate.now());
                    request1.setAmount(simulation.getBorrowed_capital());
                    request1.setState(STATE.PENDING);
                    request1.setDuration(simulation.getMonthly_payment_num());
                    request1.setClient(simulation.getClient());
                    request1.setSimulation(simulation);

                    requestService.createRequest(request1);
                }
                else {
                    request.setAttribute("message", "Error in Simulation !");
                    request.getRequestDispatcher("/simulation.jsp").forward(request, response);

                }
                response.sendRedirect(request.getContextPath() + "/simulations");
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
                    insertRequests(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

        }
    }
}