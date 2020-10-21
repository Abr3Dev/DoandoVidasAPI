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
import br.com.abr3dev.DoandoVidasAPI.models.Content;
import br.com.abr3dev.DoandoVidasAPI.models.User;
import br.com.abr3dev.DoandoVidasAPI.response.Response;
import br.com.abr3dev.DoandoVidasAPI.service.ContentService;
import br.com.abr3dev.DoandoVidasAPI.service.UserService;

@RestController
@RequestMapping("api/doandovidas")
@CrossOrigin(origins = "*")
public class CadastroUserController {
	
	private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ContentService contentService;
	
	@PostMapping("/register")
	public ResponseEntity<Response<RegisterDto>> register(@Valid @RequestBody RegisterDto registerDto, 
			BindingResult result) throws ParseException {
		Response<RegisterDto> response = new Response<RegisterDto>();
		
		validarDadosExistentes(registerDto, result);
		Content content = this.converterDtoParaContent(registerDto);
		User user = this.converterDtoParaUser(registerDto, result);
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		 
		contentService.register(content);
		user.setContent(content);
		userService.register(user);
		
		response.setData(this.converterRegisterDto(user)); 
		return ResponseEntity.ok(response);
		
	}
	
	private void validarDadosExistentes(RegisterDto registerDto, BindingResult result) {
		
		this.userService.findByEmailAndPassword(registerDto.getEmail(), registerDto.getPassword())
		.ifPresent(user -> result.addError(new ObjectError("user", "Usuário já existente.")));
		
	}
	
	private User converterDtoParaUser(RegisterDto registerDto, BindingResult result) throws ParseException {
		User user = new User(); 
		user.setEmail(registerDto.getEmail());
		user.setCpf(registerDto.getCpf()); 
		user.setPassword(registerDto.getPassword());
		user.setBirthDate(this.df.parse((registerDto.getBirthDate()))); 
		user.setGender(registerDto.getGender());
		
		return user;
	}
	
	private Content converterDtoParaContent(RegisterDto registerDto) {
		Content content = new Content(); 
		content.setMessage(registerDto.getMessage());
		content.setVideo(registerDto.getVideo());
		
		return content;
	}
	
	private RegisterDto converterRegisterDto(User user) {
		RegisterDto registerDto = new RegisterDto(); 
		registerDto.setEmail(user.getEmail());
		registerDto.setCpf(user.getCpf());
		registerDto.setPassword(user.getPassword()); 
		registerDto.setBirthDate(this.df.format(user.getBirthDate())); 
		registerDto.setGender(user.getGender());
		registerDto.setMessage(user.getContent().getMessage());
		registerDto.setVideo(user.getContent().getVideo());
		
		return registerDto;
	}
	
}
