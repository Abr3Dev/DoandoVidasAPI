package br.com.abr3dev.DoandoVidasAPI.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.abr3dev.DoandoVidasAPI.dtos.UserDto;
import br.com.abr3dev.DoandoVidasAPI.models.Content;
import br.com.abr3dev.DoandoVidasAPI.models.User;
import br.com.abr3dev.DoandoVidasAPI.response.Response;
import br.com.abr3dev.DoandoVidasAPI.service.ContentService;
import br.com.abr3dev.DoandoVidasAPI.service.UserService;

@RestController
@RequestMapping("api/doandovidas")
@CrossOrigin(origins = "*")
public class UserController {
	
	private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ContentService contentService;
	
	@GetMapping("/user/{email}/{password}")
	public ResponseEntity<Response<UserDto>> login(@PathVariable(value = "email") String email, @PathVariable(value = "password") String password) {
		
		Response<UserDto> response = new Response<UserDto>(); 
		Optional<User> user = userService.findByEmailAndPassword(email, password);
		
		if(!user.isPresent()) {
			response.getErrors().add("Usuário não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.converterUserDto(user.get())); 
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<Response<UserDto>> update(@PathVariable("id") Long id, 
			@Valid @RequestBody UserDto userDto, BindingResult result) throws ParseException {
		
		Response<UserDto> response = new Response<UserDto>(); 
		validarUser(userDto, result); 
		userDto.setId(id);
		Content content = converterDtoParaContent(userDto);
		User user = converterDtoParaUser(userDto, result);
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		content = contentService.register(content); 
		user.setContent(content);
		user = userService.register(user); 
		response.setData(converterUserDto(user));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
		Response<String> response = new Response<String>();
		Optional<User> user = userService.findById(id);
		
		if(!user.isPresent()) {
			response.getErrors().add("Erro ao remover usuário. Usuário não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		
		userService.deleteById(id);
		response.setData("Usuário removido com sucesso");
		return ResponseEntity.ok(response);
	}
	
	private void validarUser(UserDto userDto, BindingResult result) {
		if (userDto.getId() == null) {
			result.addError(new ObjectError("user", "Usuário não informado"));
			return;
		}
		
		if (userDto.getContentId() == null) {
			result.addError(new ObjectError("cont", "Conteudo não preenchido"));
			return;
		}
		
		Optional<User> user = userService.findById(userDto.getId());
		if(!user.isPresent()) {
			result.addError(new ObjectError("user", "Usuário não encontrado, ID inexistente"));
		}
	
	}
	
	private UserDto converterUserDto(User user) {
		UserDto userDto = new UserDto(); 
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setCpf(user.getCpf());
		userDto.setPassword(user.getPassword());
		userDto.setBirthDate(this.df.format(user.getBirthDate()));
		userDto.setGender(user.getGender());
		userDto.setContentId(user.getContent().getId());
		userDto.setMessage(user.getContent().getMessage());
		userDto.setVideo(user.getContent().getVideo());
		
		return userDto;
	}
	
	
	private User converterDtoParaUser(UserDto userDto, BindingResult result) throws ParseException {
		User user = new User(); 
		
		if(userDto.getId() != null) {
			Optional<User> usr = userService.findById(userDto.getId());
			if(usr.isPresent()) {
				user = usr.get();
			} else {
				result.addError(new ObjectError("user", "Usuário não encontrado."));
			}
		} else {
			user.setContent(new Content());
			user.getContent().setId(userDto.getContentId());
			user.getContent().setMessage(userDto.getMessage());
			user.getContent().setVideo(userDto.getVideo());
		}
		
		user.setEmail(userDto.getEmail());
		user.setCpf(userDto.getCpf()); 
		user.setPassword(userDto.getPassword());
		user.setBirthDate(this.df.parse((userDto.getBirthDate()))); 
		user.setGender(userDto.getGender());
		
		return user;
	}
	
	private Content converterDtoParaContent(UserDto userDto) {
		Content content = new Content(); 
		content.setId(userDto.getContentId());
		content.setMessage(userDto.getMessage());
		content.setVideo(userDto.getVideo());
		
		return content;
	}
	
}
