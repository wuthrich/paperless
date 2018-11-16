package paperless;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadAppEngine
 */
@WebServlet(urlPatterns = { "/download/*" })
public class DownloadAppEngine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadAppEngine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
		//response.setContentLength(ArchivoAppEngine.instancia.getArchivo().length);
		OutputStream os = response.getOutputStream();

		try {
		   os.write(archivo.getArchivo() , 0, archivo.getArchivo().length);
		} catch (Exception e) {
		   e.printStackTrace();
		} finally {
		    os.close();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
