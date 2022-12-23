package org.sirkostya009.servlets;

import org.sirkostya009.service.UserService;
import org.sirkostya009.service.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AuthenticationServlet", value = "/authenticate")
public class AuthenticationServlet extends HttpServlet {

    private final UserService service = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();

        redirect(response, request, "/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var user = service.authenticate(
                request.getParameter("username"),
                request.getParameter("password")
        );

        if (user == null) {
            redirect(response, request, "/login.jsp?retry=1");
            return;
        }

        request.getSession().setAttribute("user", user);

        redirect(response, request, "/profile");
    }

    private void redirect(HttpServletResponse response, HttpServletRequest request, String url) throws IOException {
        response.sendRedirect(request.getContextPath() + url);
    }

}
