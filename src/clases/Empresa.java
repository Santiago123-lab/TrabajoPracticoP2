package clases;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
	
	private String cuit;
	private String nombre; 
	private String telefono; 
	private String email; 
	private String nombreContacto; 
	private List <String> personasAutorizadas; 
	
	public Empresa(String cuit, String nombreFantasia, String telefono, String email,String nombreContacto) {
		
		this.cuit = cuit;
		this.nombre = nombreFantasia;
		this.telefono = telefono;
		this.email = email;
		this.nombreContacto = nombreContacto;
		
		 this.personasAutorizadas = new ArrayList<>();
		
	}
	
	public String consultarCuit() {
		
		return this.cuit;
	}
	
	public void agregarAutorizado(String dniUsuario) {
		
		if(dniUsuario == null || dniUsuario.isBlank()) {
			
			throw new IllegalArgumentException ("Por favor, ingrese un DNI.");
		}
			
		
		if(this.personasAutorizadas.contains(dniUsuario)) {
			throw new IllegalArgumentException ("El DNI ingresado ya esta autorizado.");
		}
		
		this.personasAutorizadas.add(dniUsuario);
	}
	
	public boolean consultarAutorizado(String dniUsuario) {
		//verifica si el dni del usuario esta autorizado
		
		if(dniUsuario == null || dniUsuario.isBlank()) {
			
			throw new IllegalArgumentException ("Por favor, ingrese un DNI.");
		}
		
		if(this.personasAutorizadas.contains(dniUsuario)) {
			
			return true;
		}
		
		return false;
		
		
	}

}
