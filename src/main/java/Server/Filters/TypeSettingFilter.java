package Server.Filters;

import db.UserTypeEnum;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "TypeSettingFilter", urlPatterns = {"/*"})
public class TypeSettingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        if(request.getSession().getAttribute("userType") == null){
            request.getSession().setAttribute("userType", UserTypeEnum.UNREGISTERED_USER.value());
            System.out.println("Setting guest type");
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
