package gestionlibros;

import org.hibernate.SessionFactory;

import hibernateconfig.HibernateConfiguracion;
import utileria.Menu;


public class Main {

	public static void main(String[] args) {
		


		System.out.println("Inicio del programa...");

		//Configuración de hibernate
		SessionFactory sessionFactory = HibernateConfiguracion.getSessionFactory();
		System.out.println("Configuración de hibernate realizada");

		
		int opcion = Menu.mostrarMenu();
		
		switch (opcion) {
			case 1:
				System.out.println("Le has dado al " + opcion);
				break;
			case 2:
				System.out.println("Le has dado al " + opcion);
				break;
			case 3:
				System.out.println("Le has dado al " + opcion);
				break;
			case 4:
				System.out.println("Le has dado al " + opcion);
				break;
			case 5:
				System.out.println("Le has dado al " + opcion);
				break;
			case 6:
				System.out.println("Le has dado al " + opcion);
				break;
			default:
				System.out.println("Le has dado al " + opcion);
		}
		
		
		


		
		HibernateConfiguracion.closeSession();
		System.out.println("sesion cerrada");
		sessionFactory.close();
	}
}
