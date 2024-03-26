package main;

import db.DbSessionFactory;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.Session;
import service.AccountService;
import servlet.SignInServlet;
import servlet.SignUpServlet;

public class Main {
    public static void main(String[] args) throws Exception{

        Session session = DbSessionFactory
                .getInstance()
                .openSession();
        session.createSQLQuery("create table if not exists users (id bigint auto_increment, login varchar(256), password varchar(256), primary key (id));");
        session.close();

        AccountService accountService = new AccountService();

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        contextHandler.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {
                contextHandler,
        });

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
