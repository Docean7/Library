package Server.Filters;

import Server.Managers.ConfigurationManager;
import db.UserTypeEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebFilter(filterName = "ControllerFilter", urlPatterns = {"/controller"}, initParams = {@WebInitParam(name = "admin", value = "CHANGETYPE FINDBOOK EDITBOOK DELETEBOOK ADDBOOK"),
@WebInitParam(name = "librarian", value = "ONEDAYORDER BOOKDELIVERED DELETEORDER"),
@WebInitParam(name = "client", value = "ADDBOOKTOUSER SEARCHBOOK DELETEORDER GETCATALOG"),
@WebInitParam(name = "out-of-control", value = "LOGIN REGISTER LOGOUT")})
public class ControllerFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(ControllerFilter.class);
    private Map<Integer, List<String>> accessMap = new HashMap<>();
    private List<String> outOfControl = new ArrayList<>();

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        LOG.debug("Filtering permission");
        if(accessAllowed(req)) {
            LOG.debug("Acces allowed");
            chain.doFilter(req, resp);
        } else {
            req.setAttribute("errorMessage", "You have no permission");
            LOG.trace("User don't have permission. Redirecting");

            req.getRequestDispatcher(ConfigurationManager.getProperty("path.page.apperror"))
                    .forward(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        accessMap.put(UserTypeEnum.ADMIN.value(), asList(config.getInitParameter("admin")));
        accessMap.put(UserTypeEnum.LIBRARIAN.value(), asList(config.getInitParameter("librarian")));
        accessMap.put(UserTypeEnum.REGISTERED_USER.value(), asList(config.getInitParameter("client")));
        LOG.debug("Access map --> " + accessMap);


        // out of control
        outOfControl = asList(config.getInitParameter("out-of-control"));
    }

    private boolean accessAllowed(ServletRequest request){
        HttpServletRequest req = (HttpServletRequest) request;
        String command = req.getParameter("command");


        if (command == null || command.isEmpty()) {
            return false;
        }
        command = command.toUpperCase();
        if (outOfControl.contains(command)) {
            System.out.println("Out of control");
            return true;
        }

        HttpSession session = req.getSession(false);
        if (session == null) {
            return false;
        }

        Integer userType = Integer.parseInt(String.valueOf(session.getAttribute("userType")));

        return accessMap.get(userType).contains(command);

    }

    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }

}
