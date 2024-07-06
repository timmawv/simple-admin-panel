package avlyakulov.timur.servlet;

import avlyakulov.timur.dao.UserDao;
import avlyakulov.timur.dto.UserDto;
import avlyakulov.timur.dto.UserSession;
import avlyakulov.timur.exception.UserAlreadyExistException;
import avlyakulov.timur.service.UserService;
import avlyakulov.timur.util.thymeleaf.ThymeleafUtilRespondHtmlView;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    private final UserService userService = new UserService(new UserDao());
    private final String htmlPageRegister = "pages/register";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        ThymeleafUtilRespondHtmlView.respondHtmlPage(htmlPageRegister, context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserDto userDto = new UserDto(login, password);
        try {
            userService.createUser(userDto);
            resp.sendRedirect("/simple_admin_panel/main-page");
        } catch (UserAlreadyExistException e) {
            context.setVariable("error", e.getMessage());
            ThymeleafUtilRespondHtmlView.respondHtmlPage(htmlPageRegister, context, resp);
        }
    }
}