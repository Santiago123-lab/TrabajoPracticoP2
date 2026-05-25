package clases;

public class RentaFija extends Inversion{ 
    

    public RentaFija(int id, int plazo, double monto){
        super(id, plazo, monto); 
        
    }

    @Override
    public double calcularGanancia(){
        return monto * 0.06 * (plazo/365); 
    }

    @Override 
    public boolean esPrecancelable(){
        return false; 
    }

    @Override
    public boolean verificarMonto(double monto){
        return monto >= 1000; 
    }
    
}
