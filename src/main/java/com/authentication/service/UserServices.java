package com.authentication.service;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.authentication.models.LoginUser;
import com.authentication.models.User;
import com.authentication.repository.UserRepository;



@Service
public class UserServices {
    
    @Autowired
    private UserRepository userRepo;
    
    
    public UserServices(UserRepository userRepo) {
    	this.userRepo = userRepo;
    }
    
//  Métodos utiles
    public List<User> selectAllFromUsers() {
    	return userRepo.findAll();
    }   
    
// REGISTRAR NUEVO USUARIO
    public User register(User newUser, BindingResult result) {
//      VALIDAR SI EXISTE UN USUARIO CON EL EMAIL
    	if (userRepo.findByEmail(newUser.getEmail()).isPresent()) {
    		result.rejectValue("email", "Unique", "Este email ya está siendo usado");
    	}
//    	VALIDAR SI EXISTE UN USUARIO CON EL MISMO NOMBRE DE USUARIO
    	if (userRepo.findByUserName(newUser.getUserName()).isPresent()) {
    		result.rejectValue("userName", "Unique", "El nombre de usuario ya está en uso");
    	}
//    	VALIDAR QUE LAS PASSWORDS COINCIDAN EN AMBOS CAMPOS DE REGISTRO
    	if (!newUser.getPassword().equals(newUser.getConfirm())) {
    		result.rejectValue("confirm", "Matches", "Las contraseñas deben coincidir");
    	}
    	if (result.hasErrors()) {
    		return null;
    	}
    	else {
    		// BCrypt
    		String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
    		newUser.setPassword(hashed);
    		return userRepo.save(newUser);	
    	}
    }
    
     
    public User login(LoginUser newLoginObject, BindingResult result) {
//      VALIDAR SI EXISTEN ERRORES EN EL LOGIN
    	if ( result.hasErrors()) {
    		return null;
    	}
//      ENCONTRAR MAIL EN LA DB
        Optional<User> probUser = userRepo.findByEmail(newLoginObject.getEmail());
        if (!probUser.isPresent()) {
        	result.rejectValue("email", "Unique", "El email no se encuentra registrado");
        	return null;
        }
//      SI SE ENCUENTRA, GUARDAR AL USUARIO PROBABLE COMO VARIABLE PARA OPERAR
        User user = probUser.get();
//      ENCONTRAR LA PASSWORD Y VERIFICAR SI COINCIDEN
        if (!BCrypt.checkpw(newLoginObject.getPassword(), user.getPassword())) {
        	result.rejectValue("password", "Matches", "Contraseña equivocada");
        }
        if (result.hasErrors()) {
        	return null;
        }
//      SI EL LOGIN HA SIDO EXITOSO
        else {
        	return user;
        }
    }
    
    
//   RECUPERAR USUARIO PARA PROCEDER AL DASHBOARD
    public User retrieveUser ( Long id) {
    	Optional<User> optionalUser = userRepo.findById(id);
    	if (optionalUser.isPresent()) {
    		return optionalUser.get();
    	}
    	else {
    		return null;
    	}
    }
    
    
    
    
    
}
