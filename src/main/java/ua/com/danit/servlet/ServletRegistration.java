package ua.com.danit.servlet;

import ua.com.danit.service.ServiceUsers;
import ua.com.danit.utils.Freemarker;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

public class ServletRegistration extends HttpServlet {

    private ServiceUsers serviceUsers;
    private Freemarker freemarker = new Freemarker();
    private HashMap<String, Object> data = new HashMap<>();

    public ServletRegistration(Connection dbConn) {
        this.serviceUsers = new ServiceUsers(dbConn);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        freemarker.render("registration.html", data,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String position = req.getParameter("position");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String urlImg = req.getParameter("urlImg");
        String gender = req.getParameter("gender");

        //remove if
        if(serviceUsers.checkEmail(email)){
            resp.setHeader("Refresh","3; URL=/reg");
            data.put("error_email", email);
            freemarker.render("reg_error.ftl", data,resp);
        }else {
            serviceUsers.addUser(name,surname,password,position,email,urlImg,gender);
            resp.setHeader("Refresh","3; URL=/login");
            data.put("name", name);
            data.put("surname", surname);
            freemarker.render("reg_success.ftl", data,resp);
        }
    }
}
