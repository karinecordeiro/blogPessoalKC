package org.generation.blogpessoal.repository;

import java.util.List;
import java.util.Optional;

import org.generation.blogpessoal.model.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // rodar em outra porta
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // teste por classe

public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	//--------COMEÇO--------------//
	
	@BeforeAll
	void start() {
		usuarioRepository.save(new Usuario(0L, "DJ Cleiton Rasta", "cleitinho@pedra.com", "cabecadegelo", "https://i.imgur.com/FETvs2O.jpg\r\n"));
		usuarioRepository.save(new Usuario(0L, "Laurinha Lero", "laurinha@lero.com", "laura123", "https://i.imgur.com/FETvs2O.jpg\r\n"));
		usuarioRepository.save(new Usuario(0L, "DJ Ednaldo Pereira", "ednaldo@pereira.com", "naovalenada", "https://i.imgur.com/FETvs2O.jpg\r\n"));
		usuarioRepository.save(new Usuario(0L, "Naninha", "naninha@mc.com", "trabalholindo", "https://i.imgur.com/FETvs2O.jpg\r\n"));
		usuarioRepository.save(new Usuario(0L, "Florentina", "flor@entina.com", "dejesus", "https://i.imgur.com/FETvs2O.jpg\r\n"));
	}
	
	@Test
	@DisplayName("Retorna apenas um usuário")
		public void deveRetornarUmUsuario() {
		
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("naninha@mc.com");
		
		assertTrue(usuario.get().getUsuario().equals("naninha@mc.com"));
	}
	
	//----- Busca coisas em comum //O list forma um array; Ele é um array
	@Test
	@DisplayName("Retorna dois usuarios")
	public void deveRetornarDoisUsuarios() {
		
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("DJ");
		assertEquals(2, listaDeUsuarios.size()); //mostra tamanho da lista
		
		//Abaixo get(...) ... é a posição da lista que é se inicia por 0, tem que seguir: 0,1,2...
		assertTrue(listaDeUsuarios.get(0).getNome().equals("DJ Cleiton Rasta")); 
		assertTrue(listaDeUsuarios.get(1).getNome().equals("DJ Ednaldo Pereira"));
	}
	
	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}
	
}
