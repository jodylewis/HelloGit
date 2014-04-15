package guestbook;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// JWL
import javax.servlet.http.Cookie;
import java.util.logging.Logger;
import java.util.logging.Level;

public class SignGuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());

	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        log.setLevel(Level.INFO);
        log.info("User: " + userService.getCurrentUser());
        log.info("Get Cookies");
        Cookie[] cookies = req.getCookies();
        if(cookies !=null) {
	        for(Cookie cookie : cookies) {
	        	//resp.getWriter().println(cookie.getName());
	        	log.info(cookie.getName());
	        }
        } else {
        	log.info("No Cookies");
        }
        
        // We have one entity group per Guestbook with all Greetings residing
        // in the same entity group as the Guestbook to which they belong.
        // This lets us run a transactional ancestor query to retrieve all
        // Greetings for a given Guestbook.  However, the write rate to each
        // Guestbook should be limited to ~1/second.
        String guestbookName = req.getParameter("guestbookName");
        Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
        String content = req.getParameter("content");
        Date date = new Date();
        Entity greeting = new Entity("Greeting", guestbookKey);
        greeting.setProperty("user", user);
        greeting.setProperty("date", date);
        greeting.setProperty("content", content);

        log.info("AppID: " + greeting.getAppId() + "\n" +
        		 "User: " + greeting.getProperty("user") + "\n" + 
        		 "Date: " + greeting.getProperty("date"));
        //log.info("User: " + greeting.getProperty("user"));
        //log.info("Date: " + greeting.getProperty("date"));
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(greeting);

        resp.sendRedirect("/guestbook.jsp?guestbookName=" + guestbookName);
    }
}