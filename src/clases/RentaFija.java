package clases;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
        return true; 
    }

    @Override
    public boolean verificarMonto(double monto){
        return monto >= 1000; 
    }

	@Override
	protected double precancelar() {
		
		LocalDate hoy = Utilitarios.hoy();
		long dias = ChronoUnit.DAYS.between(fechaDeConstitucion, hoy);
		
		if (dias <= 0) {
			return 0;
		}
		
		if(dias >= plazo) {
			dias = plazo;
		}
		
		double ganancia = this.monto * 0.20 * (dias / 365.0);
		return this.monto+ganancia/2;
	}
	
    @Override
    public boolean corroborarCuenta (Cuenta cuenta) {
    	
    	return true;
    	
    }
	
    @Override
	public String consultarTipo() {
		
		return "Renta fija";
		
	}
    
}
