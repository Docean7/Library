package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import db.Entity.User;

public class RegistrationCommand implements ActionCommand {
    @Override
    public String execute(RequestContent requestContent) {
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
            System.out.println("Login is already taken");
            requestContent.addSessionAttribute("errorLogin", "This username is taken");
            requestContent.setError(true);
        }
        if(!emailIsAvailable){
            System.out.println("Email has been already used");
            requestContent.addSessionAttribute("errorEmail", "Email is already in use");
            requestContent.setError(true);
        }
        if(loginIsAvailable && emailIsAvailable) {
            User user = new User();
            user.setFirstName(requestContent.getParameter("first_name"));
            System.out.println(requestContent.getParameter("first_name"));
            user.setLastName(requestContent.getParameter("last_name"));
            user.setLogin(login);
            user.setPassword(requestContent.getParameter("user_password"));
            user.setEmail(email);
            user.setTelNumber(requestContent.getParameter("contact_no"));
            DBManager dbManager = DBManager.getInstance();
            dbManager.addNewUser(user);

        }
        return page;
    }
}
