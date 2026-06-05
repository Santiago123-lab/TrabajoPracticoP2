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
	
	public List <Cuenta> consultarCuentas(){
		
		List <Cuenta> lista = new ArrayList<>();
		
		for(Cuenta c: this.cuentas.values()) {
			
			lista.add(c);
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
	
	public List <Actividad> consultarActividades() {
		
		
		List <Actividad>lista = new ArrayList<>();
		
		for(Cuenta c: this.cuentas.values()) {
			
			lista.addAll(c.consultarActividades());
			
		}
		
		return lista;
		
		
	}
	
	public void recibirPermisoEmpresarial() {
		
		this.permisoEmpresarial=true;
	}
	
	public Cuenta buscarCuenta(String cvu) {
		
		return cuentas.get(cvu);
	}
	

}
