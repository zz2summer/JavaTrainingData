package mystore;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */

public class LoginServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

        /**
         * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
         */
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                // TODO Auto-generated method stub
        }

        /**
         * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
         */
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                String name = request.getParameter("login");
                String pwd = request.getParameter("password");
                String sql = "select * from clients where name='"+name+"' and password='"+pwd+"'";
               book.bk users=new book.bk();
                ResultSet rs = users.executeQuery(sql);
                HttpSession session =request.getSession();

                try {
                        if(rs.next()){

                                session.setAttribute("username", name);
                                response.sendRedirect("index.jsp");
                                rs.close();
                        }else{
                                rs.close();
                                response.sendRedirect("login.html");
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
		}
        }


}
