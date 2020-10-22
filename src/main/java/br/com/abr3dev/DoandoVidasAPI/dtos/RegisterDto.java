package br.com.abr3dev.DoandoVidasAPI.dtos;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

public class RegisterDto {
	
	@NotBlank(message="Nome não pode ser vazio")
	private String name;
	
	@NotBlank(message="E-mail não pode ser vazio")
	@Email(message="E-mail inválido")
	private String email; 
	
	@NotBlank(message="CPF não pode ser vazio")
	@CPF(message="CPF inválido")
	private String cpf; 
	
	@NotBlank(message="Senha não pode ser vazia")
	@Size(min=8, message="É necessario uma senha com mais de 5 caracteres")
	private String password; 
	
	@NotBlank(message="Data de nascimento não pode ser vazia")
	private String birthDate;
	
	@NotBlank(message="Genero precisa ser informado")
	private String gender;
	
	@Size(max=300, message="Tamanho da mensagem deve ser de 300 caracteres.")
	private String message; 
	
	private String video;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}
	
}
