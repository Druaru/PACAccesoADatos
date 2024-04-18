package gestionlibros;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import hibernateconfig.HibernateConfiguracion;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		final String marcos = "-----------------------------------------------------------";
		final String titulo = "\t\t\t BIBLIOTECA";
		
		
		int opcion = 1;

		System.out.println("Inicio del programa...");

		//Configuración de hibernate
		SessionFactory sessionFactory = HibernateConfiguracion.getSessionFactory();
		System.out.println("Configuración de hibernate realizada");

		
		do {
			System.out.println(marcos);
			System.out.println(titulo);
			System.out.println(marcos);
			
			System.out.println("1- Insertar Libro");
			System.out.println("2- Insertar Lector");
			System.out.println("3- Listado de Libros");
			System.out.println("4- Listado de Lectores");			
			System.out.println("5- Ver libro por ID");
			System.out.println("6- Ver lector por ID");
			System.out.println("7- Salir");

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
		} while(opcion < 1 || opcion > 7);



		
		HibernateConfiguracion.closeSession();
		System.out.println("sesion cerrada");
		sessionFactory.close();
	}
}
