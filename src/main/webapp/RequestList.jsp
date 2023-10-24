<%--
Created by IntelliJ IDEA.
User: adm
Date: 23/10/2023
Time: 17:05
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" href="./css/clients.css" />

    </head>
<body>
<header class="header">
    <nav class="nav">
        <img src="./img/icon.png" alt="Bankist logo" class="nav__logo"
             id="logo" data-version-number="3.0" />
        <ul class="nav__links">
            <li class="nav__item"><a class="nav__link" href="${pageContext.servletContext.contextPath}">Home</a>
            </li>
            <li class="nav__item"><a class="nav__link" href="${pageContext.servletContext.contextPath}/clients">Clients</a></li>
            <li class="nav__item"><a class="nav__link" href="${pageContext.servletContext.contextPath}/employees">Employees</a></li>
            <li class="nav__item"><a class="nav__link" href="${pageContext.servletContext.contextPath}/requests">Requests</a></li>

            <li class="nav__item">
                <a class="nav__link nav__link--btn btn--show-modal" href="${pageContext.servletContext.contextPath}/simulations">Simulate a credit</a>
            </li>
        </ul>
    </nav>
</header>
<div class="content">
    <button class="add-button">Add Client</button>
    <div class="search-container">
        <form action="${pageContext.request.contextPath}/simulations?action=search" method="POST">
            <input type="search" id="clientSearch" name="code" placeholder="Search by code...">
        </form>
    </div>
    <table>
        <thead>
            <tr>
                <th>Client Code</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Birthdate</th>
                <th>Phone</th>
                <th>Address</th>
                <th>Responsible Employee</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="request" items="${requests}" >
                <tr>
                    <td>${request.number}</td>
                    <td>${request.amount}</td>
                    <td>${request.credit_date}</td>
                    <td>${request.duration}</td>
                    <td>${request.state}</td>
                    <td>
                        <button class="update-button" data-requestnumber="${request.number}" >Update</button>
                        <button class="delete-button" data-requestnumber="${request.number}">Delete</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<div id="deleteClientModal" class="modal">
    <div class="modal-content delete-modal">
        <h2>Delete Client</h2>
        <p>Are you sure you want to delete this client ?</p>
        <form action="${pageContext.request.contextPath}/requests?action=delete" method="POST">
            <input type="hidden" id="deleteRequesttId" name="deleteRequestId">
            <div class="form-group">
                <button type="submit" name="action" class="delete-button">Delete</button>
                <button type="button" class="cancel-button">Cancel</button>
            </div>
        </form>
    </div>
</div>


</body>
</html>
