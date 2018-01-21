package Server.Filters;

import db.UserTypeEnum;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LibrarianFilter", urlPatterns = {"/jsp/requireAuth/librarian/*"}, initParams = { @WebInitParam(name = "CATALOG", value = "/jsp/requireAuth/catalog.jsp") })
public class LibrarianFilter implements Filter {
    private String catalogPath;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;

        int type = Integer.parseInt(String.valueOf(httpRequest.getSession().getAttribute("userType")));

        if(UserTypeEnum.LIBRARIAN.value() != type){
            System.out.println("redirecting");
            httpResponse.sendRedirect(httpRequest.getContextPath() + catalogPath);
        }

    }

    public void init(FilterConfig config) throws ServletException {
        catalogPath = config.getInitParameter("CATALOG");
    }

}
