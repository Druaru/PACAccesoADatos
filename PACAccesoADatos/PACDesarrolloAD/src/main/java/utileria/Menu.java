package utileria;

import java.util.Scanner;

public class Menu {

	final static String marcos = "===========================================================";
	final static String separador = "\n------------------------------------------------------------";
	final static String titulo = "\t\t\t BIBLIOTECA";

	//Este método es el encargado de aportar el menú al método main.
	public static int mostrarMenu(Scanner scanner) {

		
			int opcion = 1;

			do {
				System.out.println(marcos);
				System.out.println(titulo);
				System.out.println(marcos);

				
				System.out.println(separador);
				System.out.println("1- Insertar Libro");
				System.out.println("2- Listado de Libros");
				System.out.println("3- Ver libro por ID");
				System.out.println("4- Modificar Libro");
				System.out.println("5- Eliminar Libro");
				System.out.println(separador);
				System.out.println("6- Insertar Lector");
				System.out.println("7- Listado de Lectores");
				System.out.println("8- Ver lector por ID");
				System.out.println("9- Modificar Lector");
				System.out.println("10- Eliminar Lector");
				System.out.println(separador);
				System.out.println("11- Asignar libro a lector");
				System.out.println("12- Devolución de libros");
				System.out.println("13- Libros prestados por Lector");
				System.out.println("14- Libros disponibles para prestamo");
				System.out.println("15- Historial de préstamos por lector");
				System.out.println(separador);



				System.out.println("16- Introducir usuarios de prueba");
				System.out.println("0- Salir");

				System.out.println(marcos);

				System.out.print("Seleccione una opción: ");

				try {
					opcion = scanner.nextInt();


				} catch (Exception e) {
					System.err.println(e);
					System.err.println("Tienes que seleccionar una opcion numérica");
					scanner.nextLine();
					opcion = 0;

					continue;
				}
			} while (opcion < 1 || opcion > 16);
			scanner.nextLine();
			return opcion;
		}

	

}
