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
@WebFilter(filterName = "LibrarianFilter", urlPatterns = {"/jsp/requireAuth/admin/*"}, initParams = { @WebInitParam(name = "CATALOG", value = "/jsp/requireAuth/catalog.jsp") })
public class AdminFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(AdminFilter.class.getName());
    private String catalogPath;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;

        int type = Integer.parseInt(String.valueOf(httpRequest.getSession().getAttribute("userType")));

        if(UserTypeEnum.ADMIN.value() != type){
            LOG.info("Usertype is not 'admin'. Redirecting");
            httpResponse.sendRedirect(httpRequest.getContextPath() + catalogPath);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        catalogPath = config.getInitParameter("CATALOG");
    }

}
