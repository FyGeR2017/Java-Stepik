package servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.AccountService;

import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private AccountService accountService;

    public SignInServlet(AccountService service) {
        accountService = service;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        resp.setContentType("text/html;charset=utf-8");

        User user = null;
        try {
            user = accountService.findUserByLogin(login);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user == null || !user.getPassword().equals(password)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().println("Unauthorized");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("Authorized: " + user.getLogin());
    }
}
