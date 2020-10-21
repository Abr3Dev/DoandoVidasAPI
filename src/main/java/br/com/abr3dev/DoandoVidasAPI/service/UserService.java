package br.com.abr3dev.DoandoVidasAPI.service;

import java.util.Optional;

import br.com.abr3dev.DoandoVidasAPI.models.User;

public interface UserService {
	
	User register (User user);
	
	Optional<User> findByEmailAndPassword(String email, String password);
	
	Optional<User> findById(Long id);
	
	void deleteById (Long id);
	
	
}
