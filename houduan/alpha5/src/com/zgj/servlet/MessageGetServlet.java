package com.zgj.servlet;

//全部消息
//所需参数:无

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

import com.zgj.bean.*;
import com.zgj.hibernate.HibernateSessionFactory;

@WebServlet("/MessageGetServlet")
public class MessageGetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MessageGetServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		List<Message> messages=new LinkedList<Message>();
		Session session=null;
		JSONObject obj=new JSONObject();
		try{
			session=HibernateSessionFactory.getSession();
			String sql="from Message message";
			Query query=session.createQuery(sql);
			messages=query.list();
			
			JSONArray list=new JSONArray();
			Iterator<Message> iterator=messages.iterator();
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
		PrintWriter out=response.getWriter();
		out.write(obj.toJSONString());
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
