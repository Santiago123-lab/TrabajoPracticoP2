package clases;

public class Divisa extends Inversion { 
    private double interes; 
    private String divisa; 

    public Divisa(int id, int plazo, double monto, double interes, String divisa){
        super(id, plazo, monto); 
        this.interes = interes; 
        this.divisa = divisa; 
    }

    @Override
    public double calcularGanancia(){
        return monto * interes; 
    }

    @Override
    public boolean esPrecancelable(){
        return true; 
    }

    @Override
    public boolean verificarMonto(double monto){
        return monto > 5000; 
    }

    public void precancelar(){
        System.out.println("Inversion precancelada"); 
    }
    
}
