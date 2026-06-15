package clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import clases.Cuenta;
	
public abstract class Cuenta {
	protected String cvu;
	protected String alias;
	protected double saldo;
	protected double saldoInvertido;
	protected List<Actividad> actividades;
	protected HashMap <Integer, Inversion> inversiones;
  
   
	public Cuenta(String alias) {
		
		if(alias==null || alias.isBlank()) {
			
			throw new IllegalArgumentException ("Por favor, ingrese otro alias.");
		}
		
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
    
    public abstract String toString();	     
	      
	public static int IdInversion = 1;

	public Inversion obtenerInversion(int id) {
		return inversiones.get(id); 
	}
	
	public void eliminarInversiones(int id) {
		inversiones.remove(id); 
	}
	
	public void descontarSaldoInvertido(double monto) {
		saldoInvertido -= monto; 
	}
	
	public boolean puedeInvertir(double monto) {
		return saldo >= monto;
	}
	 
	public void agregarInversion(Inversion i) {
		
		inversiones.put(i.consultarId(), i);
		this.saldoInvertido += i.consultarMonto();
	}
	
	
	
	
	
	
	
	
	
}