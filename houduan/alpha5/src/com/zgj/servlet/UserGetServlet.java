package com.zgj.servlet;

//��ѯĳ���û���Ϣ
//�������:stuNum

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.zgj.hibernate.HibernateSessionFactory;
import com.zgj.bean.*;

@WebServlet("/UserGetServlet")
public class UserGetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserGetServlet() {
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
				User user=(User)session.get(User.class, stuNum);
				if(user!=null){
					JSONObject jsonUser=new JSONObject();
					jsonUser.put("stuNum", user.getStuNum());
					jsonUser.put("name", user.getName());
					jsonUser.put("defaultLocation", user.getDefaultLocation());
					jsonUser.put("gender", user.getGender());
					jsonUser.put("telephone", user.getTelephone());
					jsonUser.put("pay", user.getPay());
					JSONArray list=new JSONArray();
					list.add(jsonUser);
					obj.put("status", 0);
					obj.put("type", 4);
					obj.put("data", list);
					obj.put("info", null);
				}
				else{
					obj.put("status", 1);
					obj.put("type", 4);
					obj.put("data", null);
					obj.put("info", "未查询到此用户");
				}
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
			obj.put("type", 4);
			obj.put("data", null);
			obj.put("info", "未收到学号");
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
