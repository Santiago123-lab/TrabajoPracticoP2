package clases;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Divisa extends Inversion { 
    private double interes; 
    private String divisa; 
    private double cantidadDivisa;

    public Divisa(int id, int plazo, double monto, double interes, String divisa){
        super(id, plazo, monto); 
        this.interes = interes; 
        this.divisa = divisa; 
        this.cantidadDivisa= monto / Utilitarios.consultarCotizacion(divisa);
    }

    
    @Override
    public double calcularGanancia(){
       
    	double cotizacion = Utilitarios.consultarCotizacion(divisa);
    	
    	double montoEnDivisa = this.monto / cotizacion;
    	
  	    double gananciaDivisa = montoEnDivisa * interes * (this.plazo / 365);
    	
    	double gananciaPesos = gananciaDivisa * cotizacion;
    	
    	return gananciaPesos;
    	
    }

    @Override
    public boolean esPrecancelable(){
        return true; 
    }

    @Override
    public boolean verificarMonto(double monto){
        return monto > 5000; 
    }

	@Override
	protected double precancelar() {
		
		LocalDate hoy = Utilitarios.hoy();
		long dias = ChronoUnit.DAYS.between(fechaDeConstitucion, hoy);
		
		if (dias <= 0) {
			return 0;
		}
		
		if (dias >= plazo) {
			dias = plazo;
		}
		
		double cotizacion = Utilitarios.consultarCotizacion(divisa);
		
		double interesesDivisa =cantidadDivisa * interes * (dias / 365.0);
		
		interesesDivisa /= 2;
		
		return (cantidadDivisa + interesesDivisa) * cotizacion;
	}
	
    @Override
    public boolean corroborarCuenta (Cuenta cuenta) {
    	
    	return true;
    	
    }
	
    @Override
	public String consultarTipo() {
		
		return "Divisa";
		
	}

    
}
