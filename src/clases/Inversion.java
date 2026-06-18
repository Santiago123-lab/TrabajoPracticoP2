package clases;

import java.time.LocalDate; 

public abstract class Inversion {
    protected int id; 
    protected int plazo; 
    protected double monto; 
    protected LocalDate fechaDeConstitucion; 

    public Inversion(int id, int plazo, double monto){
        this.id = id; 
        this.plazo = plazo; 
        this.monto = monto; 
        this.fechaDeConstitucion = Utilitarios.hoy(); 
    
    }
  
    public int consultarId() {
    	return this.id;
    }
    
    public double consultarMonto() {
    	return this.monto; 
    }
    
    public int consultarPlazo() {
    	return this.plazo; 
    }
    
    public abstract double calcularGanancia(); 

    public abstract boolean esPrecancelable(); 

    public abstract boolean verificarMonto(double monto);
    
    public abstract boolean corroborarCuenta (Cuenta cuenta);
    
    public abstract String consultarTipo();
            
    protected abstract double precancelar(); 
	
    
    

}
