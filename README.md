# PACAccesoADatos
PAC de Acceso a datos ILERNA, simulación biblioteca

Desarrollar una aplicación en Java utilizando Hibernate para gestionar una
biblioteca, permitiendo la manipulación de datos relacionados con Libros, Lectores
(usuarios de la biblioteca) y las relaciones entre ellos, como los préstamos de libros.
Consideraciones Adicionales
Se valorará el uso de buenas prácticas en el diseño del código y la estructura del
proyecto.
Es importante manejar correctamente las transacciones y asegurar la integridad de
los datos en la base de datos.
3.2. Descripción
Los alumnos deberán implementar una solución que utilice Hibernate para
interactuar con una base de datos MySQL, diseñada para gestionar los libros de
una biblioteca y su asignación a los lectores. La aplicación debe permitir la
inserción, consulta, actualización y eliminación de datos de libros y lectores, así
como gestionar los préstamos de libros.
Especificaciones Técnicas
Configurar Hibernate para trabajar con una base de datos MySQL. Deben utilizarse
las configuraciones recomendadas para asegurar una conexión eficiente y segura.
3.3. Modelar las Entidades:
Libro: Debe contener atributos como id, titulo, autor, año de publicación y
disponible (un booleano que indica si el libro está disponible para préstamo).
Lector: Debe incluir id, nombre, apellido, email y cualquier otro atributo que
consideres relevante.
Préstamo: Esta entidad gestionará los préstamos de libros a los lectores. Deberá
incluir id, fecha de préstamo, fecha de devolución y las referencias a las entidades
Libro y Lector.
3.4. Implementar Operaciones CRUD (Crear, Leer, Actualizar, Borrar):
Para libros y lectores.
Gestión de préstamos, incluyendo asignar un libro a un lector y registrar la
devolución.
3.5. Consultas:
Libros actualmente prestados a un lector.
Libros disponibles para préstamo.
Historial de préstamos por lector.
3.7. Pruebas Unitarias:
Se deben implementar pruebas para validar las funcionalidades desarrolladas.

Para la ejecución y prueba de esta 
------------------------------------------------------------------------------------------------------
Para la ejecución y prueba de esta aplicación es necesario la instalación de eclipse, MYSQL y crear una base de datos que se llame bbddpac
(O bien cambiar el archivo de configuración colocando una base de datos con distinto nombre), que será donde se crearan las tablas con hibernate
y se trabajara sobre ella.

Además, para probarlo será necesario que en el archivo hibernate.cfg.xml localizado en src/main/resources sea modificado con las credenciales 
de acceso a la base de datos que cada uno tenga. (He dejado marcadas las properties que hay que cambiar)
Una vez este esas cosas cambiadas, ya podremos probarla. Para ello probaremos todos los posibles casos, probando las opciones una a una.

He creado una ayuda para la introduzcción de Lectores, un método en el paquete de utilería que lo que hace es que genera 10 lectores mezclando
nombres, apellidos y dominios aleatorios, para facilitar las pruebas y no introducir tantos datos.

Las pruebas que he realizado son:

1-	Insertar libro: 
    a.	Insertar una fecha en un formato que no sea el solicitado.
    b.	Insertar un dato distinto a true o false en la disponibilidad.
2-	Consultar un libro:
    a.	Consultar un libro que no existe, arrojará un mensaje informativo.
    b.	Escribir caracteres no numéricos en la solicitud del id. Arrojara un error.
3-	Eliminar un libro 
    a.	Consultar un libro que no existe, arrojará un mensaje informativo.
    b.	Escribir caracteres no numéricos en la solicitud del id. Arrojara un error.
4-	Consultar un Lector:
    a.	Consultar un Lector que no existe, arrojará un mensaje informativo.
    b.	Escribir caracteres no numéricos en la solicitud del id. Arrojara un error.
5-	Eliminar un Lector 
    a.  Consultar un libro que no existe, arrojará un mensaje informativo.
    b.	Escribir caracteres no numéricos en la solicitud del id. Arrojara un error.
6-	Asignar libro a lector 
    a.	Escribir un id de un lector que no existe
    b.	Escribir caracteres no numéricos en la solicitud del id del lector. Arrojara un error.
    c.  Asignar un libro que no existe
    d.  Escribir caracteres no numéricos en la solicitud del id del libro. Arrojara un error.
7-	Devolución de libros
    a.	Devolver un libro que no existe. Arrojara un error.
    b.	Devolver un libro que no esté asignado. Arrojara un error.
    c.	Escribir caracteres no numéricos en la solicitud del id del libro. Arrojara un error.
8-	Libros prestados por Lector
    a.	Escribir un lector que no existe, lanzará un mensaje informativo.
    b.	Escribir caracteres no numéricos en la solicitud del id del lector. Arrojara un error.
9-	Historial de préstamos por lector
    a.  Escribir un lector que no existe, lanzará un mensaje informativo.
    b.  Escribir caracteres no numéricos en la solicitud del id del lector. Arrojara un error.

