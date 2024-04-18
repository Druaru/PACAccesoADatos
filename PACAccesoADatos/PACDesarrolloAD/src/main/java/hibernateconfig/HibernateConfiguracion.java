package hibernateconfig;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateConfiguracion {
	
	// Para indicar que queremos usar hibernate definimos las interfaces
			static Configuration cfg = new Configuration().configure();
			 // Única instancia de sessionFactory en nuestra sesión
			static SessionFactory sessionFactory = cfg
					.buildSessionFactory(new StandardServiceRegistryBuilder().configure().build());
			static Session session = sessionFactory.openSession();

			public static SessionFactory getSessionFactory(){
				return sessionFactory;
				
			}
			public static void closeSession(){
				session.close();
				
			}

}
