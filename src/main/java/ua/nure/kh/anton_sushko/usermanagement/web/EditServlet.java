package ua.nure.kh.anton_sushko.usermanagement.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kh.anton_sushko.usermanagement.User;
import ua.nure.kh.anton_sushko.usermanagement.db.DaoFactory;
import ua.nure.kh.anton_sushko.usermanagement.db.DatabaseException;
import ua.nure.kh.anton_sushko.usermanagement.web.exeptions.ValidationException;

public class EditServlet extends HttpServlet {

    private static final String DATE_PATTERN = "dd.mm.yyyy";
    private static final DateFormat FORMATER = new SimpleDateFormat(DATE_PATTERN);
    private static final String EDIT_JSP = "/edit.jsp";
    private static final String BROWSE_SERVLET = "/browse";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        if (Objects.nonNull(req.getParameter("okButton"))) {
            
            updateUser(req, resp);
        } 
        else if (Objects.nonNull(req.getParameter("cancelButton"))) {
            
            cancelEdit(req, resp);
        } 
        else {
            
            showPage(req, resp);
        }
    }

    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        req.getRequestDispatcher(EDIT_JSP).forward(req, resp);
    }

    private void cancelEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        req.getRequestDispatcher(BROWSE_SERVLET).forward(req, resp);
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        User user;
        try {
            
            user = getUserFromRequest(req);
        } catch (ValidationException e) {
            
            req.setAttribute("error", e.getMessage());
            
            showPage(req, resp);
            
            return;
        }
        try {
            
            processUser(user);
        } catch (DatabaseException e) {
            
            e.printStackTrace();
            
            throw new ServletException(e);
        }
        
        req.getRequestDispatcher(BROWSE_SERVLET).forward(req, resp);
    }

    protected void processUser(User user) throws DatabaseException {
        
        DaoFactory.getInstance().getUserDao().update(user);
    }

    private User getUserFromRequest(HttpServletRequest req) throws ValidationException {
        
        User user = new User();
        String idString = req.getParameter("id");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String dateString = req.getParameter("date");
        
        if (Objects.nonNull(idString)) {
            
            user.setId(new Long(idString));
        }
        if (Objects.isNull(firstName)) {
            
            throw new ValidationException("First name can not be empty");
        }
        if (Objects.isNull(lastName)) {
            
            throw new ValidationException("Last name can not be empty");
        }
        if (Objects.isNull(dateString)) {
            
            throw new ValidationException("Date can not be empty");
        }
        
        user.setFirstName(firstName);
        user.setLastName(lastName);
        
        try {
            
            user.setDateOfBirth(FORMATER.parse(dateString));
        } catch (ParseException e) {
            
            throw new ValidationException("Date format is incorrect");
        }
        
        return user;
    }
}