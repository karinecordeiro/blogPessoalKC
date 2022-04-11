package org.generation.blogpessoal.service;

import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;

import org.generation.blogpessoal.model.Usuario;
import org.generation.blogpessoal.model.UsuarioLogin;
import org.generation.blogpessoal.repository.UsuarioRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service

public class UsuarioService {

	//abaixo regra de negocio, cadastrar usuario
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	//tem que ser public aqui embaixo
	
	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
		
		//abaixo saber se o usuario já existe
		
		if(usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) 
		{
			return Optional.empty();
		}
		
		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		return Optional.of(usuarioRepository.save(usuario));
		
		}
	
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		
		
		if(usuarioRepository.findById(usuario.getId()).isPresent()) 
		{
			Optional<Usuario> atualizarUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());
			if((atualizarUsuario.isPresent()) && (atualizarUsuario.get().getId() !=usuario.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			return Optional.ofNullable(usuarioRepository.save(usuario));
		}
		
			return Optional.empty();
			
	}
		
		public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin){
			
			Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());
			
			if(usuario.isPresent()) {
				if(compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {
					usuarioLogin.get().setId(usuario.get().getId());
					usuarioLogin.get().setNome(usuario.get().getNome());
					usuarioLogin.get().setFoto(usuario.get().getFoto());
					usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
					usuarioLogin.get().setSenha(usuario.get().getSenha());
					
					return usuarioLogin;
				
				}
		}
			return Optional.empty();
			
		}
		
		//abaixo tudo que se refere a logar
		
		private boolean compararSenhas(String senhaDigitada, String senhaDoBanco)
		{
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			return encoder.matches(senhaDigitada, senhaDoBanco);
		}
		
		private String criptografarSenha(String senha) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			return encoder.encode(senha);
		}
		
		private String gerarBasicToken(String usuario, String senha) {
			String token = usuario + ":" +senha;
			byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
			
			return "Basic "+ new String(tokenBase64);
		}
	
}
	
	
