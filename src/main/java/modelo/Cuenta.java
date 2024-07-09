package modelo;

public class Cuenta {
	
	    private int id;
	    private int clienteId;
	    private double saldo;
	    
	    
	    
	    
		public Cuenta() {
			super();
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getClienteId() {
			return clienteId;
		}
		public void setClienteId(int clienteId) {
			this.clienteId = clienteId;
		}
		public double getSaldo() {
			return saldo;
		}
		public void setSaldo(double saldo) {
			this.saldo = saldo;
		}
	    
	    
}
