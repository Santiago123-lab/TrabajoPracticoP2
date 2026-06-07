package clases;

public class CuentaRegular extends Cuenta {
	
	public CuentaRegular(String alias) {
		super(alias);
		
		
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
	
	public String toString() {
		
		StringBuilder st = new StringBuilder();
		
		//[Tipo]: [Alias] ([CVU])
		
		st.append(this.cvu);
		st.append("\n       Alias: ");
		st.append(this.alias);
		st.append("\n");
		st.append("       Tipo: Cuenta Regular\n");
		
		return st.toString();

		
		
	}
	
}

