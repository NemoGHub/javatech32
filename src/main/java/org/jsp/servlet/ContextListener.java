package org.jsp.servlet;

import accounts.AccountService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

// при запуске сервера создаем сервис дя аккаунтов
@WebListener // tckb xnj-nj ghb[jlbn gj ctnb? nj dslftv cthdbc
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AccountService accountService = new AccountService();
        sce.getServletContext().setAttribute("AccountService", accountService);

    }
    // ContextDestroyed() не переопределяем -
    // сервис будет в памяти пока не выключим сервер
}
