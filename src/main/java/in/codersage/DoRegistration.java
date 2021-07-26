package in.codersage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DoRegistration
 */
@WebServlet("/DoRegistration")
public class DoRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DoRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String psw = request.getParameter("psw");
		String token = UUID.randomUUID().toString();
		

		try {

			// Initialise driver (Type 4)/class

			//Class.forName("com.mysql.cj.jdbc.Driver");

			// Making connection to the database

			//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/temp", "root", "root");
			
			ServletContext sc = getServletContext();

			Connection conn = (Connection) sc.getAttribute("DB_CONN");
			
			
			// Open the statement to execute the sql
			PreparedStatement preStmt = conn.prepareStatement("insert into login (username, password,activated,token) values (?,?,?,?)");
			//Statement stmt = conn.createStatement();

			preStmt.setString(1, email);
			preStmt.setString(2, psw);
			preStmt.setBoolean(3, false);
			preStmt.setString(4, token);
			
			// execute the sql
					
			
			  int count = preStmt.executeUpdate();
			 
			
			
			if (count == 1) {
				/* System.out.println("Registration done!!!"); */

				String host = "smtp.gmail.com"; // mail server
				final String user = "codersage.in@gmail.com";
				final String password = "Parth@4380";

				Properties prop = new Properties();

				prop.put("mail.smtp.host", host);
				prop.put("mail.smtp.auth", "true");
				prop.put("mail.smtp.port", "465");
				prop.put("mail.smtp.protocol", "smtps");
				prop.put("mail.smtp.ssl.enable", "true");

				Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
				});

				// create a message
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(user));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				message.setSubject("Activate your account");
				message.setText("click on the link to activate your account : http://" + InetAddress.getLocalHost().getHostAddress()
						+ ":9090" + getServletContext().getContextPath() + "/DoVerification?email=" + email + "&token="
						+ token);
				
				Transport.send(message);
				
				out.print("<h1>Kinldy verify your email account using the link send to your email address!!!</h1>");

			} else {
				System.out.println("Registration not done!!!");
			}

			//stmt.close();
			//conn.close();

		} catch ( SQLException | MessagingException e) {
			
			e.printStackTrace();
		}

	}

}
