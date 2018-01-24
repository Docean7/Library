package Server.Filters;

import db.UserTypeEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "TypeSettingFilter", urlPatterns = {"/*"})
public class TypeSettingFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(TypeSettingFilter.class);
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        if(request.getSession().getAttribute("userType") == null){
            request.getSession().setAttribute("userType", UserTypeEnum.UNREGISTERED_USER.value());
            LOG.info("Setting guest type");
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
