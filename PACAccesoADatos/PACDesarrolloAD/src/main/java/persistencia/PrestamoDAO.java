package persistencia;

import java.sql.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import entidades.Lector;
import entidades.Libro;
import entidades.Prestamo;
import hibernateconfig.HibernateConfiguracion;

public class PrestamoDAO {

	//Este método se encarga de gestionar los préstamos y devoluciones de libros.
	public void gestionarPrestamo(Date fec_pres, Date fec_dev, Long idLibro, Long idLector, boolean esPrestamo) {
		Session session = HibernateConfiguracion.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			// Cargamos las entidades libro y lector mediante su id
			Libro libro = session.get(Libro.class, idLibro);
			Lector lector = session.get(Lector.class, idLector);

			// Verificamos si el libro está disponible para préstamo
			if (libro == null) {
				throw new IllegalArgumentException("El libro no existe.");
			}

			// Si es un préstamo, verificamos si el libro está disponible
			if (esPrestamo && !libro.isDisponible()) {
				System.err.println("El libro que intenta reservar no está disponible.");
			}

			// Creamos la entidad préstamo para asignar los valores
			Prestamo prestamo = new Prestamo();
			prestamo.setLibro(libro);
			prestamo.setLector(lector);

			// Si es un préstamo, establecemos la fecha de prestación y marcamos el libro
			// como no disponible
			if (esPrestamo) {
				prestamo.setFec_pres(fec_pres);
			} else { // Si es una devolución, establecemos la fecha de devolución y marcamos el libro
						// como disponible
				prestamo.setFec_dev(fec_dev);
			}

			// Guardamos las entidades en la BBDD
			session.save(prestamo);

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
	}
	

	//Este método nos devolvera los libros que tiene disponibles un determinado lector por ID (Libros que tiene en su poder)
	@SuppressWarnings("unchecked")
	public List<Prestamo> obtenerLibrosPorLector(Long idLector) {
		Session session = HibernateConfiguracion.getSessionFactory().openSession();

		// Creamos criteria para la entidad préstamo
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Prestamo.class);

		// Agregamos las restricciones.
		criteria.createAlias("lector", "l");
		criteria.createAlias("libro", "lib");
		criteria.add(Restrictions.eq("l.id", idLector)); // Filtrar por el lector específico
		criteria.add(Restrictions.eq("lib.disponible", false)); // Filtrar por libros no disponibles

		// Obtenemos la lista de préstamos
		List<Prestamo> prestamos = criteria.list();

		if (!prestamos.isEmpty()) {
			System.out.println("El lector seleccionado tiene actualmente los siguientes libros asignados: ");
			System.out.println("----------------------------------------------------------------------------");

			for (Prestamo prestamo : prestamos) {
				String titulo = prestamo.getLibro().getTitulo();
				String lector = prestamo.getLector().getNombre();
				String apellido = prestamo.getLector().getApellido();

				System.out.print("Título del libro: " + titulo + " - ");
				System.out.print("Nombre del lector: " + lector + " - ");
				System.out.println("Apellido del lector: " + apellido);
				System.out.println("---------------------------------------");
			}
		} else {
			System.out.println("El lector no tiene libros asignados");
		}

		return prestamos;
	}

	// Este método nos devuelve una lista con los libros que ha tenido y devuelto un determinado lector obtenido por ID.
	@SuppressWarnings("deprecation")
	public List<Prestamo> obtenerHistorialPorLector(Long idLector) {
		Session session = HibernateConfiguracion.getSessionFactory().openSession();

		// Creamos criteria para la entidad préstamo
		Criteria criteria = session.createCriteria(Prestamo.class);

		// Agregamos las restricciones.
		criteria.createAlias("lector", "l");
		criteria.createAlias("libro", "lib");
		criteria.add(Restrictions.eqOrIsNull("l.id", idLector));
		criteria.add(Restrictions.eqOrIsNull("lib.disponible", true));

		// Obtenemos la lista de prestamos
		@SuppressWarnings("unchecked")
		List<Prestamo> prestamos = criteria.list();

		if (!prestamos.isEmpty()) {
			System.out.println("El Historial de libros del lector es el siguiente: ");
			System.out.println("----------------------------------------------------------------------------");

			for (Prestamo prestamo : prestamos) {
				String titulo = prestamo.getLibro().getTitulo();
				String lector = prestamo.getLector().getNombre();
				String apellido = prestamo.getLector().getApellido();
				Date fechaPrestamo = prestamo.getFec_pres();
				Date fechaDevolucion = prestamo.getFec_dev();

				System.out.print("Título del libro: " + titulo + " - ");
				System.out.print("Nombre del lector: " + lector + " - ");
				System.out.print("Apellido del lector: " + apellido + " - ");
				System.out.print("Fecha de préstamo: " + fechaPrestamo + " - ");
				System.out.println("Fecha de devolución: " + fechaDevolucion);

				System.out.println("---------------------------------------");

			}
		} else {
			System.out.println("El historial del Lector esta vacío.");
		}

		return prestamos;

	}

}
