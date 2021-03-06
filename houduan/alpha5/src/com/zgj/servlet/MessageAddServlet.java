package com.zgj.servlet;

//�����Ϣ
//�������senderId,msg,fetchLocation,sendLocation

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;
import org.json.simple.JSONObject;

import com.zgj.bean.*;
import com.zgj.hibernate.HibernateSessionFactory;

@WebServlet("/MessageAddServlet")
public class MessageAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MessageAddServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String senderId=request.getParameter("senderId");
		String msg=request.getParameter("msg");
		String fetchLocation=request.getParameter("fetchLocation");
		String sendLocation=request.getParameter("sendLocation");
		
		User sender=null;
		
		Session session=null;
		
		JSONObject obj=new JSONObject();
		
		if(senderId!=null&&
				msg!=null&&
				fetchLocation!=null&&
				sendLocation!=null){
			try{
				session=HibernateSessionFactory.getSession();
				sender=(User)session.get(User.class, senderId);
			}catch(Exception e){
				obj.put("status", 1);
				obj.put("type", 4);
				obj.put("data", null);
				obj.put("info", "后台报错"+e.getStackTrace().toString());
				PrintWriter out=response.getWriter();
				out.write(obj.toJSONString());
				out.close();
				e.printStackTrace();
			}finally{
				HibernateSessionFactory.closeSession();
			}
			if(sender!=null){
				Message message=new Message();
				message.setUserBySenderId(sender);
				message.setSendDate(new java.sql.Date(new java.util.Date().getTime()));
				message.setSendTime(new java.sql.Time(new java.util.Date().getTime()));
				message.setMsg(msg);
				message.setFetchLocation(fetchLocation);
				message.setSendLocation(sendLocation);
				message.setIsCaught(false);
				message.setIsDone(false);
				try{
					session=HibernateSessionFactory.getSession();
					session.beginTransaction();
					session.save(message);
					session.getTransaction().commit();
					obj.put("status", 0);
					obj.put("type", 3);
					obj.put("data", null);
					obj.put("info", null);
				}catch(Exception e){
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
				obj.put("info", "未查到此发送者");
			}
		}
		else{
			obj.put("status", 1);
			obj.put("type", 3);
			obj.put("data", null);
			obj.put("info", "信息不完善");
		}
		PrintWriter out=response.getWriter();
		out.write(obj.toJSONString());
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override  
    public void init() throws ServletException {  
        // TODO Auto-generated method stub  
        super.init();  
        String prefix = getServletContext().getRealPath("/");  
        String file = getInitParameter("log4j-init-file");  
        if (file != null) {  
            System.out.println("read log4j.properties:"+prefix + file);  
            PropertyConfigurator.configure(prefix + file);  
        }  
    } 

}
