package Server.Filters;

import db.UserTypeEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/jsp/requireAuth/*"}, initParams = { @WebInitParam(name = "INDEX_PATH", value = "/index.jsp") })
public class SecurityFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(SecurityFilter.class);
    private String indexPath;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;

        int type = Integer.parseInt(String.valueOf(httpRequest.getSession().getAttribute("userType")));
        LOG.info("Usertype is " + type);
        if(UserTypeEnum.UNREGISTERED_USER.value() == type){
            LOG.info("Not authorised user. Redirecting");
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        }
// переход на заданную страницу

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        indexPath = config.getInitParameter("INDEX_PATH");
    }

}
