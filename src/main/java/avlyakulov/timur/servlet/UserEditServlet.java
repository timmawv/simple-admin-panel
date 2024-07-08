package avlyakulov.timur.servlet;

import avlyakulov.timur.dao.UserDao;
import avlyakulov.timur.dto.UserDto;
import avlyakulov.timur.dto.UserSession;
import avlyakulov.timur.exception.UserAlreadyExistException;
import avlyakulov.timur.service.UserService;
import avlyakulov.timur.util.auth.AuthParamUtil;
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
        context.setVariable("id", req.getParameter("id"));
        AuthParamUtil.setParamsToPage(req, context);
        ThymeleafUtilRespondHtmlView.respondHtmlPage(userCreatePage, context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        Integer id = Integer.parseInt(req.getParameter("id"));
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserDto userDto = new UserDto(id, login, password);
        try {
            userService.updateUser(userDto);
            resp.sendRedirect("/simple_admin_panel/main-page");
        } catch (UserAlreadyExistException e) {
            context.setVariable("error", e.getMessage());
            context.setVariable("id", id);
            AuthParamUtil.setParamsToPage(req, context);
            ThymeleafUtilRespondHtmlView.respondHtmlPage(userCreatePage, context, resp);
        }
    }
}