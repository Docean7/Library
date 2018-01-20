package Server.Managers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class RequestContent {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;
    // конструкторы


    public RequestContent() {
        this.requestAttributes = new HashMap<>();
        this.requestParameters = new HashMap<>();
        this.sessionAttributes = new HashMap<>();
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
        HttpSession session = request.getSession();
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
        //Добавляем атрибуты в сессию
        HttpSession session = request.getSession();
        Set<String> sessionkeySet = sessionAttributes.keySet();
        Iterator<String> skeyIterator = sessionkeySet.iterator();
        while (skeyIterator.hasNext()) {
            String key = skeyIterator.next();
            //System.out.println("Inserting session attribute " + key);
            session.setAttribute(key, sessionAttributes.get(key));
        }
        //добавляем атрибуты в реквест
        Set<String> reqKeySet = requestAttributes.keySet();
        Iterator<String> rKeyIterator = reqKeySet.iterator();
        while(rKeyIterator.hasNext()){
            String key = rKeyIterator.next();
            //System.out.println("Inserting request attribute" + key);
            request.setAttribute(key, requestAttributes.get(key));
        }
    }

    public String getParameter(String parameterName){
        String[] parameters = requestParameters.get(parameterName);
        if(parameters == null)
            return null;
        return parameters[0];
    }

    public Object getAttribute(String name){

        return requestAttributes.get(name);
    }

    public Object getSessionAttribute(String name){
        return sessionAttributes.get(name);
    }

    public void addSessionAttribute(String name, Object object){
        sessionAttributes.put(name, object);
    }
    public void addRequestAttribute(String name, Object object){
        requestAttributes.put(name, object);
    }

    public void removeSessionAttribute(String name){
        sessionAttributes.replace(name, null);

    }

}
