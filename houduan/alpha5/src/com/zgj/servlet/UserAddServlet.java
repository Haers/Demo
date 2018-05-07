package com.zgj.servlet;

//添加用户
//所需参数:stuNum

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.zgj.hibernate.*;
import com.zgj.bean.*;

import java.io.PrintWriter;

import org.json.simple.*;


@WebServlet("/UserAddServlet")
public class UserAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserAddServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String stuNum=request.getParameter("stuNum");
		
		JSONObject obj=new JSONObject();
		Session session=null;
		if(stuNum!=null){
			try{
				session=HibernateSessionFactory.getSession();
				session.beginTransaction();
				if(session.get(User.class, stuNum)==null){
					session.save(new User(stuNum));
					session.getTransaction().commit();
					obj.put("status", 0);
					obj.put("type", 4);
					obj.put("data", null);
					obj.put("info", null);
				}
				else{
					obj.put("status", 1);
					obj.put("type", 4);
					obj.put("data", null);
					obj.put("info", "此用户已注册");
				}
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
			obj.put("type", 4);
			obj.put("data", null);
			obj.put("info", "未收到学号信息");
		}
		PrintWriter out=response.getWriter();
		out.write(obj.toJSONString());
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
