package org.sirkostya009.filters;

import org.sirkostya009.models.User;
import org.sirkostya009.service.AuthenticationService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/profile", "/profile.jsp", "/all_users.jsp"})
public class AuthenticationFilter implements Filter {

    private final AuthenticationService service = new AuthenticationService();

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain) throws ServletException, IOException {
        var request = (HttpServletRequest) servletRequest;
        var response = (HttpServletResponse) servletResponse;

        var user = (User) request.getSession().getAttribute("user");

        if (service.userExists(user.getUsername())) chain.doFilter(request, response);
        else response.sendRedirect(request.getContextPath() + "/authenticate");
    }

}
