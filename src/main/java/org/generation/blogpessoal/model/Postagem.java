package org.generation.blogpessoal.model;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity /*transforma em tabela*/
@Table(name="tb_postagens") /*nome da tabela*/

public class Postagem {
	
	@Id /*dizer que vai ser uma primary key*/
	
	@GeneratedValue(strategy=GenerationType.IDENTITY) /*auto-increment*/
	
	private Long id; /*é a forma que o java entende "Long=bigint"*/
	
	@NotNull /*campo obrigatório*/
	private String titulo;
	
	@NotNull
	@Size(min=4, max=50) /*impede que o usuário faça além do que o premeditado "pode ser só min ou só max"*/
	private String texto;
	
	@UpdateTimestamp
	private LocalDateTime data; /*pega a data e hora do seu pc*/
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
}
