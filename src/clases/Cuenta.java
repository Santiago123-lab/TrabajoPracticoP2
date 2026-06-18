package clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

	
public abstract class Cuenta {
	protected String cvu;
	protected String alias;
	protected double saldo;
	protected double saldoInvertido;
	protected List<Actividad> actividades;
	protected HashMap <Integer, Inversion> inversiones;
  
   
	public Cuenta(String alias) {
		
		if(alias==null || alias.isBlank()) {
			
			throw new IllegalArgumentException ("Por favor, ingrese otro alias.");
		}
		
		this.cvu = Utilitarios.generarSiguienteCvu();
	    this.alias = alias;
	    this.saldo = 0.0;
	    this.saldoInvertido = 0.0;
	    this.actividades = new ArrayList<>();
	    this.inversiones = new HashMap<>(); 	}
	
    public double consultarSaldo() {
           return saldo;
       }
     
    public double consultarSaldoInvertido() {
        return saldoInvertido;
    }
    
    public String consultarCVU() {
    	   return this.cvu;
    	   
       }
      
    public String consultarAlias() {
           return alias;
       }
    
    public int consultarVolumen() {

        return actividades.size();

    }
    
    public void agregarActividad (Actividad act) {
    	   
    	   this.actividades.add(act);
       }
    
    public void debitar(double monto) {
        this.saldo -= monto;
    }
      
    public void acreditar(double monto) {
        this.saldo += monto;
    }
    
    public void agregarSaldoInvertido(double monto) {
           this.saldoInvertido += monto;
       }
 	      
	public void descontarSaldoInvertido(double monto) {
		saldoInvertido -= monto; 
	}
	 
	public void agregarInversion(Inversion i, String dni) {
		
		if(!i.corroborarCuenta(this)) {
			
			Actividad act = new Act_Inversion(dni, cvu, i.consultarTipo(), i.consultarPlazo(), i.consultarMonto(), "Rechazado");
			this.actividades.add(act);
			
			throw new IllegalArgumentException ("No se puede realizar este tipo de inversion desde una cuenta NO corporativa");
					
		}
		
		if(!puedeInvertir(i.consultarMonto())) {
			
			Actividad act = new Act_Inversion(dni, cvu, i.consultarTipo(), i.consultarPlazo(), i.consultarMonto(), "Rechazado");
			this.actividades.add(act);
			
			throw new IllegalArgumentException ("La cuenta no posee saldo suficiente para invertir");
					
		}
		
		Actividad act = new Act_Inversion(dni, cvu, i.consultarTipo(), i.consultarPlazo(), i.consultarMonto(), "Aprobado");
		this.actividades.add(act);
		
		inversiones.put(i.consultarId(), i);
		this.saldoInvertido += i.consultarMonto();
		
		}
	
	public void precancelarInversion(int idInversion) {
		
		if(!this.inversiones.containsKey(idInversion)) {
			
			throw new IllegalArgumentException ("La cuenta no posee una inversion bajo el ID ingresado.");
			
		}
		
		Inversion inv = this.inversiones.get(idInversion);
		
		if(!inv.esPrecancelable()) {
			
			throw new IllegalArgumentException ("La inversion ingresada no se puede precancelar");
		}
		
		double monto = inv.consultarMonto(); 
		 
		double gananciaFinal = inv.precancelar();
		
		acreditar(gananciaFinal); 
		
		descontarSaldoInvertido(monto); 
		
		this.inversiones.remove(idInversion); 
		
		
	}
	
	public boolean puedeInvertir(double monto) {
		return saldo >= monto;
	}
	
    public List<String> obtenerHistorial() {
    	
		List <String> lista = new ArrayList<>();
		
		for(Actividad act : this.actividades) {
			
			lista.add(act.toString());
			
		}
	
       return lista;
   }
	
    public abstract boolean puedeDebitar(double monto);
    
    public abstract boolean puedeAcreditar(double monto);
    
    public abstract String toString();		
		

	}
	
	
	
	
	
	
	
	
	
