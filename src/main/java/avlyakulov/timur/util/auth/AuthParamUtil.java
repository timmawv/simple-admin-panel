package avlyakulov.timur.util.auth;

import avlyakulov.timur.dto.UserSession;
import jakarta.servlet.http.HttpServletRequest;
import org.thymeleaf.context.Context;

public class AuthParamUtil {

    public static void setParamsToPage(HttpServletRequest req, Context context) {
        UserSession userSession = (UserSession) req.getSession().getAttribute("user_session");
        context.setVariable("login", userSession.getLogin());
        context.setVariable("role", userSession.getRole());
    }
}
