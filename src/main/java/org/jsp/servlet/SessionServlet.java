package org.jsp.servlet;

import accounts.AccountService;
import accounts.UserProfile;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

public class SessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserProfile user = (session != null) ? (UserProfile) session.getAttribute("user") : null;
        if (session != null) {
            user = (UserProfile) session.getAttribute("user");
        } else {
            response.sendRedirect("loginPage.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String todo = request.getParameter("todo");
        AccountService accountService = (AccountService) getServletContext().getAttribute("AccountService");
        HttpSession session = request.getSession();

        String userHome = "C:/CSUjava/";
        switch (todo){
            case "signup":
                UserProfile newUser = new UserProfile(login, password, email);
                session.setAttribute("user", newUser);
                boolean success = false; // Если все пройдет нормально, то превратится в тру
                try {
                    success = accountService.addNewUser(newUser);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if (success) {
                    userHome += login;
                    response.sendRedirect("servlet?path=" + userHome);
                } else {response.sendRedirect("loginPage.html");}
                break;
            case "signin":
                UserProfile user = new UserProfile(login, password);
                try {
                    if(accountService.isUserRegistered(user)){
                        userHome += login;
                        session.setAttribute("user", user);
                        response.sendRedirect("servlet?path=" + userHome);
                    } else { response.sendRedirect("loginPage.html");}
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "logout":
                session.removeAttribute("user");
                response.sendRedirect("loginPage.html");
            default:
                throw new IllegalStateException("Unexpected value: " + todo);
        }
    }



}
