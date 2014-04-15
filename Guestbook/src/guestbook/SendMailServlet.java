package guestbook;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.IOException;
//import java.util.Date;
import java.util.Properties;
//import java.util.Date;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// JWL
import javax.servlet.http.Cookie;

//import com.google.appengine.api.datastore.DatastoreService;
//import com.google.appengine.api.datastore.DatastoreServiceFactory;
//import com.google.appengine.api.datastore.Entity;
//import com.google.appengine.api.datastore.Key;
//import com.google.appengine.api.datastore.KeyFactory;
//import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.util.logging.Logger;
import java.util.logging.Level;

public class SendMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());

	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
        
        UserService userService = UserServiceFactory.getUserService();
//        User user = userService.getCurrentUser();
        
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
        
        String guestbookName = req.getParameter("guestbookName");
        String message = req.getParameter("message");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        
        //        Date date = new Date();
        
        log.info("guestbookNameD: " + guestbookName + "\n" +
        		 "name: " + name + "\n" +
        		 "email: " + email + "\n" +
        		 "message: " + message);
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String msgBody = "name: " + name + "\n" +
			       		 "email: " + email + "\n" +
			       		 "message: " + message;
		
		try {
		    Message msg = new MimeMessage(session);
		    msg.setFrom(new InternetAddress("jwl0124@gmail.com", "Jody Lewis"));
		    msg.addRecipient(Message.RecipientType.TO, new InternetAddress("jody.lewis.MBA@gmail.com", "JW Lewis MBA"));
		    msg.setSubject("Test message from App");
		    msg.setText(msgBody);
		    Transport.send(msg);
		} catch (AddressException aException) {
			log.info("AddressException");
		} catch (MessagingException mExecption) {
			log.info("MessagingException");
		}
		
        resp.sendRedirect("/#");
    }
}

/*
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

	Properties props = new Properties();
	Session session = Session.getDefaultInstance(props, null);

	//String msgBody = "Hello World of email!";
	
	try {
	    Message msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress("admin@example.com", "Example.com Admin"));
	    msg.addRecipient(Message.RecipientType.TO, new InternetAddress("user@example.com", "Mr. User"));
	    msg.setSubject("Your Example.com account has been activated");
	    msg.setText(msgBody);
	    Transport.send(msg);

	} catch (AddressException aException) {
	    // ...
	} catch (MessagingException mExecption) {
	    // ...
	}

*/
