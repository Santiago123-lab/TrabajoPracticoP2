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
  
   
	public Cuenta(String alias) {
		this.cvu = Utilitarios.generarSiguienteCvu();
	    this.alias = alias;
	    this.saldo = 0.0;
	    this.saldoInvertido = 0.0;
	    this.actividades = new ArrayList<>();
	    this.inversiones = new HashMap<>(); 	}
	
       public double consultarSaldo() {
           return saldo;
       }
       
       public String consultarCVU() {
    	   return this.cvu;
    	   
       }
      
       public double consultarSaldoInvertido() {
           return saldoInvertido;
       }
      
       public String consultarAlias() {
           return alias;
       }
       
       public void agregarActividad (Actividad act) {
    	   
    	   this.actividades.add(act);
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
     
	     
	      
	public static int IdInversion = 1;

	public Inversion crearInversionRentaFija(double monto, int plazo) {
          
//		if (!this.puedeDebitar(monto)) {
//			throw new RuntimeException("Saldo insuficiente");
//		} 
		
		if (!this.puedeDebitar(monto)) {
			throw new IllegalArgumentException("Saldo insuficiente");
    } 
		
        
//    this.debitar(monto);
//        
//    double interes = 0.10;
//        
//    Inversion inversion = new RentaFija(IdInversion++, plazo, monto);
//        
//    this.agregarSaldoInvertido(monto);
//        
//    inversiones.put(IdInversion, inversion);
//        
//    return inversion;
		debitar(monto); 
		
		int id = IdInversion++; 
		
		Inversion inversion = new RentaFija(id, plazo, monto); 
		
		inversiones.put(id, inversion); 
		
		agregarSaldoInvertido(monto); 
		
		return inversion; 
}
      
public Inversion crearInversionDivisa(double monto, int plazo, double interes, String divisa) {
          
//    if (!this.puedeDebitar(monto)) {
//        throw new RuntimeException("Saldo insuficiente");
//    }
    
    if (!this.puedeDebitar(monto)) {
        throw new IllegalArgumentException("Saldo insuficiente"); 
    }
        
//    this.debitar(monto);
//        
//    Inversion inversion = new Divisa(IdInversion++, plazo, monto, interes, divisa);
//        
//    this.agregarSaldoInvertido(monto);
//        
//    inversiones.put(IdInversion, inversion);
//        
//    return inversion;
    
    debitar(monto); 
    
    int id = IdInversion++; 
    
    Inversion inversion = new Divisa(id, plazo, monto, interes, divisa); 
    
    inversiones.put(id, inversion); 
    
    agregarSaldoInvertido(monto); 
    
    return inversion; 
}

public Inversion crearInversionLiquidez(double monto, int plazoDias) {
            
//    if (!this.puedeDebitar(monto)) {
//        throw new RuntimeException("Saldo insuficiente");
//    }
    if(monto < 20000000) {
    	throw new IllegalArgumentException("El monto minimo para el fondo de liquidez es 20 millones"); 
    }
    
    if (!this.puedeDebitar(monto)) {
        throw new IllegalArgumentException("Saldo insuficiente"); 
    }
        
//    double interes = 0.15;
//        
//    this.debitar(monto);
//        
//    Inversion inversion = new Liquidez(IdInversion++, plazoDias, monto);
//        
//    inversiones.put(IdInversion, inversion);
//        
//    return inversion;
    debitar(monto); 
    
    int id = IdInversion++; 
    
    Inversion inversion = new Liquidez(id, plazoDias, monto); 
    
    inversiones.put(id, inversion); 
    
    agregarSaldoInvertido(monto); 
    
    return inversion; 
}}