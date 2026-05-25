package clases;

public class CuentaCorporativa extends Cuenta{
	
	private String cuitEmpresa;
	
	public CuentaCorporativa(String alias,String cuitEmpresa) {
		super(alias);
		
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
}
