package Server.Commands;

import Server.Managers.MessageManager;
import Server.Managers.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionFactory {
    private static final Logger LOG = LogManager.getLogger(ActionFactory.class.getName());
    public ActionCommand defineCommand(RequestContent requestContent) {
        ActionCommand current = new EmptyCommand();

// извлечение имени команды из запроса
        String action = requestContent.getParameter("command");
        LOG.debug("Executing command " + action);
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
