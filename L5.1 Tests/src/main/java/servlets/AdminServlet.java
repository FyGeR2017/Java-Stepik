package servlets;

import accountServer.AccountServerControllerMBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class AdminServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AdminServlet.class.getName());
    public static final String PAGE_URL = "/admin";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("Admin:type=AccountServerController.usersLimit");
            AccountServerControllerMBean serverController = (AccountServerControllerMBean) mbs.getAttribute(name, "UsersLimit");
            int usersLimit = serverController.getUsersLimit();
            resp.getWriter().println(usersLimit);
        } catch (Exception e) {
            logger.error("Error getting users limit", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}