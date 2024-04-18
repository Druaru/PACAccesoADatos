package entidades;

import java.sql.Date;

public class Libro {
	private Long id;
	private String titulo;
	private String autor;
	private Date anho;
	private boolean disponible;
	
	public Libro(String titulo, String autor, Date anho, boolean disponible) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.anho = anho;
		this.disponible = disponible;
	}

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

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Date getAnho() {
		return anho;
	}

	public void setAnho(Date anho) {
		this.anho = anho;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	
	
	
	
}
