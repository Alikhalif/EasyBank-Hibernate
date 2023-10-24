package org.youcode.easybank_hibernate.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.youcode.easybank_hibernate.entities.Request;
import org.youcode.easybank_hibernate.services.RequestService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RequestServlet", value = "/requests")
public class RequestServlet extends HttpServlet {

    @Inject
    private RequestService requestService;

    private void deleteRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("deleteClientId"));
        boolean isDeleted = requestService.deleteRequest(id);
        if (isDeleted) {
            response.sendRedirect(request.getContextPath() + "/clients");
        }
    }

    private void getRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        List<Request> requests = requestService.getAllRequests();
        request.setAttribute("requests",requests);
        request.getRequestDispatcher("/RequestList.jsp").forward(request, response);

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "delete":
                deleteRequest(request, response);
                break;
            case "search":
                //findClientByID(request, response);
                break;

        }
    }
}