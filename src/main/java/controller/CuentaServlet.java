package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.ClienteDao;
import modelo.Cuenta;

/**
 * Servlet implementation class CuentaServlet
 */
@WebServlet("/cuenta")
public class CuentaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteDao clienteDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public CuentaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() {
		clienteDao = new ClienteDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if (action == null) {
			action = "view";
		}
		try {
			switch (action) {
			case "deposit":
				mostrarFormularioDeposito(request, response);
				break;
			case "withdraw":
				mostrarFormularioRetiro(request, response);
				break;
			case "view":
			default:
				verSaldo(request, response);
				break;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if (action == null) {
			action = "view";
		}
		try {
			switch (action) {
			case "deposit":
				depositarFondos(request, response);
				break;
			case "withdraw":
				retirarFondos(request, response);
				break;
			case "view":
			default:
				verSaldo(request, response);
				break;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}

	}

	private void verSaldo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int clienteId = Integer.parseInt(request.getParameter("clienteId"));
		Cuenta cuenta = clienteDao.obtenerCuentaPorClienteId(clienteId);
		request.setAttribute("cuenta", cuenta);
		request.getRequestDispatcher("saldo.jsp").forward(request, response);
	}
	
	private void mostrarFormularioDeposito(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("deposito.jsp").forward(request, response);
	}

	private void mostrarFormularioRetiro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("retiro.jsp").forward(request, response);
	}

	private void depositarFondos(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int clienteId = Integer.parseInt(request.getParameter("clienteId"));
		double monto = Double.parseDouble(request.getParameter("monto"));
		Cuenta cuenta = clienteDao.obtenerCuentaPorClienteId(clienteId);
		cuenta.setSaldo(cuenta.getSaldo() + monto);
		clienteDao.actualizarSaldo(cuenta);
		response.sendRedirect("cuenta?action=view&clienteId=" + clienteId);
	}

	private void retirarFondos(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int clienteId = Integer.parseInt(request.getParameter("clienteId"));
		double monto = Double.parseDouble(request.getParameter("monto"));
		Cuenta cuenta = clienteDao.obtenerCuentaPorClienteId(clienteId);
		cuenta.setSaldo(cuenta.getSaldo() - monto);
		clienteDao.actualizarSaldo(cuenta);
		response.sendRedirect("cuenta?action=view&clienteId=" + clienteId);
	}
}
