package ua.nure.kh.anton_sushko.usermanagement.web;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailsServlet extends HttpServlet {

    private static final String BROWSE_SERVLET = "/browse";
    
    private static final String DETAILS_JSP = "/details.jsp";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
        if (Objects.nonNull((req.getParameter("backButton")))) {
        
            req.getSession(true).removeAttribute("user");
            
            redirect(req, resp, BROWSE_SERVLET);
        } else {
        
            redirect(req, resp, DETAILS_JSP);
        }
    }

    private void redirect(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
    
        req.getRequestDispatcher(path).forward(req, resp);
    }
}