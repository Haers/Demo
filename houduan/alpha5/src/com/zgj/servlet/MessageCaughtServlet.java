package com.zgj.servlet;

//��Ϣ�ѱ��ӵ�
//���������id,receievrId

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

@WebServlet("/MessageCaughtServlet")
public class MessageCaughtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MessageCaughtServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String id=request.getParameter("id");
		String receiverId=request.getParameter("receiverId");
		
		Session session=null;
		JSONObject obj=new JSONObject();
		if(id!=null&&receiverId!=null){
			try{
				session=HibernateSessionFactory.getSession();
				session.beginTransaction();
				
				Message message=(Message)session.get(Message.class, Integer.parseInt(id));
				if(message!=null){
					User receiver=(User)session.get(User.class, receiverId);
					if(receiver!=null){
						message.setIsCaught(true);
						message.setUserByReceiverId(receiver);
						
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
						obj.put("info", "���ݿ���δ�鵽����");
					}
				}
				else{
					obj.put("status", 1);
					obj.put("type", 3);
					obj.put("data", null);
					obj.put("info", "���ݿ���δ�鵽����Ϣ");
				}
				
				session.getTransaction().commit();
			}catch(Exception e){
				session.getTransaction().rollback();
				if(obj.isEmpty()){
					obj.put("status", 1);
					obj.put("type", 4);
					obj.put("data", null);
					obj.put("info", "��̨����"+e.getStackTrace().toString());
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
			obj.put("info", "������Ϣ������");
		}
		PrintWriter out=response.getWriter();
		out.write(obj.toJSONString());
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}