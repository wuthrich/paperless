Simple proyecto WAR java 8 para Google Cloud (App Engine)
Se deja WAR compilado en target/Paperless-1.war para ser deployado en cualquier servidor que soporte Servlet 3.1 (instalación y ejecutable compilado).
El sitio se puede ver operativo en https://paperless-dot-firestore-216521.appspot.com (extra)

(descripción de lo realizado)
La aplicacion muestra un formulario en index.html con una entrada tipo archivo, la cual es leida desde el servlet upload y manipulada con el singleton
ArchivoAppEngine, el cual lee el InputStream y lo divide en padazos con los cuales hace una lista. El servlet upload sigue a downloadAppEngine.html,
donde se vuelve a llamar al servlet upload mediante GET para conseguir los datos de los pedazos y pintarlos en la pagina como un conjunto de links
que permiten bajar cada pedazo mediante le servlet download (DownloadAppEngine.java).

Nota: 
Con respecto a: "No debe haber restricciones respecto al archivo a cargar", el archivo a cargar no puede sobrepasar 1 MB, 
debido a que el sistema de archivos de App Engine es de solo lectura, y para poder cumplir lo de "No se requiere hacer uso de base de datos.", 
tuve que guardar el archivo en RAM, pense en ocupar algun almacenamiento como Google Cloud Storage https://cloud.google.com/storage/ o algo asi, 
pero era un punto o el otro, y hacerlo asi permitiria que el WAR fuera deployable en cualquier lugar sin mayores configuraciones.
Este proyecto se desarrollo con Eclipse Photon y Google Cloud Tools for Eclipse Version: 1.7.1.201807071456