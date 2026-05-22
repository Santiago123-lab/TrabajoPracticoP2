package clases;

import java.util.HashMap;
import java.util.List;

public class Billetera implements IBilletera {
	
	private HashMap<String, Usuario> usuarios;
	private HashMap<String, Empresa> empresas;
	
	public Billetera() {
		
        this.usuarios = new HashMap<>();
        this.empresas = new HashMap<>();
		
	}
	

	@Override
	public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email,
			String nombreContacto) {
		
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
		
		if(this.empresas.containsKey(cuit)) {
			
			throw new IllegalArgumentException("El CUIT: "+cuit+", ya esta registrado en el sistema");
		}
		
		Empresa nuevaEmpresa = new Empresa(cuit,nombreFantasia,telefono,email,nombreContacto);
		this.empresas.put(cuit, nuevaEmpresa);
		
		
	}

	@Override
	public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) {
		
		if(cuitEmpresa== null || cuitEmpresa.isBlank()) {
			throw new IllegalArgumentException("Por favor, ingrese un CUIT correcto.");
			
		}
		
		if(dniAutorizado == null || dniAutorizado.isBlank()) {
			
			throw new IllegalArgumentException("Por favor, ingrese un DNI correcto.");
			
		}
		
		if(!(this.empresas.containsKey(cuitEmpresa))) {
			
			throw new IllegalArgumentException("El CUIT de la empresa no esta registrado.");
		}
		
		Empresa e = this.empresas.get(cuitEmpresa);
		
		e.agregarAutorizado(dniAutorizado);
		
	}

	@Override
	public void registrarUsuario(String dni, String nombre, String telefono, String email) {
		
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
		if(usuarios.containsKey(dni)) {
			throw new IllegalArgumentException ("El usuario con el DNI:"+dni+", ya esta ingresado en el sistema");

		}
		
		Usuario nuevoUsuario = new Usuario(dni, nombre, telefono, email);
		usuarios.put(dni, nuevoUsuario);

	}

	@Override
	public String crearCuentaRegular(String dniUsuario, String alias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> obtenerCuentas(String dniUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double obtenerSaldoDisponible(String cvu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa,
			double tasa) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String consultarCvu(String alias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialGlobal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialCuenta(String cvu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialUsuario(String dniUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double obtenerTotalInvertido(String dniUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> cuentasConMayorVolumen(int cantidadTop) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	

}
