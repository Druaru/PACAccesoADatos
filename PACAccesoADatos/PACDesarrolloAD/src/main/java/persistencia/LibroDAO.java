package persistencia;

import java.sql.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import entidades.Libro;
import hibernateconfig.HibernateConfiguracion;

public class LibroDAO {

	//Método para listar todos los libros
	public List<Libro> listarTodos() {
		Session session = HibernateConfiguracion.getSessionFactory().openSession();
		List<Libro> libros = null;

		try {
			//Consulta para obtener todos los libros
			Query<Libro> query = session.createQuery("FROM Libro", Libro.class);
			libros = query.list();
			//Si la lista no esta vacía mostramos el detalle de cada libro.
			if(libros != null && !libros.isEmpty()) {
				for (Libro libro : libros) {
					System.out.print("ID: " + libro.getId() + " - ");
					System.out.print("Título: " + libro.getTitulo() + " - ");
					System.out.print("Autor: " + libro.getAutor() + " - ");
					System.out.print("Año: " + libro.getAnho() + " - ");
					System.out.println("Disponible: " + (libro.isDisponible() ? "Sí": "No"));
					System.out.println("---------------------------------------");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConfiguracion.closeSession();
		}
		return libros;
	}

	//Método para crear un nuevo libro
	public void crear(String titulo, String autor, Date anho, boolean isPrestado) {

		Libro libro = new Libro(titulo, autor, anho, isPrestado);

		libro.setTitulo(titulo);
		libro.setAutor(autor);
		libro.setAnho(anho);
		libro.setDisponible(isPrestado);
	
		Session session = HibernateConfiguracion.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.save(libro);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			HibernateConfiguracion.closeSession();
		}
	}

	//Obtener libro por ID
	@SuppressWarnings("null")
	public Libro leerUno(long libroId) {
		Session session = HibernateConfiguracion.getSessionFactory().openSession();
		Libro libro = null;

		try {
			libro = session.get(Libro.class, libroId);
			if(libro!=null) {
				System.out.print("ID: " + libro.getId() + " - ");
				System.out.print("Título: " + libro.getTitulo() + " - ");
				System.out.print("Autor: " + libro.getAutor() + " - ");
				System.out.print("Anho: " + libro.getAnho()+ " - ");
				System.out.println("Disponible: " + (libro.isDisponible() ? "Sí": "No"));
	
			} else {
				System.err.println("El libro con ID " + + libro.getId() + " no se ha encontrado.");
			}
			
			
		} catch (Exception e) {
			System.err.println("Error leyendo el libro, ha de ser una ID válido");
		} finally {
			HibernateConfiguracion.closeSession();
		}

		return libro;
	}

	//Para modificar un libro
	public boolean modificar(Libro libro, String nuevoTitulo, String nuevoAutor, Date nuevoAnho, boolean isDisponible) {
		
		Session session = HibernateConfiguracion.getSessionFactory().openSession();
		Transaction transaction = null;
		boolean exito = false;

		try {
			transaction = session.beginTransaction();
			session.update(libro);
			transaction.commit();
			exito = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			HibernateConfiguracion.closeSession();
		}
		return exito;

	}

	//Metodo para borrar un libro.
	public void borrar(long idBorrar) {
		Session session = HibernateConfiguracion.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Libro libro= session.get(Libro.class, idBorrar);
			
			if(libro != null) {
				session.delete(libro);
				System.out.println("Libro borrado correctamente.");
			} else {
				System.out.println("No se encontró ningún Libro con el ID escrito.");
			}
			
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			HibernateConfiguracion.closeSession();
		}

	}
	
	//Este metodo cambia la disponibilidad del libro.
	public void cambiarDisponibilidad(Long idLibro, boolean disponible) {
	    Session session = HibernateConfiguracion.getSessionFactory().openSession();
	    Transaction transaction = null;

	    try {
	        transaction = session.beginTransaction();
	        
	        // Cargar el libro mediante su ID
	        Libro libro = session.load(Libro.class, idLibro);
	        
	        // Establecer el nuevo valor de disponibilidad
	        libro.setDisponible(disponible);
	        
	        // Actualizar el objeto libro en la base de datos
	        session.update(libro);
	        
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        HibernateConfiguracion.closeSession();
	    }
	}
	//Método que muestra los libros disponibles.
	public void mostrarLibrosDisponibles() {
	    Session session = HibernateConfiguracion.getSessionFactory().openSession();
	    try {
	        CriteriaBuilder builder = session.getCriteriaBuilder();
	        CriteriaQuery<Libro> criteriaQuery = builder.createQuery(Libro.class);
	        Root<Libro> root = criteriaQuery.from(Libro.class);
	        criteriaQuery.select(root).where(builder.isTrue(root.get("disponible")));
	        Query<Libro> query = session.createQuery(criteriaQuery);
	        List<Libro> librosDisponibles = query.getResultList();
	        if (librosDisponibles.isEmpty()) {
	            System.out.println("No hay libros disponibles para préstamo.");
	        } else {
	            System.out.println("Libros disponibles para préstamo:");
                System.out.println("-----------------------------------------------------------------------------");

	            for (Libro libro : librosDisponibles) {
	                System.out.println("ID: " + libro.getId() + " - Título: " + libro.getTitulo() + " - Autor: " + libro.getAutor());
	                System.out.println("----------------------------------------------------");
	            }
	        }
	    } finally {
	        session.close();
	    }
	}


}
