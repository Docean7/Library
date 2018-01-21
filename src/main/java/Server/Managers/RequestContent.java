package Server.Managers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class RequestContent {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;
    private HttpSession session;
    private HttpServletResponse response;
    private boolean error;
    // конструкторы


    public RequestContent(HttpSession session, HttpServletResponse response) {
        this.requestAttributes = new HashMap<>();
        this.requestParameters = new HashMap<>();
        this.sessionAttributes = new HashMap<>();
        this.session = session;
        this.response = response;
    }

    // метод извлечения информации из запроса
    public void extractValues(HttpServletRequest request) {
        //добавляем атрибуты реквеста
        String name;
        Enumeration<String> attributeNames = request.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            name = attributeNames.nextElement();
            // System.out.println("Extract request attribute " + name);
            requestAttributes.put(name, request.getAttribute(name));
        }
        //добавляем параметры
        if (request.getParameterMap() != null) {
            // System.out.println("Putting request parameters");
            requestParameters.putAll(request.getParameterMap());
        }
        //атрибуты сессии

        String sName;
        Enumeration<String> sessionAttributeNames = session.getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()) {
            sName = sessionAttributeNames.nextElement();
            //System.out.println("Extract session attribute " + sName);
            sessionAttributes.put(sName, session.getAttribute(sName));
        }

    }

    // метод добавления в запрос данных для передачи в jsp
    public void insertAttributes(HttpServletRequest request) {
            if(request.getSession(false)!= null) {
                //Добавляем атрибуты в сессию
                Set<String> sessionkeySet = sessionAttributes.keySet();
                Iterator<String> skeyIterator = sessionkeySet.iterator();
                while (skeyIterator.hasNext()) {
                    String key = skeyIterator.next();
                    System.out.println("Inserting session attribute " + key);
                    session.setAttribute(key, sessionAttributes.get(key));
                }
                //добавляем атрибуты в реквест
                Set<String> reqKeySet = requestAttributes.keySet();
                Iterator<String> rKeyIterator = reqKeySet.iterator();
                while (rKeyIterator.hasNext()) {
                    String key = rKeyIterator.next();
                    //System.out.println("Inserting request attribute" + key);
                    request.setAttribute(key, requestAttributes.get(key));
                }
                if(error){
                    try {
                        response.sendError(400);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    public String getParameter(String parameterName) {
        String[] parameters = requestParameters.get(parameterName);
        if (parameters == null)
            return null;
        return parameters[0];
    }

    public Object getAttribute(String name) {

        return requestAttributes.get(name);
    }

    public Object getSessionAttribute(String name) {
        return sessionAttributes.get(name);
    }

    public void addSessionAttribute(String name, Object object) {
        System.out.println(name + " " + object);
        sessionAttributes.put(name, object);
    }

    public void addRequestAttribute(String name, Object object) {
        requestAttributes.put(name, object);
    }

    public void removeSessionAttribute(String name) {
        sessionAttributes.replace(name, null);
    }

    public void deleteSession() {
        session.invalidate();
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
