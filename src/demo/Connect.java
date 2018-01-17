package demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import database.Account;
import beans.*;

@WebServlet("/Connect")
public class Connect extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource dSource;

	public void init(ServletConfig config) throws ServletException {
		try {
			InitialContext initialContext = new InitialContext();
			Context env = (Context) initialContext.lookup("java:comp/env");
			dSource = (DataSource) env.lookup("jdbc/webshop");
		} catch (NamingException e) {
			throw new ServletException();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		try {
			con = (Connection) dSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		PrintWriter out = response.getWriter();
		out.println("Connected to database");

		String action = request.getParameter("action");
		if (action == null) {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} else if (action.equals("login")) {
			request.setAttribute("email", "");
			request.setAttribute("password", "");
			request.setAttribute("repeatpassword", "");
			request.setAttribute("message", "");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else if (action.equals("createaccount")) {
			request.setAttribute("email", "");
			request.setAttribute("password", "");
			request.setAttribute("repeatedpassword", "");
			request.setAttribute("message", "");
			request.getRequestDispatcher("/createaccount.jsp").forward(request, response);

		} else {
			out.println("unrecognized action");
			return;
		}
		// use connection
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		try {
			con = (Connection) dSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		PrintWriter out = response.getWriter();
		out.println("Connected to database");

		String action = request.getParameter("action");
		if (action == null) {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} else if (action.equals("dologin")) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			User user = new User(email, password);
			request.setAttribute("email", email);
			request.setAttribute("password", "");

			Account account = new Account(con);

			try {
				if (account.login(email, password)) {
					request.getRequestDispatcher("/loginsuccess.jsp").forward(request, response);
				} else {
					request.setAttribute("message", "email address or password not recognised.");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}
			} catch (SQLException e) {
				// Do something sensible here -- forward to error.jsp etc.
				e.printStackTrace();
			}

		} else if(action.equals("createaccount")) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String repeatedPassword = request.getParameter("repeatpassword");
			
			request.setAttribute("email", email);
			request.setAttribute("password", "");
			request.setAttribute("repeatedpassword", repeatedPassword);
			request.setAttribute("message", "");
			
			if(!password.equals(repeatedPassword)) {
				request.setAttribute("message", "Passwords do not match");
				request.getRequestDispatcher("./createaccount.jsp").forward(request, response);
			} else {
				User user = new User(email, password);
				if(!user.validate()) {
					request.setAttribute("message", user.getMessage());
					request.getRequestDispatcher("/createaccount.jsp").forward(request, response);
				} else {
					Account account = new Account(con);
					try {
						if(account.exists(email)) {
							request.setAttribute("message", "the account already exists");
							request.getRequestDispatcher("/error.jsp").forward(request, response);
						} else {
							account.create(email, repeatedPassword);
							request.setAttribute("email", email);
							request.getRequestDispatcher("/createsuccess.jsp").forward(request, response);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
			}
			}
		} else {
			out.println("unrecognized action");
			return;
		}
		// use connection
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
