package com.authentication.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.authentication.models.LoginUser;
import com.authentication.models.User;
import com.authentication.service.UserServices;

@Controller
public class HomeController {
    

	@Autowired
    private UserServices userServ;
    
    @GetMapping("/")
    public String index(Model model) {
    
        // Enlazar objetos User y LoginUser vacíos al JSP
        // para capturar la entrada del formulario
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());
        return "index.jsp";
    }
    
//  REGISTRO
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser, 
            BindingResult result, Model model, HttpSession session) {
    	System.out.println("--- INICIO CONTROLLER REGISTER ---");
//    	Llamar al servicio con el metodo register
    	userServ.register(newUser, result);
        if(result.hasErrors()) {
//        	Vaciar login
        	model.addAttribute("newLogin", new LoginUser());
        	return "index.jsp";     
        }
        else {
        System.out.println("--- SE HA CREADO UN USUARIO ---");
        session.setAttribute("user_id", newUser.getId());
        return "redirect:/home";       	
        }

    }
    
//  LOGIN
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
            BindingResult result, Model model, HttpSession session) {
        System.out.println("--- INICIO DE DE CONTROLLER LOGIN ---");
      
        // Añadir una vez implementado el servicio:
        User user = userServ.login(newLogin, result);
    
        if(result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "index.jsp";
        }
    
        // ¡Sin errores!
        // PARA HACER después: Almacena sus ID de la base de datos en sesión, 
        // en otras palabras, inicia sus sesiones.
        session.setAttribute("user_id", user.getId());
        System.out.println("--- LOGIN EXITOSO ---");
        return "redirect:/home";
    }
//  ACCESO AL HOME - DASHBOARD
    @GetMapping("/home")
    public String home ( Model model, HttpSession session) {
    	if (session.isNew() || session.getAttribute("user_id") == null) {
    		return "redirect:/";
    	}
    	else {
    		User loggedUser = userServ.retrieveUser((Long) session.getAttribute("user_id"));
    		model.addAttribute("loggedUser", loggedUser);
    		return "home.jsp";
    	}
    }
//    LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	session.invalidate();
    	return "redirect:/";
    }
    
}