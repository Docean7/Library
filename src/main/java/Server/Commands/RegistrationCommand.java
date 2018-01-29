package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import db.Entity.User;
import exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class RegistrationCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(RegistrationCommand.class.getName());
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    @Override
    public String execute(RequestContent requestContent) throws AppException {
        String login = requestContent.getParameter("user_name");
        String email = requestContent.getParameter("email");
        String page = ConfigurationManager.getProperty("path.page.login");

        boolean loginIsAvailable = RegistrationLogic.isAvailableLogin(login);
        boolean emailIsAvailable = RegistrationLogic.isAvailableEmail(email);
        //Заполнение формы

        String fname = requestContent.getParameter("first_name");
        requestContent.addSessionAttribute("firstname",fname );
        String lname = requestContent.getParameter("last_name");
        requestContent.addSessionAttribute("lastname", lname);
        requestContent.addSessionAttribute("checklogin", login);
        requestContent.addSessionAttribute("checkemail", email);
        String telnumber = requestContent.getParameter("contact_no");
        requestContent.addSessionAttribute("contact", telnumber);


        if (!loginIsAvailable){
            LOG.warn("Login is already taken");
            requestContent.addSessionAttribute("errorLogin", "This username is taken");
            requestContent.setError(true);
        }
        if(!emailIsAvailable){
            LOG.warn("Email has been already used");
            requestContent.addSessionAttribute("errorEmail", "Email is already in use");
            requestContent.setError(true);
        }

        if(loginIsAvailable && emailIsAvailable) {
            User user = new User();
            user.setFirstName(requestContent.getParameter("first_name"));
            user.setLastName(requestContent.getParameter("last_name"));
            user.setLogin(login);
            user.setPassword(requestContent.getParameter("user_password"));
            user.setEmail(email);
            user.setTelNumber(requestContent.getParameter("contact_no"));
            DBManager dbManager = DBManager.getInstance();
            dbManager.addNewUser(user);
            LOG.debug("Registered user " + login);
        }
        return page;
    }

//    public static boolean checkLength(String arg, int min, int max){
//        return arg.length()>=min && arg.length() <= max;
//    }
//
//    public static boolean checkEmail(String email){
//       Matcher matcher =  VALID_EMAIL_ADDRESS_REGEX.matcher(email);
//       return matcher.find();
//    }
}
