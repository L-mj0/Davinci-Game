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
	public String login(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException//只处理POST请求
	{
		
        HttpSession session = request.getSession(false); // 不创建新的session，只获取已有的session
        if (session != null && session.getAttribute("username") != null) {
            // session存在
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
        	model.addAttribute("errorMessage", "用户名密码不为空");
            return "index";
        }
        
        UserService userService = new UserService();
        try {
            userService.login(username, password);
            System.out.println("try : Mapping succeed to login");
            session.setAttribute("username", username);
            // 判断是否选择了“记住我”，如果选择了，则设置长期有效的cookie
            if (remember != null && remember.equals("true")) {
                Cookie cookie = new Cookie("username", username);
                cookie.setMaxAge(60); // 设置cookie过期时间
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
        // 使cookie失效
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
        // 使session失效
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
            session.setAttribute("error", "两次密码输入不一致");
            return "register";
        }
        System.out.println(username + "&" + password);
        UserService userService = new UserService();
        try {
            userService.register(username, password);
            System.out.println("注册成功！正在登录");
            session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("message", "注册成功！正在登录...");
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
