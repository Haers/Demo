package com.zgj.servlet;

//将isCaught改为false
//所需参数id

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

@WebServlet("/MessageUncatchServlet")
public class MessageUncatchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MessageUncatchServlet() {
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
					message.setIsCaught(false);
					message.setUserByReceiverId(null);
					
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
			obj.put("info", "所给信息不完善");
		}
		PrintWriter out=response.getWriter();
		out.write(obj.toJSONString());
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
