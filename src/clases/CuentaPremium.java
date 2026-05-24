package clases;

public class CuentaPremium extends Cuenta {
	private double depositoInicial;
	
	public CuentaPremium(String alias, double depositoInicial) {
		super(alias, depositoInicial);
		this.depositoInicial = depositoInicial;
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
