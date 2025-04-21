package org.jsp.servlet;

import accounts.AccountService;
import accounts.UserProfile;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

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
                accountService.addNewUser(newUser);
                userHome += login;
                response.sendRedirect("servlet?path=" + userHome);
                break;
            case "signin":
                if(accountService.getUserByLogin(login) != null){
                    UserProfile user = accountService.getUserByLogin(login);
                    if(password.equals(user.getPassword())){
                        userHome += login;

                        session.setAttribute("user", user);
                        response.sendRedirect("servlet?path=" + userHome);
                    }
                    else{
                        response.sendRedirect("loginPage.html");
                    }
                }
                else{
                    response.sendRedirect("loginPage.html");
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
