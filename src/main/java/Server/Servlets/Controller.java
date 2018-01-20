package Server.Servlets;

import Server.Commands.ActionCommand;
import Server.Commands.ActionFactory;
import Server.Managers.ConfigurationManager;
import Server.Managers.MessageManager;
import Server.Managers.RequestContent;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "Controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {
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
        RequestContent requestContent = new RequestContent();
        requestContent.extractValues(request);
        ActionCommand command = client.defineCommand(requestContent);
/*
* вызов реализованного метода execute() и передача параметров
* классу-обработчику конкретной команды
*/
        page = command.execute(requestContent, response);
        requestContent.insertAttributes(request);
// метод возвращает страницу ответа
// page = null; // поэксперементировать!
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
//// вызов страницы ответа на запрос
            dispatcher.forward(request, response);
        } else {
// установка страницы c cообщением об ошибке
            page = ConfigurationManager.getProperty("path.page.index");
            request.getSession().setAttribute("nullPage",
                    MessageManager.getProperty("message.nullpage"));
            response.sendRedirect(request.getContextPath() + page);
        }

    }
}
