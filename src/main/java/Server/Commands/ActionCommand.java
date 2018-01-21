package Server.Commands;

import Server.Managers.RequestContent;

public interface ActionCommand {
    String execute(RequestContent requestContent);
}

