package persistencia;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import entidades.Lector;
import hibernateconfig.HibernateConfiguracion;

public class LectorDAO {

	//Método que muestra todos los lectores.
	public List<Lector> listarTodos() {
		Session session = HibernateConfiguracion.getSessionFactory().openSession();
		List<Lector> lectores = null;

		try {
			//Creamos la consulta para obtenerlos
			Query<Lector> query = session.createQuery("FROM Lector", Lector.class);
			lectores = query.list();
			//Si la lista no esta vacía los mostramos
			if(lectores != null && !lectores.isEmpty()) {
				for (Lector lector : lectores) {
					System.out.print("ID: " + lector.getId() + " - ");
					System.out.print("Nombre: " + lector.getNombre() + " - ");
					System.out.print("Apellido: " + lector.getApellido() + " - ");
					System.out.println("Email: " + lector.getEmail());
					System.out.println("---------------------------------------");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConfiguracion.closeSession();
		}
		return lectores;
	}

	//Método para crear un nuevo Lector
	public void crear(String nombre, String apellido, String email) {

		Lector lector = new Lector(nombre, apellido, email);

		lector.setNombre(nombre);
		lector.setApellido(apellido);
		lector.setEmail(email);

		Session session = HibernateConfiguracion.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.save(lector);
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

	//Este metodo lee solamente un lector, lo devuelve y lo escribe por consola.
	public Lector leerUno(long lectorId) {
		Session session = HibernateConfiguracion.getSessionFactory().openSession();
		Lector lector = null;

		try {
			lector = session.get(Lector.class, lectorId);
			System.out.print("ID: " + lector.getId() + " - ");
			System.out.print("Nombre: " + lector.getNombre() + " - ");
			System.out.print("Apellido: " + lector.getApellido() + " - ");
			System.out.println("Email: " + lector.getEmail());
		} catch (Exception e) {
			System.out.println("No hay un lector con el ID seleccionado");
		} finally {
			HibernateConfiguracion.closeSession();
		}

		return lector;
	}

	//Para modificar un lector.
	public void modificar(Lector lector, String nuevoNombre, String nuevoApellido, String nuevoCorreo) {
		
		Session session = HibernateConfiguracion.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.update(lector);
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
	
	//ESte metodo borra un lector pasando su id.
	public void borrar(long idBorrar) {
		Session session = HibernateConfiguracion.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Lector lector = session.get(Lector.class, idBorrar);
			
			if(lector != null) {
				session.delete(lector);
				System.out.println("Lector borrado correctamente.");
			} else {
				System.out.println("No se encontró ningún lector con el ID escrito.");
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

}
