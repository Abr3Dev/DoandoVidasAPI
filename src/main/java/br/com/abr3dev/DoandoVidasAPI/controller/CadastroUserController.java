package br.com.abr3dev.DoandoVidasAPI.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.abr3dev.DoandoVidasAPI.dtos.RegisterDto;
import br.com.abr3dev.DoandoVidasAPI.models.User;
import br.com.abr3dev.DoandoVidasAPI.response.Response;
import br.com.abr3dev.DoandoVidasAPI.service.UserService;

@RestController
@RequestMapping("api/doandovidas")
@CrossOrigin(origins = "*")
public class CadastroUserController {
	
	private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<Response<RegisterDto>> register(@Valid @RequestBody RegisterDto registerDto, 
			BindingResult result) throws ParseException {
		Response<RegisterDto> response = new Response<RegisterDto>();
		
		validarDadosExistentes(registerDto, result);
		User user = this.converterDtoParaUser(registerDto, result);
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		userService.register(user);
		
		response.setData(this.converterRegisterDto(user)); 
		return ResponseEntity.ok(response);
		
	}
	
	private void validarDadosExistentes(RegisterDto registerDto, BindingResult result) {
		
		this.userService.findByEmail(registerDto.getEmail())
		.ifPresent(user -> result.addError(new ObjectError("user", "E-mail já cadastrado.")));
		
		this.userService.findByCpf(registerDto.getCpf())
		.ifPresent(user -> result.addError(new ObjectError("user", "CPF já cadastrado.")));
		
	}
	
	private User converterDtoParaUser(RegisterDto registerDto, BindingResult result) throws ParseException {
		User user = new User(); 
		user.setName(registerDto.getName());
		user.setEmail(registerDto.getEmail());
		user.setCpf(registerDto.getCpf()); 
		user.setPassword(registerDto.getPassword());
		user.setBirthDate(this.df.parse((registerDto.getBirthDate()))); 
		user.setGender(registerDto.getGender());
		
		return user;
	}
	
	private RegisterDto converterRegisterDto(User user) {
		RegisterDto registerDto = new RegisterDto(); 
		registerDto.setName(user.getName());
		registerDto.setEmail(user.getEmail());
		registerDto.setCpf(user.getCpf());
		registerDto.setPassword(user.getPassword()); 
		registerDto.setBirthDate(this.df.format(user.getBirthDate())); 
		registerDto.setGender(user.getGender());
		
		return registerDto;
	}
	
}
