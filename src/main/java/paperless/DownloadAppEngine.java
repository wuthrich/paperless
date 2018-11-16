package paperless;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = { "/download/*" })
public class DownloadAppEngine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public DownloadAppEngine() {
        super();        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getPathInfo();		
		if(null!=id && id.length()>1) {
			id= id.substring(1);
		}else {
			id=null;
		}
		
		System.out.println("Archivo: "+id);
		
		ArchivoRAM archivo = ArchivoAppEngine.instancia.traerArchivoRAM(Integer.parseInt(id));
		
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\""+archivo.nombrePedazo()+"\"");		
		OutputStream os = response.getOutputStream();

		try {
		   os.write(archivo.getArchivo() , 0, archivo.getArchivo().length);
		} catch (Exception e) {
		   e.printStackTrace();
		} finally {
		    os.close();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doGet(request, response);
	}

}
