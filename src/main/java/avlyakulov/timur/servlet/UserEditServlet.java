package avlyakulov.timur.servlet;

import avlyakulov.timur.dao.UserDao;
import avlyakulov.timur.dto.UserSession;
import avlyakulov.timur.dto.UserUpdateDto;
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

@WebServlet(urlPatterns = "/users/edit")
public class UserEditServlet extends HttpServlet {

    private final String userCreatePage = "pages/edit-user";

    private final UserService userService = new UserService(new UserDao());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        UserSession userSession = (UserSession) req.getSession().getAttribute("user_session");
        context.setVariable("login", userSession.getLogin());
        context.setVariable("role", userSession.getRole());
        context.setVariable("id", req.getParameter("id"));
        ThymeleafUtilRespondHtmlView.respondHtmlPage(userCreatePage, context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        Integer id = Integer.parseInt(req.getParameter("id"));
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserUpdateDto userUpdateDto = new UserUpdateDto(id, login, password);
        try {
            userService.updateUser(userUpdateDto);
            resp.sendRedirect("/simple_admin_panel/main-page");
        } catch (UserAlreadyExistException e) {
            context.setVariable("error", e.getMessage());
            context.setVariable("id", id);
            UserSession userSession = (UserSession) req.getSession().getAttribute("user_session");
            context.setVariable("login", userSession.getLogin());
            context.setVariable("role", userSession.getRole());
            ThymeleafUtilRespondHtmlView.respondHtmlPage(userCreatePage, context, resp);
        }
    }
}