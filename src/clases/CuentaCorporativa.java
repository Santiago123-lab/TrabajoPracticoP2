package clases;

public class CuentaCorporativa extends Cuenta{
	
	private String cuitEmpresa;
	
	public CuentaCorporativa(String alias,String cuitEmpresa) {
		super(alias);
		
		if(cuitEmpresa==null || cuitEmpresa.isBlank()) {
			
			throw new IllegalArgumentException ("Por favor, ingrese un CUIT correcto.");
		}
		
		this.cuitEmpresa = cuitEmpresa;
		
	}
	
	public boolean puedeDebitar (double monto) {
		
		return (saldo - monto) >= 0;
	}
	
	public boolean puedeAcreditar (double monto) {
		
		return true;
	}
	
	public String consultarTipoCuenta() {
		
		return "Corporativa";
	}
	
	public String toString() {
		
		StringBuilder st = new StringBuilder();
	
		st.append(this.cvu);
		st.append("\n       Alias: ");
		st.append(this.alias);
		st.append("\n");
		st.append("       Tipo: Cuenta corporativa\n");
		
		return st.toString();
}
}
