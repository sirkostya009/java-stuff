package org.sirkostya009.servlets;

import org.sirkostya009.services.UserService;
import org.sirkostya009.services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private final UserService service = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().invalidate();

        forward(response, request, getServletContext().getInitParameter("jspPath") + "/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var user = service.authenticate(
                request.getParameter("username"),
                request.getParameter("password")
        );

        if (user == null) {
            forward(response, request, getServletContext().getInitParameter("jspPath") + "/login.jsp?retry=1");
            return;
        }

        request.getSession().setAttribute("user", user);

        // update users whenever someone new logs in
        getServletContext().setAttribute("users", service.findAll());

        response.sendRedirect(request.getContextPath() + "/profile");
    }

    private void forward(HttpServletResponse response, HttpServletRequest request, String url) throws IOException, ServletException {
        request.getRequestDispatcher(url).forward(request, response);
    }

}
