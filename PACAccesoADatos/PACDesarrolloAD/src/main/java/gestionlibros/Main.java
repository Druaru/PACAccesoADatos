package gestionlibros;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import entidades.Lector;
import entidades.Libro;
import hibernateconfig.HibernateConfiguracion;
import persistencia.LectorDAO;
import persistencia.LibroDAO;
import persistencia.PrestamoDAO;
import utileria.Menu;
import utileria.registrosPrueba;

public class Main {

	public static void main(String[] args) {

		//Declaración de variables
		boolean salir = false;
		Scanner scanner = new Scanner(System.in);
		LectorDAO lectorDAO = new LectorDAO();
		LibroDAO libroDAO = new LibroDAO();
		PrestamoDAO prestamoDAO = new PrestamoDAO();

		System.out.println("Inicio del programa...");

		// Configuración de hibernate
		SessionFactory sessionFactory = HibernateConfiguracion.getSessionFactory();
		System.out.println("Configuración de hibernate realizada");

		//Flujo de ejecucion de programa es un do while del que solo saldremos pulsando la opcion 0.
		do {
			int opcion = Menu.mostrarMenu(scanner);

			switch (opcion) {
			// Este es el caso de insertar libro, solicitaremos al usuario los datos del libro mediante la clase scanner.
			case 1:
				System.out.println("Le has dado al " + opcion);
				System.out.println("Inserte el titulo del libro: ");
				String titulo = scanner.nextLine();
				System.out.println("Inserte el autor del libro: ");
				String autor = scanner.nextLine();
				do {
					//Tenemos que asegurarnos que el usuario introduzca la fecha en el formato que nosotros necesitamos para trabajar.
					//Por eso creamos esta estructura de repetición.
					System.out.println("Inserte el año del libro: (formato: yyyy-MM-dd) ");
					String anho = scanner.nextLine();
					java.util.Date fecha = null;
					try {
						fecha = new SimpleDateFormat("yyyy-MM-dd").parse(anho);
					} catch (Exception e) {
						System.err.println("Formato de fecha incorrecto");
						continue;
					}
					java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
					// Mediante una estructura de repetición también forzaremos a que se escriba true o false en la consola.
					while (true) {
						System.out.println("¿Está disponible(true, false)? ");
						String disponibleStr = scanner.nextLine().trim().toLowerCase();
						if (disponibleStr.equals("true") || disponibleStr.equals("false")) {
							boolean isDisponible = Boolean.parseBoolean(disponibleStr);
							libroDAO.crear(titulo, autor, fechaSQL, isDisponible);
							break;
						} else {
							System.err.println("Introduzca un valor válido (true o false)");
						}
					}
					break;
				} while (true);
				break;

			//En este case lo que hacemos es llamar al metodo listar todos unicamente, el método ya se encarga de los system.print
			case 2:
				System.out.println("Le has dado al " + opcion);
				libroDAO.listarTodos();
				break;
			// Este case nos mostrará un libro buscado por ID, en caso de no existir el metodo devolverá un mensaje de que no existe
			case 3:
				System.out.println("Le has dado al " + opcion);
				long idLibro = 0;
				boolean idValido = false;

				/*Esta estructura la repito bastante a lo largo del switch case, basicamente lo que hago es crear una bandera que cambiará a
				true cuando el usuario ingrese un dato en concreto que estoy solicitando.
				Seguido de la comprobación de que el libro/lector existe, para no continuar con la ejecución en caso de que no exista.
				*/
				
				while (!idValido) {
					System.out.println("Escribe el ID del libro que quieres consultar: ");
					String input = scanner.nextLine();

					try {
						idLibro = Long.parseLong(input);
						idValido = true;
					} catch (NumberFormatException e) {
						System.err.println("Error: Debes ingresar un número válido.");
					}
				}

				try {
					libroDAO.leerUno(idLibro);
				} catch (Exception e) {
					System.err.println("El libro con ID " + idLibro + " no se ha encontrado.");
				}
				break;
				

			//En este case lo que hacemos es modificar un libro, pidiendo todos los nuevos datos al usuario por consola.
			case 4:
				System.out.println("Le has dado al " + opcion);
				long idModLibro = 0;
				idValido = false;
				
				/*Esta estructura la repito bastante a lo largo del switch case, basicamente lo que hago es crear una bandera que cambiará a
				true cuando el usuario ingrese un dato en concreto que estoy solicitando.
				Seguido de la comprobación de que el libro/lector existe, para no continuar con la ejecución en caso de que no exista.
				*/

				while (!idValido) {

					try {
						System.out.println("Escribe el Id del libro que quieres modificar: ");
						String input = scanner.nextLine();
						idModLibro = Long.parseLong(input);
						idValido = true;
					} catch (NumberFormatException e) {
						System.err.println("Error: Debes ingresar un número válido.");
					}
				}

				// Verificar si el libro existe
				if (libroDAO.leerUno(idModLibro) == null) {
					System.out.println("El libro con ID " + idModLibro + " no se ha encontrado.");
					break;
				}
				System.out.println("Escribe el nuevo título para el libro: ");
				String nuevoTitulo = scanner.nextLine();
				System.out.println("Escribe el nuevo autor para el libro: ");
				String nuevoAutor = scanner.nextLine();
				System.out.println("Escribe la nueva fecha para el libro:(formato: yyyy-MM-dd) ");
				String nuevaFecha = scanner.nextLine();

				java.util.Date nuevaFechaDate = null;
				try {
					nuevaFechaDate = new SimpleDateFormat("yyyy-MM-dd").parse(nuevaFecha);
				} catch (Exception e) {
					System.err.println("Formato de fecha incorrecto");
					return;
				}

				java.sql.Date nuevaFechaSQL = new java.sql.Date(nuevaFechaDate.getTime());

				System.out.println("Escribe la disponibilidad del libro: ");
				boolean nuevaDisponibilidad = scanner.nextBoolean();

				Libro libro = new Libro(idModLibro, nuevoTitulo, nuevoAutor, nuevaFechaSQL, nuevaDisponibilidad);

				libroDAO.modificar(libro, nuevoTitulo, nuevoAutor, nuevaFechaSQL, nuevaDisponibilidad);

				break;
				
			//Case en el que eliminaremos libro por Id
			case 5:
				System.out.println("Le has dado al " + opcion);
				long idBorrarLibro = 0;
				idValido = false;
				
				/*Esta estructura la repito bastante a lo largo del switch case, basicamente lo que hago es crear una bandera que cambiará a
				true cuando el usuario ingrese un dato en concreto que estoy solicitando.
				Seguido de la comprobación de que el libro/lector existe, para no continuar con la ejecución en caso de que no exista.
				*/

				while (!idValido) {
					System.out.println("Escribe el ID del libro que quieres eliminar: ");
					String input = scanner.nextLine();

					try {
						idBorrarLibro = Long.parseLong(input);
						idValido = true;
					} catch (NumberFormatException e) {
						System.err.println("Error: Debes ingresar un número válido.");
					}
				}
				libroDAO.borrar(idBorrarLibro);
				break;
				
			// En esta opción crearemos un nuevo lector pidiendo cada uno de los datos al usuario.
			case 6:
				System.out.println("Le has dado al " + opcion);
				System.out.println("Inserte el nombre del lector: ");
				String nombre = scanner.nextLine();
				System.out.println("Inserte el apellido del lector: ");
				String apellido = scanner.nextLine();
				System.out.println("Inserte el email del lector: ");
				String email = scanner.nextLine();

				lectorDAO.crear(nombre, apellido, email);

				break;
			//Mostramos un listado de lectores.	
			case 7:
				System.out.println("Le has dado al " + opcion);
				lectorDAO.listarTodos();

				break;
			// Mostrar un lector concreto por ID	
			case 8:
				System.out.println("Le has dado al " + opcion);
				long idLector = 0;
				idValido = false;

				while (!idValido) {
					System.out.println("Escribe el ID del lector que quieres consultar: ");
					String input = scanner.nextLine();

					try {
						idLector = Long.parseLong(input);
						idValido = true;
					} catch (NumberFormatException e) {
						System.err.println("Error: Debes ingresar un número válido.");
					}
				}

				lectorDAO.leerUno(idLector);
				break;
			
			//Caso para la modificación de un lector pidiendo todos los datos nuevos por consola.
			case 9:
				System.out.println("Le has dado al " + opcion);
				long idMod = 0;
				idValido = false;
				
				/*Esta estructura la repito bastante a lo largo del switch case, basicamente lo que hago es crear una bandera que cambiará a
				true cuando el usuario ingrese un dato en concreto que estoy solicitando.
				Seguido de la comprobación de que el libro/lector existe, para no continuar con la ejecución en caso de que no exista.
				*/

				while (!idValido) {
					System.out.println("Escribe el ID del lector que quieres modificar: ");
					String input = scanner.nextLine();

					try {
						idMod = Long.parseLong(input);
						idValido = true;
					} catch (NumberFormatException e) {
						System.err.println("Error: Debes ingresar un número válido.");
					}
				}
				if (lectorDAO.leerUno(idMod) == null) {
					System.out.println("El Lector con ID " + idMod + " no se ha encontrado.");
					break;
				}
				System.out.println("Escribe el nuevo nombre para el lector: ");
				String nuevoNombre = scanner.nextLine();
				System.out.println("Escribe el nuevo apellido para el lector: ");
				String nuevoApellido = scanner.nextLine();
				System.out.println("Escribe el nuevo correo para el lector: ");
				String nuevoCorreo = scanner.nextLine();

				Lector lector = new Lector(idMod, nuevoNombre, nuevoApellido, nuevoCorreo);

				lectorDAO.modificar(lector, nuevoNombre, nuevoApellido, nuevoCorreo);

				break;
			// case para eliminar lector.	
			case 10:
				System.out.println("Le has dado al " + opcion);
				long idBorrar = 0;
				idValido = false;

				while (!idValido) {
					System.out.println("Escribe el ID del lector que quieres eliminar: ");
					String input = scanner.nextLine();
					try {
						idBorrar = Long.parseLong(input);
						idValido = true;
					} catch (NumberFormatException e) {
						System.err.println("Error: Debes ingresar un número válido.");
					}
				}

				lectorDAO.borrar(idBorrar);
				break;
			//En los cases de aqui abajo la validación que he estado realizando hay que realizarla para dos entidades, libro y lector.
			case 11:
				System.out.println("Le has dado al " + opcion);

				long idPrestamoLector = 0;
				long idPrestamoLibro = 0;

				// Solicitar el ID del lector
				while (true) {
					System.out.println("Introduzca el ID del lector para realizar el préstamo: ");
					if (scanner.hasNextLong()) {
						idPrestamoLector = scanner.nextLong();
						scanner.nextLine();
						break;
					} else {
						System.err.println("Error: Debes ingresar un número válido.");
						scanner.nextLine(); 
					}
				}

				// Verificar si el lector existe
				Lector lectorPrestamo = lectorDAO.leerUno(idPrestamoLector);
				if (lectorPrestamo == null) {
					System.out.println("No existe un lector con ID " + idPrestamoLector);
					break;
				}

				// Solicitar el ID del libro
				while (true) {
					System.out.println("Introduzca el ID del libro que se le va a prestar: ");
					if (scanner.hasNextLong()) {
						idPrestamoLibro = scanner.nextLong();
						scanner.nextLine();
						break;
					} else {
						System.err.println("Error: Debes ingresar un número válido.");
						scanner.nextLine();
					}
				}

				// Verificar si el libro existe
				Libro libroPrestamo = libroDAO.leerUno(idPrestamoLibro);
				if (libroPrestamo == null) {
					System.out.println("No existe un libro con ID " + idPrestamoLibro);
					break;
				}

				// Obtener la fecha actual
				java.sql.Date fechaExpe = new Date(System.currentTimeMillis());
				java.sql.Date fechaDevo = null;

				// Realizar el préstamo
				prestamoDAO.gestionarPrestamo(fechaExpe, fechaDevo, idPrestamoLibro, idPrestamoLector, true);
				libroDAO.cambiarDisponibilidad(idPrestamoLibro, false);
				break;

			//Para la devolucion de libros.
			case 12:
				System.out.println("Le has dado al " + opcion);

				long idDevolucionLector = 0;
				long idDevolucionLibro = 0;

				// Solicitar el ID del lector
				while (true) {
					System.out.println("Introduzca el ID del lector para realizar la devolución: ");
					if (scanner.hasNextLong()) {
						idDevolucionLector = scanner.nextLong();
						scanner.nextLine();
						break;
					} else {
						System.err.println("Error: Debes ingresar un número válido.");
						scanner.nextLine();
					}
				}

				// Verificar si el lector existe
				Lector lectorDevolucion = lectorDAO.leerUno(idDevolucionLector);
				if (lectorDevolucion == null) {
					System.out.println("No existe un lector con ID " + idDevolucionLector);
					break; 
				}

				// Solicitar el ID del libro
				while (true) {
					System.out.println("Introduzca el ID del libro que se va a devolver: ");
					if (scanner.hasNextLong()) {
						idDevolucionLibro = scanner.nextLong();
						scanner.nextLine();
						break;
					} else {
						System.out.println("Error: Debes ingresar un número válido.");
						scanner.nextLine();
					}
				}

				// Verificar si el libro existe
				Libro libroDevolucion = libroDAO.leerUno(idDevolucionLibro);
				if (libroDevolucion == null) {
					System.out.println("No existe un libro con ID " + idDevolucionLibro);
					break;
				}

				// Obtener la fecha actual
				fechaExpe = null;
				fechaDevo = new Date(System.currentTimeMillis());

				// Realizar la devolución
				prestamoDAO.gestionarPrestamo(fechaExpe, fechaDevo, idDevolucionLibro, idDevolucionLector, false);
				libroDAO.cambiarDisponibilidad(idDevolucionLibro, true);

				break;
			// Nos mostrara los libros que un lector determinado por ID tiene en un momento dado en su poder
			case 13:
				System.out.println("Le has dado al " + opcion);
				idLector = 0;
				// Solicitamos el ID del lector
				while (true) {
					System.out.println("Introduce el Id del lector a consultar");
					if (scanner.hasNextLong()) {
						idLector = scanner.nextLong();
						scanner.nextLine();
						break;
					} else {
						System.err.println("Error: Debes ingresar un número válido.");
						scanner.nextLine();
					}
				}
				// Verificar si el lector existe
				lectorPrestamo = lectorDAO.leerUno(idLector);
				if (lectorPrestamo == null) {
					System.out.println("No existe un lector con ID " + idLector);
					break;
				}

				// Obtenemos los libros del lector con el id asociado a la entrada del usuario.
				prestamoDAO.obtenerLibrosPorLector(idLector);

				break;
			// Muestra los libros que estan disponibles para ser prestados.
			case 14:
				System.out.println("Le has dado al " + opcion);
				libroDAO.mostrarLibrosDisponibles();
				break;

			//Mostramos el historial de un lector (Libros que ha cogido y devuelto)
			case 15:
				System.out.println("Le has dado al " + opcion);
				idLector = 0;
				// Solicitar el ID del lector
				while (true) {
					System.out.println("Introduce el ID del lector que quieres consultar su historial: ");
					if (scanner.hasNextLong()) {
						idLector = scanner.nextLong();
						scanner.nextLine();
						break;
					} else {
						System.err.println("Error: Debes ingresar un número válido.");
						scanner.nextLine();
					}
				}

				// Verificar si el lector existe
				lectorPrestamo = lectorDAO.leerUno(idLector);
				if (lectorPrestamo == null) {
					System.out.println("No existe un lector con ID " + idLector);
					break;
				}

				// Obtenemos los libros del lector con el ID asociado a la entrada del usuario.
				prestamoDAO.obtenerHistorialPorLector(idLector);
				break;
			case 16:
				System.out.println("Le has dado al " + opcion);
				registrosPrueba.introducirLectoresDePrueba();
				break;
			case 0:
				System.out.println("Le has dado al " + opcion);
				salir = true;
				break;
			default:
				System.out.println("Introduce un numero valido");

			}
		} while (!salir);

		HibernateConfiguracion.closeSession();
		System.out.println("sesion cerrada");
		sessionFactory.close();
	}
}
