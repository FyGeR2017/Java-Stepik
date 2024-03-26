package servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.AccountService;

import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private AccountService accountService;

    public SignUpServlet(AccountService service) {
        accountService = service;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        resp.setContentType("text/html;charset=utf-8");

        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        long id = accountService.addUser(login, password);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("id: " + id);
    }
}
