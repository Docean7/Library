package Server.Commands;

import Server.Managers.RequestContent;

import javax.servlet.http.HttpServletResponse;

public interface ActionCommand {
    String execute(RequestContent requestContent, HttpServletResponse response);
}
