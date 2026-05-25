package clases;

<<<<<<< HEAD
import java.time.LocalDate;

public abstract class Actividad {
	
	protected LocalDate fecha;  
=======
public abstract class Actividad {
	
>>>>>>> 652244d6399da5c92ec8def31cb63c0b12a5a5ca
	protected String dniOrigen;
	protected double monto;
	protected String estado;
	
	public Actividad(String dniOrigen, double monto, String estado) {
		
<<<<<<< HEAD
		this.fecha = Utilitarios.hoy();      
=======
>>>>>>> 652244d6399da5c92ec8def31cb63c0b12a5a5ca
		this.dniOrigen=dniOrigen;
		this.monto=monto;
		this.estado=estado;
		
	}
	
	public abstract String toString();

}
