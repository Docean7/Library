package Server.Commands;

import Server.Managers.RequestContent;

import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements ActionCommand {
    @Override
    public String execute(RequestContent requestContent, HttpServletResponse response) {
//        requestContent.getSession().invalidate();
        return null;
    }
}
