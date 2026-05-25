package clases;

import java.time.LocalDate;

public abstract class Actividad {
	
	protected LocalDate fecha;  
	protected String dniOrigen;
	protected double monto;
	protected String estado;
	
	public Actividad(String dniOrigen, double monto, String estado) {
		
		this.fecha = Utilitarios.hoy();      
		this.dniOrigen=dniOrigen;
		this.monto=monto;
		this.estado=estado;
		
	}
	
	public abstract String toString();

}
