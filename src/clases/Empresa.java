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
		
		if(cuit==null || cuit.isBlank()) {
			throw new IllegalArgumentException ("Ingrese un CUIT correcto.");
		}
		
		if(nombreFantasia==null || nombreFantasia.isBlank()) {
			throw new IllegalArgumentException ("Por favor, ingrese el nombre de la empresa.");
		}
		
		if(telefono==null || telefono.isBlank()) {
			throw new IllegalArgumentException ("Por favor, ingrese un telefono de contacto.");
		}
		
		if(email==null || email.isBlank()) {
			throw new IllegalArgumentException ("Por favor, ingrese un email de contacto.");
		}
		
		if(nombreContacto==null || nombreContacto.isBlank()) {
			throw new IllegalArgumentException ("Por favor, ingrese un nombre de contacto correcto.");
		}
		
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
	
	public boolean estaAutorizado(String dniUsuario) {
		
		if(dniUsuario == null || dniUsuario.isBlank()) {
			
			throw new IllegalArgumentException ("Por favor, ingrese un DNI.");
		}
		
		if(this.personasAutorizadas.contains(dniUsuario)) {
			
			return true;
		}
		
		return false;
		
		
	}

}
