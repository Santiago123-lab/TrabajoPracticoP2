package clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Usuario {
	

	private String dni;
	private String nombre;
	private String telefono;
	private String email;
	private HashMap<String, Cuenta> cuentas;
	private boolean permisoEmpresarial;
	
	
	public Usuario(String dni, String nombre, String telefono, String email) {
		
		if(dni==null || dni.isBlank()) {
			throw new IllegalArgumentException ("El DNI ingresado no es valido");

		}
		if(nombre==null || nombre.isBlank()) {
			throw new IllegalArgumentException ("Por favor, ingrese un nombre valido");

		}
		if(telefono==null || telefono.isBlank()) {
			throw new IllegalArgumentException ("El telefono ingresado no es valido");

		}
		if(email==null || email.isBlank()) {
			throw new IllegalArgumentException ("El email ingresado no es valido");

		}
		
		this.dni=dni;
		this.nombre=nombre;
		this.telefono=telefono;
		this.email=email;
		this.cuentas= new HashMap<>();
		this.permisoEmpresarial=false;
	}
	
	public String consultarDni() {
		
		return this.dni;
		
	}
	
	public int cantidadCuentas() {

	    return cuentas.size();

	}
	
	public List <Cuenta> consultarCuentas(){
		
		ArrayList <Cuenta> cuentas = new ArrayList<>();

		for(Cuenta c : this.cuentas.values()) {
			
			cuentas.add(c);
			
		}
		
		return cuentas;
	}
	
	public List <String> obtenerCuentas(){
		
		List <String> lista = new ArrayList<>();
		
		for(Cuenta c: this.cuentas.values()) {
			
			lista.add(c.toString());
		}
		
		return lista;
	}
	
	public void agregarCuenta(String cvu, Cuenta c) {
		
		if(cvu==null||cvu.isBlank()) {
			throw new IllegalArgumentException ("Por favor, ingrese un CVU valido.");
			
		}
		
		if(c==null) {
			throw new IllegalArgumentException ("Por favor, ingrese una cuenta valida.");
			
		}
		
		if(this.cuentas.containsKey(cvu)) {
			throw new RuntimeException ("La cuenta ingresada ya se encuentra registrada.");
		}
		
		this.cuentas.put(cvu, c);
		
	}
	
	public double obtenerSaldoCuenta(String cvu) {
		
		Cuenta c= this.cuentas.get(cvu);
		
		return c.consultarSaldo();
		
		
	}
	
	public Cuenta obtenerCuenta(String cvu) {
		
		return this.cuentas.get(cvu);
		
	}
		
	public boolean existeCuenta (String cvu) {
		
		return this.cuentas.containsKey(cvu);
		
		
	}
		
	public double consultarSaldoInvertido() {
		
		double total=0;
		
		for(Cuenta c : this.cuentas.values()) {
			
			total+= c.consultarSaldoInvertido();
			
		}
		
		return total;
	}
	
	public boolean consultarPermisoEmpresarial() {
		
		return this.permisoEmpresarial;
	}
	
	public boolean puedoDebitar(String cvu, double monto) {
		
		Cuenta c= this.cuentas.get(cvu);
		
		return c.puedeDebitar(monto);
		
		
	}
	
	public void agrearActDeCuenta(String cvu, Actividad act) {
		
		Cuenta c = this.cuentas.get(cvu);
		
		c.agregarActividad(act);
		
	}
	
	public boolean puedoAcreditar(String cvu, double monto) {
		
		Cuenta c= this.cuentas.get(cvu);
		
		return c.puedeAcreditar(monto);
		
		
	}
	
	public void acreditarMonto(String cvu, double monto) {
		
		Cuenta c = this.cuentas.get(cvu);
		
		c.acreditar(monto);
		
	}
	
	public void hacerTransferencia(String cvuOrigen, String cvuDestino, Usuario usuarioDestino, double monto) {
		
		Cuenta origen = this.cuentas.get(cvuOrigen);
		
		if(!puedoDebitar(cvuOrigen,monto)) {
			
			Actividad act= new Act_Transferencia(this.dni, usuarioDestino.consultarDni(), cvuOrigen, cvuDestino,monto,"Rechazado");
			origen.agregarActividad(act);
			usuarioDestino.agrearActDeCuenta(cvuDestino, act);

			throw new IllegalArgumentException ("La cuenta de origen no posee saldo suficiente para debitar");
			
			
		}
		
		if(!usuarioDestino.puedoAcreditar(cvuDestino, monto)) {
			
			Actividad act= new Act_Transferencia(this.dni, usuarioDestino.consultarDni(), cvuOrigen, cvuDestino,monto,"Rechazado");
			origen.agregarActividad(act);
			usuarioDestino.agrearActDeCuenta(cvuDestino, act);

			throw new IllegalStateException("La cuenta no puede acreditar el monto ingresado");
			
		}
		

		
		origen.debitar(monto);
		usuarioDestino.acreditarMonto(cvuDestino, monto);
		
		Actividad act= new Act_Transferencia(this.dni, usuarioDestino.consultarDni(), cvuOrigen, cvuDestino,monto,"Aprobado");
		origen.agregarActividad(act);
		usuarioDestino.agrearActDeCuenta(cvuDestino, act);
		
	}
	
	public List <String> obtenerHistorialTotal() {
		
		
		List <String> lista = new ArrayList<>();
		
		for(Cuenta c: this.cuentas.values()) {
			
			lista.addAll(c.obtenerHistorial());
			
		}
		
		return lista;
		
		
	}
	
	public List <String> obtenerHistorial(String cvu){
		
		Cuenta c = this.cuentas.get(cvu);
		
		return c.obtenerHistorial();
		
		
	}
	
	public void recibirPermisoEmpresarial() {
		
		this.permisoEmpresarial=true;
	}

	public void agregarInversion(String cvu, Inversion i) {
		
		if(cvu==null||cvu.isBlank()) {
			
			throw new IllegalArgumentException("Por favor, ingrese un CVU valido");
		}
		
		if(!this.cuentas.containsKey(cvu)){
			
			throw new IllegalArgumentException ("Para invertir, ingrese un CVU asociado a un usuario");
		}
		
		Cuenta c = cuentas.get(cvu);
		
		c.agregarInversion(i, this.dni);
		
		
	}
	
	public void precancelarInversion(String cvu, int idInversion) {
		
		if(idInversion < 0) {
			
			throw new IllegalArgumentException("Por favor, ingrese correctamente el ID de la inversion."); 
		}
		
		Cuenta c = this.cuentas.get(cvu);
		
		c.precancelarInversion(idInversion);
		
	}
	
}
