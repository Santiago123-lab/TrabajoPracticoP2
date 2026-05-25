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
        this.fechaDeConstitucion = LocalDate.now(); 
    }


//    public LocalDate getFechaDeConstitucion(){
//        return fechaDeConstitucion; 
//    }
    
    public int getId() {
    	return id;
    }
    public abstract double calcularGanancia(); 

    public abstract boolean esPrecancelable(); 

    public abstract boolean verificarMonto(double monto); 

}
