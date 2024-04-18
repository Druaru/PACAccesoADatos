package entidades;

import java.sql.Date;

public class Prestamo {
	private Long id;
	private Date fec_pres;
	private Date fec_dev;
	private Libro libro;
	private Lector lector;
	
	
	public Prestamo(Date fec_pres, Date fec_dev, Libro libro, Lector lector) {
		super();
		this.fec_pres = fec_pres;
		this.fec_dev = fec_dev;
		this.libro = libro;
		this.lector = lector;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getFec_pres() {
		return fec_pres;
	}


	public void setFec_pres(Date fec_pres) {
		this.fec_pres = fec_pres;
	}


	public Date getFec_dev() {
		return fec_dev;
	}


	public void setFec_dev(Date fec_dev) {
		this.fec_dev = fec_dev;
	}


	public Libro getLibro() {
		return libro;
	}


	public void setLibro(Libro libro) {
		this.libro = libro;
	}


	public Lector getLector() {
		return lector;
	}


	public void setLector(Lector lector) {
		this.lector = lector;
	}
	
	
	
	
}
