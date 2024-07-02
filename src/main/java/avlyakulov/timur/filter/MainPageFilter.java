package avlyakulov.timur.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = "/main-page")
public class MainPageFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        HttpSession session = req.getSession();

        Integer userId = (Integer) session.getAttribute("user_id");

        if (userId == null) {
            resp.sendRedirect("/simple_admin_panel/login");
        } else {
            filterChain.doFilter(req, resp);
        }
    }
}
