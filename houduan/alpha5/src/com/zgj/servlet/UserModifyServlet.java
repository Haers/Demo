<<<<<<< HEAD
package com.zgj.servlet;

//修改某用户信息
//所需参数:stuNum
//可选参数:name,defaultLocation,gender,telephone,pay

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.json.simple.JSONObject;

import com.zgj.bean.User;
import com.zgj.hibernate.HibernateSessionFactory;

@WebServlet("/UserModify")
public class UserModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserModifyServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String stuNum=request.getParameter("stuNum");
		String name=request.getParameter("name");
		String defaultLocation=request.getParameter("defaultLocation");
		String gender=request.getParameter("gender");
		String telephone=request.getParameter("telephone");
		String pay=request.getParameter("pay");
		
		JSONObject obj=new JSONObject();
		Session session=null;
		if(stuNum!=null){
			try{
				session=HibernateSessionFactory.getSession();
				session.beginTransaction();
				
				User user=(User)session.get(User.class, stuNum);
				if(user!=null){
					String modified="";
					if(name!=null){
						user.setName(name);
						modified+="name\t";
					}
					if(defaultLocation!=null){
						user.setDefaultLocation(defaultLocation);
						modified+="defaultLocation\t";
					}
					if(gender!=null){
						user.setGender(Boolean.parseBoolean(gender));
						modified+="gender\t";
					}
					if(telephone!=null){
						user.setTelephone(telephone);
						modified+="telephone\t";
					}
					if(pay!=null){
						user.setPay(pay);
						modified+="pay\t";
					}
					
					session.flush();
					
					obj.put("status", 0);
					obj.put("type", 4);
					obj.put("data", null);
					obj.put("info", "modified\t"+modified);
				}
				else{
					obj.put("status", 1);
					obj.put("type", 4);
					obj.put("data", null);
					obj.put("info", "数据库中未查询到该用户");
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
			obj.put("type", 4);
			obj.put("data", null);
			obj.put("info", "未收到学号信息");
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

//修改某用户信息
//所需参数:stuNum
//可选参数:name,defaultLocation,gender,telephone,pay

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.json.simple.JSONObject;

import com.zgj.bean.User;
import com.zgj.hibernate.HibernateSessionFactory;

@WebServlet("/UserModify")
public class UserModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserModifyServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String stuNum=request.getParameter("stuNum");
		String name=request.getParameter("name");
		String defaultLocation=request.getParameter("defaultLocation");
		String gender=request.getParameter("gender");
		String telephone=request.getParameter("telephone");
		String pay=request.getParameter("pay");
		
		JSONObject obj=new JSONObject();
		Session session=null;
		if(stuNum!=null){
			try{
				session=HibernateSessionFactory.getSession();
				session.beginTransaction();
				
				User user=(User)session.get(User.class, stuNum);
				if(user!=null){
					String modified="";
					if(name!=null){
						user.setName(name);
						modified+="name\t";
					}
					if(defaultLocation!=null){
						user.setDefaultLocation(defaultLocation);
						modified+="defaultLocation\t";
					}
					if(gender!=null){
						user.setGender(Boolean.parseBoolean(gender));
						modified+="gender\t";
					}
					if(telephone!=null){
						user.setTelephone(telephone);
						modified+="telephone\t";
					}
					if(pay!=null){
						user.setPay(pay);
						modified+="pay\t";
					}
					
					session.flush();
					
					obj.put("status", 0);
					obj.put("type", 4);
					obj.put("data", null);
					obj.put("info", "modified\t"+modified);
				}
				else{
					obj.put("status", 1);
					obj.put("type", 4);
					obj.put("data", null);
					obj.put("info", "数据库中未查询到该用户");
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
			obj.put("type", 4);
			obj.put("data", null);
			obj.put("info", "未收到学号信息");
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
