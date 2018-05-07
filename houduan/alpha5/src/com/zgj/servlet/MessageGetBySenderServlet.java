<<<<<<< HEAD
package com.zgj.servlet;

//某用户发送的全部消息
//所需参数:senderId

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.zgj.hibernate.HibernateSessionFactory;
import com.zgj.bean.*;

@WebServlet("/MessageGetBySenderServlet")
public class MessageGetBySenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MessageGetBySenderServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String senderId=request.getParameter("senderId");
		
		Session session=null;
		JSONObject obj=new JSONObject();
		if(senderId!=null){
			try{
				session=HibernateSessionFactory.getSession();
				User sender=(User) session.get(User.class, senderId);
				
				if(sender!=null){

					JSONArray list=new JSONArray();
					Iterator<Message> iterator=sender.getMessagesForSenderId().iterator();
					while(iterator.hasNext()){
						Message tempmsg=iterator.next();
						JSONObject temp=new JSONObject();
						temp.put("id", tempmsg.getId());
						temp.put("senderId", tempmsg.getUserBySenderId().getStuNum());
						temp.put("sendDate", tempmsg.getSendDate());
						temp.put("sendTime", tempmsg.getSendTime());
						temp.put("msg", tempmsg.getMsg());
						temp.put("fetchLocation", tempmsg.getFetchLocation());
						temp.put("sendLocation", tempmsg.getSendLocation());
						temp.put("isCaught", tempmsg.getIsCaught());
						temp.put("receivedId", tempmsg.getUserByReceiverId().getStuNum());
						temp.put("isDone", tempmsg.getIsDone());
						list.add(temp);
					}
					
					obj.put("status", 0);
					obj.put("type", 1);
					obj.put("data", list);
					obj.put("info", null);
				}
				else{
					obj.put("status", 1);
					obj.put("type", 1);
					obj.put("data", null);
					obj.put("info", "未查询到此用户");
				}
				
			}catch(Exception e){
				if(obj.isEmpty()){
					obj.put("status", 1);
					obj.put("type", 1);
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
			obj.put("type", 1);
			obj.put("data", null);
			obj.put("info", "无学号");
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

//某用户发送的全部消息
//所需参数:senderId

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.zgj.hibernate.HibernateSessionFactory;
import com.zgj.bean.*;

@WebServlet("/MessageGetBySenderServlet")
public class MessageGetBySenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MessageGetBySenderServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String senderId=request.getParameter("senderId");
		
		Session session=null;
		JSONObject obj=new JSONObject();
		if(senderId!=null){
			try{
				session=HibernateSessionFactory.getSession();
				User sender=(User) session.get(User.class, senderId);
				
				if(sender!=null){

					JSONArray list=new JSONArray();
					Iterator<Message> iterator=sender.getMessagesForSenderId().iterator();
					while(iterator.hasNext()){
						Message tempmsg=iterator.next();
						JSONObject temp=new JSONObject();
						temp.put("id", tempmsg.getId());
						temp.put("senderId", tempmsg.getUserBySenderId().getStuNum());
						temp.put("sendDate", tempmsg.getSendDate());
						temp.put("sendTime", tempmsg.getSendTime());
						temp.put("msg", tempmsg.getMsg());
						temp.put("fetchLocation", tempmsg.getFetchLocation());
						temp.put("sendLocation", tempmsg.getSendLocation());
						temp.put("isCaught", tempmsg.getIsCaught());
						temp.put("receivedId", tempmsg.getUserByReceiverId().getStuNum());
						temp.put("isDone", tempmsg.getIsDone());
						list.add(temp);
					}
					
					obj.put("status", 0);
					obj.put("type", 1);
					obj.put("data", list);
					obj.put("info", null);
				}
				else{
					obj.put("status", 1);
					obj.put("type", 1);
					obj.put("data", null);
					obj.put("info", "未查询到此用户");
				}
				
			}catch(Exception e){
				if(obj.isEmpty()){
					obj.put("status", 1);
					obj.put("type", 1);
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
			obj.put("type", 1);
			obj.put("data", null);
			obj.put("info", "无学号");
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
