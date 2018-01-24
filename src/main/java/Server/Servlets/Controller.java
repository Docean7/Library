package Server.Servlets;

import Server.Commands.ActionCommand;
import Server.Commands.ActionFactory;
import Server.Managers.ConfigurationManager;
import Server.Managers.MessageManager;
import Server.Managers.RequestContent;
import exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



@WebServlet(name = "Controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
// определение команды, пришедшей из JSP
        ActionFactory client = new ActionFactory();
        HttpSession session = request.getSession();
        RequestContent requestContent = new RequestContent(session, response);
        requestContent.extractValues(request);
        ActionCommand command = client.defineCommand(requestContent);
/*
* вызов реализованного метода execute() и передача параметров
* классу-обработчику конкретной команды
*/      try {
            page = command.execute(requestContent);
        } catch (AppException e){
            request.setAttribute("errorMessage", e.getMessage());
            page = ConfigurationManager.getProperty("path.page.appError");
            LOG.debug("Sending on ERROR PAGE");
        }
        requestContent.insertAttributes(request);

// метод возвращает страницу ответа

        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
//// вызов страницы ответа на запрос
            LOG.debug("Forwarding onto " + page);
            dispatcher.forward(request, response);
        } else {
// установка страницы c cообщением об ошибке
            page = ConfigurationManager.getProperty("path.page.error");
            request.getSession().setAttribute("nullPage",
                    MessageManager.getProperty("message.nullpage"));
            LOG.debug("Sending on ERROR PAGE");
            response.sendRedirect(request.getContextPath() + page);
        }

    }
}
