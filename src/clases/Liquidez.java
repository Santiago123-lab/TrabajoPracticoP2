package clases;

public class Liquidez extends Inversion {

    public Liquidez(int id, int plazo, double monto){
        super(id, plazo, monto); 
    }

    @Override
    public double calcularGanancia(){
        return monto * 0.08 * (plazo/365); 

    }

    @Override
    public boolean esPrecancelable(){
        return true; 
    }

    @Override
    public boolean verificarMonto(double monto){
        return monto > 0; 
    }
    
}
