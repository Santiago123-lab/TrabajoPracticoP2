package clases;

public class CuentaRegular extends Cuenta {
	
	public CuentaRegular(String alias, double saldoInicial) {
		super(alias, saldoInicial);
		
	}
	
	
	public boolean puedeDebitar(double monto) {
		return (saldo - monto) >= 0;
	}
	
	
	public boolean puedeAcreditar(double monto) {
		return (saldo + monto) <= 5000000;
	}
	public String consultarTipoCuenta() {
		return "Regular";
	}
	
}

