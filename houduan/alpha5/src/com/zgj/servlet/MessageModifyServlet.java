package com.zgj.servlet;

//修改某条消息
//所需参数:id,
//可选参数:msg,fetchLocation,sendLocation

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.json.simple.JSONObject;

import com.zgj.bean.Message;
import com.zgj.hibernate.HibernateSessionFactory;

@WebServlet("/MessageModifyServlet")
public class MessageModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public MessageModifyServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String id=request.getParameter("id");
		String msg=request.getParameter("msg");
		String fetchLocation=request.getParameter("fetchLocation");
		String sendLocation=request.getParameter("sendLocation");
		
		JSONObject obj=new JSONObject();
		Session session=null;
		if(id!=null){
			try{
				session=HibernateSessionFactory.getSession();
				session.beginTransaction();
				
				Message message=(Message)session.get(Message.class, Integer.parseInt(id));
				if(message!=null){
					String modified="";
					if(msg!=null){
						message.setMsg(msg);
						modified+="msg\t";
					}
					if(fetchLocation!=null){
						message.setFetchLocation(fetchLocation);
						modified+="fetchLocation\t";
					}
					if(sendLocation!=null){
						message.setSendLocation(sendLocation);
						modified+="sendLocation\t";
					}
					
					session.flush();
					
					obj.put("status", 0);
					obj.put("type", 3);
					obj.put("data", null);
					obj.put("info", "modified:\t"+modified);
				}
				else{
					obj.put("status", 1);
					obj.put("type", 3);
					obj.put("data", null);
					obj.put("info", "未查询到此消息");
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
			obj.put("info", "未收到消息id");
		}
		PrintWriter out=response.getWriter();
		out.write(obj.toJSONString());
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
