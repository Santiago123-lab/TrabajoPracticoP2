package clases;

public class CuentaPremium extends Cuenta {
	

	
	public CuentaPremium(String alias, double depositoInicial) {
		super(alias);
		

		this.saldo=depositoInicial;
		
	}
	
	public boolean puedeDebitar(double monto) {
		
		return (saldo - monto) >= 500000;
	}
	
	
	public boolean puedeAcreditar(double monto) {
		
		return true;
	}
	
	
	public String consultarTipoCuenta() {
		
		return "Premium";
	}
}
