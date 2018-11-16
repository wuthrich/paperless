package paperless;

public class ArchivoRAM {

	private byte[] archivo;
	private String nombre;
	private Integer numero;
	
	public String nombrePedazo() {
		
		String nombreSinExtencion = "";

		int i = nombre.lastIndexOf('.');
		if (i > 0) {		    
		    nombreSinExtencion = nombre.substring(0, i);
		}else {
			nombreSinExtencion = nombre;
		}
		
		return nombreSinExtencion+"."+String.format("%03d", numero);
	}
	
	public byte[] getArchivo() {
		return archivo;
	}
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	
}
