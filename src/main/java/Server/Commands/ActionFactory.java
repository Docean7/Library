package Server.Commands;

import Server.Managers.MessageManager;
import Server.Managers.RequestContent;

public class ActionFactory {
    public ActionCommand defineCommand(RequestContent requestContent) {
        ActionCommand current = new EmptyCommand();

// извлечение имени команды из запроса
        String action = requestContent.getParameter("command");
        System.out.println("Executing command " + action);
        if (action == null || action.isEmpty()) {
// если команда не задана в текущем запросе
            return current;
        }
// получение объекта, соответствующего команде
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            requestContent.addRequestAttribute("wrongAction", action
                    + MessageManager.getProperty("message.wrongaction"));
        }
        return current;
    }
}
