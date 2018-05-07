<<<<<<< HEAD
package com.zgj.servlet;

//此单完成
//所需参数:id

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.json.simple.JSONObject;

import com.zgj.hibernate.HibernateSessionFactory;
import com.zgj.bean.*;

@WebServlet("/MessageDoneServlet")
public class MessageDoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MessageDoneServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String id=request.getParameter("id");
		
		Session session=null;
		JSONObject obj=new JSONObject();
		if(id!=null){
			try{
				session=HibernateSessionFactory.getSession();
				session.beginTransaction();
				
				Message message=(Message)session.get(Message.class, Integer.parseInt(id));
				
				if(message!=null){
					message.setIsDone(true);
					
					session.flush();
					
					obj.put("status", 0);
					obj.put("type", 3);
					obj.put("data", null);
					obj.put("info", null);
				}
				else{
					obj.put("status", 1);
					obj.put("type", 3);
					obj.put("data", null);
					obj.put("info", "数据库中未查询到此消息");
				}
				
				session.getTransaction().commit();
			}catch(Exception e){
				session.getTransaction().rollback();
				if(obj.isEmpty()){
					obj.put("status", 1);
					obj.put("type", 4);
					obj.put("data", null);
					obj.put("info", "后台报错"+e.getStackTrace().toString());
				}
				PrintWriter out=response.getWriter();
				out.write(obj.toJSONString());
				out.close();
				e.printStackTrace();
			}finally{
				HibernateSessionFactory.closeSession();
			}
		}
		else{
			obj.put("status", 1);
			obj.put("type", 3);
			obj.put("data", null);
			obj.put("info", "无id");
		}
		PrintWriter out=response.getWriter();
		out.write(obj.toJSONString());
		out.close();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
=======
package com.zgj.servlet;

//此单完成
//所需参数:id

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.json.simple.JSONObject;

import com.zgj.hibernate.HibernateSessionFactory;
import com.zgj.bean.*;

@WebServlet("/MessageDoneServlet")
public class MessageDoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MessageDoneServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String id=request.getParameter("id");
		
		Session session=null;
		JSONObject obj=new JSONObject();
		if(id!=null){
			try{
				session=HibernateSessionFactory.getSession();
				session.beginTransaction();
				
				Message message=(Message)session.get(Message.class, Integer.parseInt(id));
				
				if(message!=null){
					message.setIsDone(true);
					
					session.flush();
					
					obj.put("status", 0);
					obj.put("type", 3);
					obj.put("data", null);
					obj.put("info", null);
				}
				else{
					obj.put("status", 1);
					obj.put("type", 3);
					obj.put("data", null);
					obj.put("info", "数据库中未查询到此消息");
				}
				
				session.getTransaction().commit();
			}catch(Exception e){
				session.getTransaction().rollback();
				if(obj.isEmpty()){
					obj.put("status", 1);
					obj.put("type", 4);
					obj.put("data", null);
					obj.put("info", "后台报错"+e.getStackTrace().toString());
				}
				PrintWriter out=response.getWriter();
				out.write(obj.toJSONString());
				out.close();
				e.printStackTrace();
			}finally{
				HibernateSessionFactory.closeSession();
			}
		}
		else{
			obj.put("status", 1);
			obj.put("type", 3);
			obj.put("data", null);
			obj.put("info", "无id");
		}
		PrintWriter out=response.getWriter();
		out.write(obj.toJSONString());
		out.close();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
>>>>>>> a244db9743e1a09607ddde6efe917c91a0ead07b
