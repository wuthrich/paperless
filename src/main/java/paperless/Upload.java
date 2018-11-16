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

@WebServlet(asyncSupported = true, urlPatterns = { "/upload" })
@MultipartConfig
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	          
    public Upload() {
        super();        
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
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

}
