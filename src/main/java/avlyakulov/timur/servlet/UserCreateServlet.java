package avlyakulov.timur.servlet;

import avlyakulov.timur.dao.UserDao;
import avlyakulov.timur.dto.UserDto;
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

@WebServlet(urlPatterns = "/users/create")
public class UserCreateServlet extends HttpServlet {

    private final UserService userService = new UserService(new UserDao());

    private final String userCreatePage = "pages/create-user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        ThymeleafUtilRespondHtmlView.respondHtmlPage(userCreatePage, context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        UserDto userDto = new UserDto(req.getParameter("login"), req.getParameter("password"));
        try {
            userService.createUser(userDto);
            resp.sendRedirect("/simple_admin_panel/main-page");
        } catch (UserAlreadyExistException e) {
            context.setVariable("error", e.getMessage());
            ThymeleafUtilRespondHtmlView.respondHtmlPage(userCreatePage, context, resp);
        }
    }
}
