package org.sirkostya009.filters;

import org.sirkostya009.models.User;
import org.sirkostya009.services.UserService;
import org.sirkostya009.services.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/profile", "/all-users"})
public class AuthenticationFilter implements Filter {

    private final UserService service = new UserServiceImpl();

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain) throws ServletException, IOException {
        var request = (HttpServletRequest) servletRequest;
        var response = (HttpServletResponse) servletResponse;

        var user = (User) request.getSession().getAttribute("user");

        if (service.userExists(user)) chain.doFilter(request, response);
        else response.sendRedirect(request.getContextPath() + "/login");
    }

}
