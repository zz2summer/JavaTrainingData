package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import javax.swing.text.html.HTMLDocument.Iterator;
import java.util.*;

import beans.*;
import dao.*;

public class Index extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Index() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		doPost(request,response);
	}
	
	/**
	 * 用于查询数据库中的所有书
	 * @param request
	 * @param response
	 */
	public void querryAllBooks(HttpServletRequest request, HttpServletResponse response){
		try{
		BookDAO bdao = new BookDAO();
		ArrayList al = bdao.querryAllBooks();
		request.setAttribute("allBooks", al);
		request.getRequestDispatcher("../userView/userpage.jsp").forward(request, response);
		}catch(Exception e){
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户登陆时对用户身份的验证
	 * @param request
	 * @param response
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) {

		
		response.setContentType("UTF-8");
		HttpSession session;
		try {
		session = request.getSession();
		String username = new String(request.getParameter("username").getBytes("ISO-8859-1"),"UTF-8");
		String password = request.getParameter("password");

		User u = new User();
		u.setName(username);
		u.setPass(password);
		UserDAO udao = new UserDAO();
	
			request.setCharacterEncoding("UTF-8");
			
			if (udao.userTest(u)) {
				
				User userInfo = udao.querryUseName(username);
				session.setAttribute("userInfo",userInfo);
				this.querryAllBooks(request, response);
			} else {
				request.getRequestDispatcher("../userView/login.jsp").forward(
						request,response);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * 接受不同的请求并且响应不同的功能
	 */
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		if (request.getParameter("requestType").equals("login")) {

			login(request, response);
			
		}
		
		if(request.getParameter("requestType").equals("homePage")){
			this.querryAllBooks(request, response);
		}
		if(request.getParameter("requestType").equals("left")) {
			setCateBook(request,response);
		}
		
		if(request.getParameter("requestType").equals("right")) {
			setBook(request,response);
		}
		if(request.getParameter("requestType").equals("register")){
			
			register(request, response);
		}
	}
	
	/**
	 * 查询图书类别和最新的五本书
	 * @param request
	 * @param response
	 */
	public void setCateBook(HttpServletRequest request, HttpServletResponse response) {
		ArrayList categoryList;
		BookCategoryDAO bcdao = new BookCategoryDAO();
		categoryList = bcdao.querryCategory();
		request.setAttribute("category", categoryList);
	
		ArrayList bookList;
		BookDAO bookdao = new BookDAO();
		bookList = bookdao.querryAllBooks();
		request.setAttribute("books", bookList);
		
		try {
			request.getRequestDispatcher("../index.jsp").forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询最新的5本书并且显示出来
	 * @param request
	 * @param response
	 */
	public void setBook(HttpServletRequest request, HttpServletResponse response) {
		ArrayList bookList;
		BookDAO bookdao = new BookDAO();
		bookList = bookdao.querryAllBooks();
		request.setAttribute("books", bookList);
		
		try {
			request.getRequestDispatcher("../index.jsp").forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 用于处理一个用户的注册信息,将信息插入到数据库
	 * @param request
	 * @param response
	 */
	public void register(HttpServletRequest request, HttpServletResponse response) {
		
		try{
			//request.setCharacterEncoding("UTF-8");
			//response.setCharacterEncoding("UTF-8");
		//从JSP中取的用户的信息
		String name = new String(request.getParameter("username").getBytes("ISO-8859-1"),"UTF-8");
		String pass =request.getParameter("password");
		String realName =new String(request.getParameter("realname").getBytes("ISO-8859-1"),"UTF-8");
		String sex = new String(request.getParameter("gender").getBytes("ISO-8859-1"),"UTF-8");
		String ag = "0";
		int age;
		if(request.getParameter("age")==null){
			
			age = 0;
		}else{
			
			age = Integer.parseInt(request.getParameter("age"));
		}
		
		String email = new String(request.getParameter("email").getBytes("ISO-8859-1"),"UTF-8");
		String phone = request.getParameter("connectnumber");
		String address = new String(request.getParameter("address").getBytes("ISO-8859-1"),"UTF-8");
		
		//从系统中获得注册日期
		Calendar cal = Calendar.getInstance();
		Date registerDate = new Date(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
		//强制设置用户级别为用户级
		int role = 1;
		int id = 0;
		//将用户信息封装入USER对象
		User user = new User(id,name,pass,sex,age,role,realName,phone,email,address,registerDate);
		//UserDAO实例化
		UserDAO ud = new UserDAO();
		//判断用户名是否存在
			if((ud.querryUseName(name))==null){
				ud.insertUser(user);	
				response.sendRedirect("/BookShop/userView/registSuccess.jsp");
			}else{
				String alert ="该用户名已存在";
				if(pass == null){
					alert = "密码不能为空";
					request.setAttribute("alert", alert);
				}
				//若用户名存在,给用户提示,FORWARD到注册页面
				
				request.setAttribute("alert", alert);
				request.getRequestDispatcher("../userView/register.jsp").forward(request, response);		
			}
		}catch(IOException e){
			e.printStackTrace();
		}catch(ServletException se){
			se.printStackTrace();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
