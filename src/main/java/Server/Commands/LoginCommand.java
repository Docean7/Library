package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import db.Entity.Order;
import db.Entity.User;
import db.UserTypeEnum;
import exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class LoginCommand implements ActionCommand {
//    private static final Logger rootLogger = LogManager.getRootLogger();
    private static final Logger LOG = LogManager.getLogger(LoginCommand.class.getName());

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    private static final int ALL_GOOD = 0;
    private static final int WRONG_LOGIN = 1;
    private static final int WRONG_PASSWORD = 2;

    @Override
    public String execute(RequestContent requestContent) throws AppException {
        String page = ConfigurationManager.getProperty("path.page.login");
// извлечение из запроса логина и пароля
        String login = requestContent.getParameter(PARAM_NAME_LOGIN);
        String pass = requestContent.getParameter(PARAM_NAME_PASSWORD);
// проверка логина и пароля
        int checkCode = LoginLogic.checkLogin(login, pass);
        switch (checkCode) {
            case ALL_GOOD:
                LOG.debug("User " + login + " has signed in");
                page = ConfigurationManager.getProperty("path.servlet.controller") + "?command=getcatalog&sort=name";

                //Заполнение атрибутов сессии для личного кабинета
                DBManager dbManager = DBManager.getInstance();
                User user = dbManager.getUserByLogin(login);

                int id = user.getId();
                requestContent.addSessionAttribute("userId", id);
                requestContent.addSessionAttribute("login", login);
                requestContent.addSessionAttribute("firstname", user.getFirstName());
                requestContent.addSessionAttribute("lastname", user.getLastName());
                requestContent.addSessionAttribute("email", user.getEmail());
                requestContent.addSessionAttribute("telnumber", user.getTelNumber());
                requestContent.addSessionAttribute("bookList", dbManager.getBooksForUser(id));
                int userType = user.getUserType();
                requestContent.addSessionAttribute("userType", userType);

                if(userType == UserTypeEnum.LIBRARIAN.value()){
                    LOG.debug("Adding orders to session");
                    List<Order> orders = dbManager.getAllOrders();
                    requestContent.addSessionAttribute("orders", orders);
                }

                break;

            case WRONG_LOGIN:
                LOG.warn("Wrong login");
                requestContent.addRequestAttribute("loginCheck", "User not found");
                break;

            case WRONG_PASSWORD:
                LOG.warn("Wrong password");
                requestContent.addRequestAttribute("passwordCheck", "Wrong password");
                requestContent.addRequestAttribute("cashedLogin", login);
                break;
        }

        return page;
    }
}
