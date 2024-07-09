package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteDao {

	private String DRIVER = "jdbc:mysql://localhost:3306/bdjspproyect";
	private String USER = "root";
	private String PASS = "123456";

	private static final String INSERT_CLIENTE_SQL = "INSERT INTO Cliente (nombre, correo, password, RUT) VALUES (?, ?, ?, ?)";
    private static final String SELECT_CUENTA_BY_CLIENTE_ID = "SELECT * FROM Cuenta WHERE cliente_id = ?";
    private static final String SELECT_CLIENTE_BY_RUT = "SELECT id, nombre, correo FROM clientes WHERE RUT = ?";
    private static final String SELECT_CLIENTE_BY_CORREO_Y_PASSWORD = "SELECT * FROM Cliente WHERE correo = ? AND password = ?";
    private static final String INSERT_CUENTA_SQL = "INSERT INTO Cuenta (cliente_id, saldo) VALUES (?, ?)";
    private static final String UPDATE_CUENTA_SALDO = "UPDATE Cuenta SET saldo = ? WHERE id = ?";

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(DRIVER, USER, PASS);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void registrarCliente(Cliente cliente) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENTE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getCorreo());
            preparedStatement.setString(3, cliente.getPassword());
            preparedStatement.setString(4, cliente.getRUT());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                cliente.setId(rs.getInt(1));
            }
        }
    }

    public void crearCuenta(Cuenta cuenta) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUENTA_SQL)) {
            preparedStatement.setInt(1, cuenta.getClienteId());
            preparedStatement.setDouble(2, cuenta.getSaldo());
            preparedStatement.executeUpdate();
        }
    }

    public Cuenta obtenerCuentaPorClienteId(int clienteId) throws SQLException {
        Cuenta cuenta = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUENTA_BY_CLIENTE_ID)) {
            preparedStatement.setInt(1, clienteId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                cuenta = new Cuenta();
                cuenta.setId(rs.getInt("id"));
                cuenta.setClienteId(rs.getInt("cliente_id"));
                cuenta.setSaldo(rs.getDouble("saldo"));
            }
        }
        return cuenta;
    }
    
    public Cliente obtenerClientePorRut(String RUT) throws SQLException {
        Cliente cliente = null;
        
        try (Connection connection = getConnection();
        	PreparedStatement stmt = connection.prepareStatement(SELECT_CLIENTE_BY_RUT)) {
            stmt.setString(1, RUT);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setRUT(rs.getString("rut"));
            }
        }

        return cliente;
    }

    public Cliente obtenerClientePorCorreoYPassword(String correo, String password) throws SQLException {
        Cliente cliente = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENTE_BY_CORREO_Y_PASSWORD)) {
            preparedStatement.setString(1, correo);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setPassword(rs.getString("password"));
                cliente.setRUT(rs.getString("RUT"));
            }
        }
        return cliente;
    }

    public void actualizarSaldo(Cuenta cuenta) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CUENTA_SALDO)) {
            preparedStatement.setDouble(1, cuenta.getSaldo());
            preparedStatement.setInt(2, cuenta.getId());
            preparedStatement.executeUpdate();
        }
              
    }
}
