package clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import clases.Cuenta;
	
public abstract class Cuenta {
	
	
 	protected String cvu;
	protected String alias;
	protected double saldo;
	protected double saldoInvertido;
	protected List<Actividad> actividades;
	protected HashMap <Integer, Inversion> inversiones;
	  
	public Cuenta(String alias, double saldoInicial) {
		this.cvu = Utilitarios.generarSiguienteCvu();
	    this.alias = alias;
	    this.saldo = saldoInicial;
	    this.saldoInvertido = 0.0;
	    this.actividades = new ArrayList<>();
	    this.inversiones = new HashMap<>(); 
	    
	}
	
	public double consultarSaldo() {
		
		return saldo;
	
	}
	      
	public double consultarSaldoInvertido() {
	    
		return saldoInvertido;
	    
	}
	
	public String consultarCVU() {
		
		return this.cvu;
	
	}
	      
	public String consultarAlias() {
	
		return alias;
	
	}
	      
	public List<Actividad> consultarActividades() {
	
		return actividades;
	
	}
		
	public void debitar(double monto) {
	
		this.saldo -= monto;
	
	}
	      
	public void acreditar(double monto) {
	
		this.saldo += monto;
	 
	}
	      
	public void agregarSaldoInvertido(double monto) {
	
		this.saldoInvertido += monto;
	    
	}
	      
	public abstract boolean puedeDebitar(double monto);
	
	public abstract boolean puedeAcreditar(double monto);
}
	     
	      
//	public static int IdInversion = 1;
	
//	public Inversion crearInversion(double monto, int plazo) {
//	          
//	
//		if (!this.puedeDebitar(monto)) {
//	    
//			throw new RuntimeException("Saldo insuficiente");
//	       	
//		}
//	       	
//	    
//		this.debitar(monto);
//	       	
//	    
//		double interes = 0.10;
//	       	
//	    
//		RentaFija inversion = new RentaFija(IdInversion++, plazo, monto, interes);
//	       	
//	    
//		this.agregarSaldoInvertido(monto);
//	       	
//	    
//		inversiones.put(IdInversion, inversion);
//	       	
//	    
//		return inversion;
//	    
//	}
//	      
//	public Inversion crearInversionDivisa(double monto, int plazo, String divisa) {
//	
//		// para Divisa
//	          
//	    
//		if (!this.puedeDebitar(monto)) {
//	    
//			throw new RuntimeException("Saldo insuficiente");
//	       	
//		}
//	       	
//	    
//		this.debitar(monto);
//	       	
//	    
//		Divisa inversion = new Divisa(IdInversion++, plazo, monto, divisa);
//	       	
//	    
//		this.agregarSaldoInvertido(monto);
//	       	
//	    
//		inversiones.put(IdInversion, inversion);
//	       	
//	    
//		return inversion;
//	    
//	}
//	public Inversion crearInversionLiquidez(double monto, int plazoDias) {
//				
//	
//		if (!this.puedeDebitar(monto)) {
//	    
//			throw new RuntimeException("Saldo insuficiente");
//	       	
//		}
//				
//		
//		double interes = 0.15;
//				
//		
//		this.debitar(monto);
//				
//		
//		Liquidez inversion = new Liquidez(IdInversion++, plazoDias, interes);
//				
//		
//		inversiones.put(IdInversion, inversion);
//				
//		
//		return inversion;
//				
//		
//	}
//			
//	
//}



