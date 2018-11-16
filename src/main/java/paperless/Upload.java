package paperless;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class Upload
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/upload" })
@MultipartConfig
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private String nombre=null;
	//private String nombreAbsoluto=null;
          
    public Upload() {
        super();        
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*
		JsonObject json = new JsonObject();		
		json.addProperty("nombre", nombre);
		json.addProperty("nombreAbsoluto", nombreAbsoluto);
		response.getWriter().append(json.toString());
		*/
		
		JsonObject json = new JsonObject();		
		json.addProperty("nombre", ArchivoAppEngine.instancia.getNombre());
		json.addProperty("nombreAbsoluto", "Se guarda como byte array");
		
		JsonArray archivos = new JsonArray();
		for(ArchivoRAM archivo: ArchivoAppEngine.instancia.getPedazos()) {
			JsonObject jsonArchivo = new JsonObject();
			jsonArchivo.addProperty("nombrePedazo", archivo.nombrePedazo());
			jsonArchivo.addProperty("numero", archivo.getNumero());
			jsonArchivo.addProperty("nombre", archivo.getNombre());
			archivos.add(jsonArchivo);
		}
		json.add("archivos", archivos);
		
//		JsonArray array = new JsonArray();
//		json.add("array", array);
		response.getWriter().append(json.toString());
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
	    
		try {
			ArchivoAppEngine.instancia.leerInputStream(filePart);
			
			String nextJSP = "/downloadAppEngine.html";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request,response);
			
		} catch (Exception e) {			
			e.printStackTrace();
			response.getWriter().print("Error: "+e.getMessage());
		}
	
	    
	}
	
	/*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    InputStream inputStream = filePart.getInputStream();
	    // ... (do your job here)
	    OutputStream  outputStream = null;
	    try {
			File archivo = new File(fileName);
			// write the inputStream to a FileOutputStream
	    	  outputStream = //new ByteArrayOutputStream();
	                    new FileOutputStream(archivo);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

			//((ByteArrayOutputStream) outputStream).toByteArray();
			System.out.println("Done! "+fileName+" "+ archivo.getAbsolutePath());
			
			//request.setAttribute("name", fileName);
			this.nombre = fileName;
			this.nombreAbsoluto = archivo.getAbsolutePath();
			
			String nextJSP = "/download.html";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request,response);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("Error: "+e.getMessage());
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
	
	*/

}
