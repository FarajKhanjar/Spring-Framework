package ajbc.learn.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@WebServlet("/hello")
//public class HelloServlet extends HttpServlet {
//
//	private static final long serialVersionUID = 7695038024636034527L;
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		
//		PrintWriter out = resp.getWriter();
//		out.println("Hello from Faraj");
//	}
//
//}


@Controller
public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = 3158870339648745875L;

	@RequestMapping("/sayHello")
	public String SayHello() {
		System.out.println("In Say Hello");
		return "Hello from Server";
	}

}
