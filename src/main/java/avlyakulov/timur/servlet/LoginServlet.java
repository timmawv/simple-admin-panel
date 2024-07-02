package avlyakulov.timur.servlet;

import avlyakulov.timur.dao.UserDao;
import avlyakulov.timur.dto.UserDto;
import avlyakulov.timur.exception.AuthException;
import avlyakulov.timur.service.UserService;
import avlyakulov.timur.util.thymeleaf.ThymeleafUtilRespondHtmlView;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private final String htmlPageLogin = "pages/login";

    private final UserService userService = new UserService(new UserDao());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        ThymeleafUtilRespondHtmlView.respondHtmlPage(htmlPageLogin, context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserDto userDto = new UserDto(login, password);
        try {
            Integer userId = userService.loginUser(userDto);
            HttpSession session = req.getSession();
            session.setAttribute("user_id", userId);
            resp.sendRedirect("/simple_admin_panel/main-page");
        } catch (AuthException e) {
            context.setVariable("error", e.getMessage());
            ThymeleafUtilRespondHtmlView.respondHtmlPage(htmlPageLogin, context, resp);
        }
    }
}