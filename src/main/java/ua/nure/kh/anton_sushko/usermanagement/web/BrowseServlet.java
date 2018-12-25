package ua.nure.kh.anton_sushko.usermanagement.web;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kh.anton_sushko.usermanagement.User;
import ua.nure.kh.anton_sushko.usermanagement.db.DaoFactory;
import ua.nure.kh.anton_sushko.usermanagement.db.DatabaseException;

public class BrowseServlet extends HttpServlet {
	
	private static final String BROWSE_JSP = "/browse.jsp";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String BROWSE_SERVLET = "/browse";
    private static final String DETAILS_SERVLET = "/details";
    private static final String EDIT_SERVLET = "/edit";
    private static final String ADD_SERVLET = "/add";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
        if (Objects.nonNull(req.getParameter("addButton"))) {
        
            add(req, resp);
        } 
        else if (Objects.nonNull(req.getParameter("editButton"))) {
        
            edit(req, resp);
        } 
        else if (Objects.nonNull(req.getParameter("deleteButton"))) {
        
            delete(req, resp);
        } 
        else if (Objects.nonNull(req.getParameter("detailsButton"))) {
        
            details(req, resp);
        } 
        else {
        
            browse(req, resp);
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
        req.getRequestDispatcher(ADD_SERVLET).forward(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
        putUserToSesseonAndRedirect(req, resp, EDIT_SERVLET);
    }

    private void redirectBackWithError(HttpServletRequest req, HttpServletResponse resp, String s) throws ServletException, IOException {
        
        req.setAttribute(ERROR_ATTRIBUTE, s);
        
        req.getRequestDispatcher(BROWSE_JSP).forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
        String idString = getIdString(req, resp);
        
        if (Objects.isNull(idString)) {
        
            return;
        }
        try {
        
            User user = DaoFactory.getInstance().getUserDao().find(Long.parseLong(idString));
            
            DaoFactory.getInstance().getUserDao().delete(user);
        } catch (DatabaseException e) {
        
            redirectBackWithError(req, resp, "Error :" + e.toString());
            
            return;
        }
        
        resp.sendRedirect(BROWSE_SERVLET);
    }

    private void putUserToSesseonAndRedirect(HttpServletRequest req, HttpServletResponse resp, String pathToRedirect) throws ServletException, IOException {
    
        String idString = getIdString(req, resp);
        
        if (Objects.isNull(idString)) {
        
            return;
        }
        try {
        
            User user = DaoFactory.getInstance().getUserDao().find(Long.parseLong(idString));
            
            req.getSession(true).setAttribute("user", user);
        } catch (Exception e) {
        
            redirectBackWithError(req, resp, "Error :" + e.toString());
            
            return;
        }
        
        req.getRequestDispatcher(pathToRedirect).forward(req, resp);
    }

    private String getIdString(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
        String idString = req.getParameter("id");
        
        if (Objects.isNull(idString) || idString.trim().isEmpty()) {
        
            redirectBackWithError(req, resp, "You should choose user");
            
            return null;
        }
        
        return idString;
    }

    private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
        putUserToSesseonAndRedirect(req, resp, DETAILS_SERVLET);
    }

    private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
        
            Collection<User> users = DaoFactory.getInstance().getUserDao().findAll();
            
            req.getSession(true).setAttribute("users", users);
            
            req.getRequestDispatcher(BROWSE_JSP).forward(req, resp);
        } catch (DatabaseException e) {
        
            throw new ServletException(e);
        }
    }
}