package controller;

/*
 * Alexis Santander
 */

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Cliente;
import modelo.ClienteDao;
import modelo.Cuenta;

/**
 * Servlet implementation class ClienteServlet
 */
@WebServlet("/cliente")
public class ClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteDao clienteDao;  
    /**
     * @see HttpServlet#HttpServlet()
     */
	public void init() {
        clienteDao = new ClienteDao();
    }
    public ClienteServlet() {
        super();
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String action = request.getParameter("action");
	        if (action == null) {
	            action = "login";
	        }
	        switch (action) {
			    case "register":
			        mostrarFormularioRegistro(request, response);
			        break;
			    case "login":
			    default:
			        mostrarFormularioLogin(request, response);
			        break;
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
        if (action == null) {
            action = "login";
        }
        try {
            switch (action) {
                case "register":
                    registrarCliente(request, response);
                    break;
                case "login":
                default:
                    autenticarCliente(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
		
	private void mostrarFormularioRegistro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("registro.jsp").forward(request, response);
    }

    private void mostrarFormularioLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    private void registrarCliente(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String password = request.getParameter("password");
        String RUT = request.getParameter("RUT");
        
        if (nombre == null || nombre.isEmpty() ||
                correo == null || correo.isEmpty() ||
                password == null || password.isEmpty() ||
                RUT == null || RUT.isEmpty()) {

                request.setAttribute("errorMessage", "Todos los campos son obligatorios.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("registro.jsp");
                dispatcher.forward(request, response);
                return;
            }

        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(nombre);
        nuevoCliente.setCorreo(correo);
        nuevoCliente.setPassword(password);
        nuevoCliente.setRUT(RUT);

        clienteDao.registrarCliente(nuevoCliente);

        Cuenta nuevaCuenta = new Cuenta();
        nuevaCuenta.setClienteId(nuevoCliente.getId());
        nuevaCuenta.setSaldo(0.0);
        clienteDao.crearCuenta(nuevaCuenta);

        response.sendRedirect("cliente?action=login");
    }

    private void autenticarCliente(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        Cliente cliente = clienteDao.obtenerClientePorCorreoYPassword(correo, password);
        if (cliente != null) {
            HttpSession session = request.getSession();
            session.setAttribute("cliente", cliente);
            response.sendRedirect("cuenta?action=view&clienteId=" + cliente.getId());
        } else {
            request.setAttribute("errorMessage", "Correo o contraseña incorrectos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        	}
    	}
	
}



