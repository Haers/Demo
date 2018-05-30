package com.zgj.servlet;

//ĳ�û����չ��ȫ����Ϣ
//������� :receiverId

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.zgj.bean.Message;
import com.zgj.bean.User;
import com.zgj.hibernate.HibernateSessionFactory;

@WebServlet("/MessageGetByReceiverServlet")
public class MessageGetByReceiverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MessageGetByReceiverServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String receiverId=request.getParameter("receiverId");
		
		Session session=null;
		JSONObject obj=new JSONObject();
		if(receiverId!=null){
			try{
				session=HibernateSessionFactory.getSession();
				User sender=(User) session.get(User.class, receiverId);
				
				if(sender!=null){

					JSONArray list=new JSONArray();
					Iterator<Message> iterator=sender.getMessagesForReceiverId().iterator();
					while(iterator.hasNext()){
						Message tempmsg=iterator.next();
						JSONObject temp=new JSONObject();
						temp.put("id", tempmsg.getId());
						temp.put("senderId", tempmsg.getUserBySenderId().getStuNum());
						temp.put("sendDate", tempmsg.getSendDate().toString());
						temp.put("sendTime", tempmsg.getSendTime().toString());
						temp.put("msg", tempmsg.getMsg());
						temp.put("fetchLocation", tempmsg.getFetchLocation());
						temp.put("sendLocation", tempmsg.getSendLocation());
						temp.put("isCaught", tempmsg.getIsCaught());
						if(tempmsg.getUserByReceiverId()==null)
							temp.put("receiverId", null);
						else
							temp.put("receiverId", tempmsg.getUserByReceiverId().getStuNum());
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
					obj.put("info", "未查到此接受者");
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
			obj.put("info", "未收到接收者id");
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
