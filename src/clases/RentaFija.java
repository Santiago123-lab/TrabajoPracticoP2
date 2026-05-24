package clases;

public class RentaFija extends Inversion{ 
    private double interes; 

    public RentaFija(int id, int plazo, double monto, double interes){
        super(id, plazo, monto); 
        this.interes = interes; 
    }

    @Override
    public double calcularGanancia(){
        return monto * interes; 
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
