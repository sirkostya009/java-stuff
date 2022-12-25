package org.sirkostya009.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AllUsersServlet", value = "/all-users")
public class AllUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(getServletContext().getInitParameter("jspPath") + "all_users.jsp").forward(request, response);
    }
}
