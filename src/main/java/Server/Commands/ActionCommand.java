package Server.Commands;

import Server.Managers.RequestContent;
import exception.AppException;

public interface ActionCommand {
    String execute(RequestContent requestContent) throws AppException;
}

