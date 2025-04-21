package org.jsp.servlet;

import accounts.UserProfile;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.*;
import java.nio.file.Paths;
import java.util.Date;


public class DownloadServlet extends HttpServlet {
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
        HttpSession session = request.getSession();
        UserProfile user = (UserProfile) session.getAttribute("user");
        if (user == null) {response.sendRedirect("loginPage.html");}
        String path = Paths.get(request.getParameter("path")).normalize().toString();
        String rootDirectory = "C:/CSUjava/" + user.getLogin();
        if (!path.contains(rootDirectory)){
            path = rootDirectory;
        }

        File file = new File(path);

        // Проверяем, существует ли файл и является ли он файлом
        if (!file.exists() || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found.");
            return;
        }

        // Устанавливаем заголовки для скачивания файла
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        response.setContentLengthLong(file.length());

        // Читаем файл и отправляем его в ответ
        try (FileInputStream inStream = new FileInputStream(file);
             OutputStream outStream = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        }
    }

    @Override
    public  void destroy(){}
}