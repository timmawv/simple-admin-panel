package avlyakulov.timur.filter;

import avlyakulov.timur.dto.UserSession;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/login", "/register"})
public class AuthFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        HttpSession session = req.getSession();

        UserSession userSession = (UserSession) session.getAttribute("user_session");

        if (userSession == null) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect("/simple_admin_panel/main-page");
        }
    }
}