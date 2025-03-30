package org.jsp.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


public class MainServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String path = request.getParameter("path");
//
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>3.2</title></head><body>");

        File directory;
        if (path == null || path.isEmpty()) {
            directory = new File(System.getProperty("user.dir"));
        } else {
            directory = new File(path);
        }

        if (!directory.exists() || !directory.isDirectory()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // статус 404
            return;
        }

        out.println("<i>" + path + " | " + new Date() + "</i>");
        if (directory.getParentFile() != null){
            out.println("<br> <a href='/servletjsp/servlet?path=" + directory.getParentFile().getAbsolutePath() + "'>" + "<==" + "</a>");
        }
        out.println("<ul>");
//
        File[] filesList = directory.listFiles();
        if (filesList != null) {
            for (File file : filesList) {
                if (file.isDirectory()) {
                    out.println("<li> <a href='/servletjsp/servlet?path=" + file.getAbsolutePath() + "'>" + file.getName() + "</a>" + "/</li>");
                } else {
                    out.println("<li><i> <a href='/servletjsp/download?file=" + file.getAbsolutePath() + "'>" + file.getName() + "</a></i></li>");
                }
            }
        } else {
            out.println("<li>Папка пуста.</li>");
        }

        out.println("</ul>");
        out.println("</body></html>");
    }

    @Override
    public  void destroy(){}
}