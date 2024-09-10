package com.roe21005.controller;



import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.roe21005.service.UserService;

@Controller
public class UserController {
    @RequestMapping("/")
    public String index() {
        System.out.println("/ : Mapping succeed to index");
        return "index";
    }
    
	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException//ֻ����POST����
	{
		
        HttpSession session = request.getSession(false); // �������µ�session��ֻ��ȡ���е�session
        if (session != null && session.getAttribute("username") != null) {
            // session����
            System.out.println("login: Login From Session");
            return "redirect:/game";
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        session = request.getSession();
        session.setAttribute("error", null);
        System.out.println(username+" - "+password);
        
        if (username == null || password == null || password.equals("") || username.equals("")) {
        	model.addAttribute("errorMessage", "�û������벻Ϊ��");
            return "index";
        }
        
        UserService userService = new UserService();
        try {
            userService.login(username, password);
            System.out.println("try : Mapping succeed to login");
            session.setAttribute("username", username);
            // �ж��Ƿ�ѡ���ˡ���ס�ҡ������ѡ���ˣ������ó�����Ч��cookie
            if (remember != null && remember.equals("true")) {
                Cookie cookie = new Cookie("username", username);
                cookie.setMaxAge(60); // ����cookie����ʱ��
                response.addCookie(cookie);
            }
            System.out.println("try: Mapping to game");
            return "redirect:/game";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "index";
        }
        	
	}
	
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getSession().getAttribute("username").toString();
        // ʹcookieʧЧ
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(username)) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        // ʹsessionʧЧ
        request.getSession().invalidate();
        return "redirect:/";
    }


    @RequestMapping("/register")
    public String register(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Mapping succeed to register");
        
        HttpSession session = request.getSession(false);
        session.setAttribute("error", null);
        System.out.println("Mapping succeed to register");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        if (username == null || password == null || password2 == null || password2.equals("") || password.equals("") || username.equals("")) {
            return "register";
        }
        if (!password2.equals(password)) {
            session.setAttribute("error", "�����������벻һ��");
            return "register";
        }
        System.out.println(username + "&" + password);
        UserService userService = new UserService();
        try {
            userService.register(username, password);
            System.out.println("ע��ɹ������ڵ�¼");
            session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("message", "ע��ɹ������ڵ�¼...");
            return "redirect:/game";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.setAttribute("error", e.getMessage());
            return "register";
        }

    }
    
    @RequestMapping("/introduction")
    public String introduce() {
        System.out.println("Mapping succeed to introduction");
        return "introduction";
    }
    
}
