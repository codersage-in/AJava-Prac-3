package in.codersage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DoLogin
 */
@WebServlet("/DoLogin")
public class DoLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DoLogin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String email = request.getParameter("email");
		String psw = request.getParameter("psw");

		try {
			// Class.forName("com.mysql.cj.jdbc.Driver");
			// Connection conn =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/temp", "root",
			// "root");
			Connection conn = (Connection) getServletContext().getAttribute("DB_CONN");
			PreparedStatement preStmt = conn.prepareStatement("select * from login where username=? and password=?");
			// Statement stmt = conn.createStatement();
			preStmt.setString(1, email);
			preStmt.setString(2, psw);

			ResultSet rs = preStmt.executeQuery();
			// ResultSet rs = stmt.executeQuery("select * from login where username='" +
			// email + "' and password='"+psw+"'");

			String[] rememberMe = request.getParameterValues("remember");
			if (rememberMe != null)
			if (rememberMe[0].equals("remember")) {
				Cookie emailCookie = new Cookie("email", email);
				response.addCookie(emailCookie);
				Cookie pswCookie = new Cookie("psw", psw);
				response.addCookie(pswCookie);
			}

			if (rs.next()) {
				out.print("Successfully logged in!!!");
			} else {
				out.print("Invalid credentials!!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String email = request.getParameter("email");
		String psw = request.getParameter("psw");

		try {
			// Class.forName("com.mysql.cj.jdbc.Driver");
			// Connection conn =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/temp", "root",
			// "root");
			Connection conn = (Connection) getServletContext().getAttribute("DB_CONN");
			PreparedStatement preStmt = conn.prepareStatement("select * from login where username=? and password=?");
			// Statement stmt = conn.createStatement();
			preStmt.setString(1, email);
			preStmt.setString(2, psw);

			ResultSet rs = preStmt.executeQuery();
			// ResultSet rs = stmt.executeQuery("select * from login where username='" +
			// email + "' and password='"+psw+"'");

			String[] rememberMe = request.getParameterValues("remember");

			if (rememberMe[0].equals("remember")) {
				Cookie emailCookie = new Cookie("email", email);
				response.addCookie(emailCookie);
				Cookie pswCookie = new Cookie("psw", psw);
				response.addCookie(pswCookie);
			}

			if (rs.next()) {
				out.print("Successfully logged in!!!");
			} else {
				out.print("Invalid credentials!!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
