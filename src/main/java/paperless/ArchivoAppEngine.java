package paperless;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Part;

public class ArchivoAppEngine {

	public final static ArchivoAppEngine instancia = new ArchivoAppEngine();

	private List<ArchivoRAM> pedazos;

	private String nombre;

	private ArchivoAppEngine() {
	}
	
	public ArchivoRAM traerArchivoRAM(Integer i) {
		if(null!=pedazos)
		for(ArchivoRAM archivo: pedazos) {
			if(archivo.getNumero().equals(i)) {
				return archivo;
			}
		}
		
		return null;
	}

	public void dividir(byte[] archivoTotal) {

		int divisor = 0, dividendo = 0, cociente = 0, residuo = 0;
		boolean cocienteNoCero = true;
		Random rand = new Random();
		
		while (cocienteNoCero) {
			divisor = rand.nextInt(20) + 1;//3;
			dividendo = archivoTotal.length;
			cociente = dividendo / divisor;
			residuo = dividendo % divisor;

			if (cociente > 0) {
				cocienteNoCero = false;
			}			
		}
		
		System.out.println(
				"El cociente de " + dividendo + " sobre " + divisor + " es: " + cociente + " el resto:" + residuo);

		pedazos = new ArrayList<ArchivoRAM>();
		//List<byte[]> archivos = new ArrayList<byte[]>(divisor);

		int from = 0, to = 0;

		for (int i = 0; i < divisor; i++) {

			if (i == 0) {
				to = to + cociente + residuo;
				byte[] archivo = Arrays.copyOfRange(archivoTotal, from, to);
				//archivos.add(archivo);
				ArchivoRAM archivoRam = new ArchivoRAM();
				archivoRam.setArchivo(archivo);
				archivoRam.setNombre(nombre);
				archivoRam.setNumero(i);
				pedazos.add(archivoRam);				
				from = from + cociente + residuo;
			} else {
				to = to + cociente;
				byte[] archivo = Arrays.copyOfRange(archivoTotal, from, to);
				//archivos.add(archivo);
				ArchivoRAM archivoRam = new ArchivoRAM();
				archivoRam.setArchivo(archivo);
				archivoRam.setNombre(nombre);
				archivoRam.setNumero(i);
				pedazos.add(archivoRam);
				from = from + cociente;
			}

		}

	}

	public void leerInputStream(Part filePart) throws Exception {
		this.nombre = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
		InputStream inputStream = filePart.getInputStream();
		pedazos = new ArrayList<ArchivoRAM>();

		OutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();

			int unmega = 0;
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);

				if (unmega++ == 1025)
					throw new Exception("Archivo sobrepasa un mega");
			}

			byte[] archivo = ((ByteArrayOutputStream) outputStream).toByteArray();
			dividir(archivo);

		} catch (Exception e) {
			// e.printStackTrace();
			throw e;

		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

//	public byte[] getArchivo() {
//		return archivo;
//	}

	public String getNombre() {
		return nombre;
	}

	public List<ArchivoRAM> getPedazos() {
		return pedazos;
	}

}
