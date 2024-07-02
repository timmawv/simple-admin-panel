package avlyakulov.timur.servlet;

import avlyakulov.timur.dao.UserDao;
import avlyakulov.timur.entity.User;
import avlyakulov.timur.service.UserService;
import avlyakulov.timur.util.thymeleaf.ThymeleafUtilRespondHtmlView;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/main-page")
public class MainPageServlet extends HttpServlet {

    private final UserService userService = new UserService(new UserDao());

    private final String htmlPageMain = "pages/main-page";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Context context = new Context();
        String userLogin = (String) req.getSession().getAttribute("user_login");
        context.setVariable("login", userLogin);
        List<User> users = userService.findAll();
        context.setVariable("users", users);
        ThymeleafUtilRespondHtmlView.respondHtmlPage(htmlPageMain, context, resp);
    }
}
