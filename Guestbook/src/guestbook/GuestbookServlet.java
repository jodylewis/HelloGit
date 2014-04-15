package guestbook;

import java.io.IOException;
import javax.servlet.http.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class GuestbookServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        if (user != null) {
			resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().println("Hello, world: " + user.getNickname());
			resp.getWriter().print("<a href=");
			resp.getWriter().print(userService.createLogoutURL(req.getRequestURI()));
			resp.getWriter().println(">sign out</a>");
       } else {
    	   resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
       }
	}
}


