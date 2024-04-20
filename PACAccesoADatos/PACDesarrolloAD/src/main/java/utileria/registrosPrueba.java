package utileria;

import java.util.Random;

import persistencia.LectorDAO;

public class registrosPrueba {
	
	//Este método lo he usado como ayuda para insertar usuarios de prueba y no estar constantemente creándolos a mano en cada ejecución
	public static void introducirLectoresDePrueba() {
		LectorDAO lectorDao = new LectorDAO();
		Random random = new Random();
		
		//Generamos arrays de nombres, apellidos y dominios de correo electrónico de prueba
		String[] nombres= {"Juan", "María","Luisa", "Pedro", "David", "Francisco", "Antonia","Norberto", "Carmen", "Matias"};
		String[] apellidos= {"Ruano", "Gonzalez","Torres","Mesas","Fornieles", "Iorga", "Zamora", "Ruiz", "Haz", "Asensio"};
		String[] dominios= {"gmail.com", "hotmail.com", "outlook.com", "yahoo.com"};
		
		for(int i = 0; i < 10; i++) {
			//Generamos un indice aleatorio para selecionar cada uno de los datos
			int indiceNombre = random.nextInt(nombres.length);
			int indiceApellido = random.nextInt(apellidos.length);
			int indiceDominio = random.nextInt(dominios.length);
			
			String email = nombres[indiceNombre].toUpperCase() + apellidos[indiceApellido].toUpperCase() + "@" + dominios[indiceDominio];
			
			lectorDao.crear(nombres[indiceNombre], apellidos[indiceApellido], email);
			
			
		}
	}

	
}
