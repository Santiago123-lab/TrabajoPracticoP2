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
	
	public String toString() {
		
		StringBuilder st = new StringBuilder();
		
		//[Tipo]: [Alias] ([CVU])
		
		st.append(this.cvu);
		st.append("\n       Alias: ");
		st.append(this.alias);
		st.append("\n");
		st.append("       Tipo: Cuenta Premium\n");
		
//		st.append("[Premium:]: [\n");
//		st.append(this.alias);
//		st.append("] \n");
//		st.append("([\n");
//		st.append(this.cvu);
//		st.append("])\n");
		
		return st.toString();
}
}
