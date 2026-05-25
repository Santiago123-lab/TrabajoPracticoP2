

package clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Billetera implements IBilletera {
	
	private HashMap<String, Usuario> usuarios;
	private HashMap<String, Empresa> empresas;
	private HashMap<String, String> aliasCvu;
	
	public Billetera() {
		
        this.usuarios = new HashMap<>();
        this.empresas = new HashMap<>();
        this.aliasCvu= new HashMap<>();
		
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
		
		if(this.aliasCvu.containsKey(alias)) {
			
			throw new IllegalArgumentException ("El alias ya esta registrado");
		}
		
		if(dniUsuario==null || dniUsuario.isBlank()) {
			
			throw new IllegalArgumentException ("El DNI ingresado no es valido");
		}
		
		if(!this.usuarios.containsKey(dniUsuario)) {
			
			throw new IllegalArgumentException ("No existe usuario registrado con DNI: "+dniUsuario);
		}
		
		if(alias==null || alias.isBlank()) {
			
			throw new IllegalArgumentException ("Por favor, ingrese otro alias.");
		}
		
		Usuario u = this.usuarios.get(dniUsuario);
		Cuenta c = new CuentaRegular (alias);
		u.agregarCuenta(c.consultarCVU(), c); //Asocio la cuenta con el usuario
		this.aliasCvu.put(alias, c.consultarCVU());
		
		return c.consultarCVU();
	}

	@Override
	public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {
		
		if(alias==null || alias.isBlank()) {
			
			throw new IllegalArgumentException ("Por favor, ingrese otro alias.");
		}

		if(this.aliasCvu.containsKey(alias)) {
			
			throw new IllegalArgumentException ("El alias ya esta registrado");
		}
		
		if(dniUsuario==null || dniUsuario.isBlank()) {
			
			throw new IllegalArgumentException ("El DNI ingresado no es valido");
		}
		
		if(!this.usuarios.containsKey(dniUsuario)) {
			
			throw new IllegalArgumentException ("No existe usuario registrado con DNI: "+dniUsuario);
		}
		
		
		if(depositoInicial<500000) {
			
			throw new IllegalArgumentException ("Para abrir una cuenta PREMIUM, el deposito inicial debe ser mayor a $500.000");
			
		}
		
		Cuenta c = new CuentaPremium (alias, depositoInicial);
		Usuario u = this.usuarios.get(dniUsuario);
		u.agregarCuenta(c.consultarCVU(), c);
		this.aliasCvu.put(alias, c.consultarCVU());
		
		
		return c.consultarCVU();
	}

	@Override
	public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
		
		if(alias==null || alias.isBlank()) {
			
			throw new IllegalArgumentException ("Por favor, ingrese otro alias.");
		}
		
		if(this.aliasCvu.containsKey(alias)) {
			
			throw new IllegalArgumentException ("El alias ya esta registrado");
		}
		
		
		if(dniUsuario==null || dniUsuario.isBlank()) {
			
			throw new IllegalArgumentException ("El DNI ingresado no es valido");
		}
		
		if(!this.usuarios.containsKey(dniUsuario)) {
			
			throw new IllegalArgumentException ("No existe usuario registrado con DNI: "+dniUsuario);
		}
		
		if(cuitEmpresa==null || cuitEmpresa.isBlank()) {
			
			throw new IllegalArgumentException ("Por favor, ingrese un CUIT correcto.");
		}
		
		if(!this.empresas.containsKey(cuitEmpresa)) {
			
			throw new IllegalArgumentException ("La empresa no se encuentra registrada");
		}
		

		
		Empresa e = this.empresas.get(cuitEmpresa);
		
		if (e.consultarAutorizado(dniUsuario)) {
			
			Usuario u = this.usuarios.get(dniUsuario);
			Cuenta c = new CuentaCorporativa(alias, cuitEmpresa);
			u.agregarCuenta(c.consultarCVU(), c);
			this.aliasCvu.put(alias, c.consultarCVU());
			
			return c.consultarCVU();
		}

		
		throw new IllegalArgumentException ("El usuario no posee el permiso de la empresa.");
	}

	@Override
	public List<String> obtenerCuentas(String dniUsuario) {
		
		if(dniUsuario==null || dniUsuario.isBlank()) {
			
			throw new IllegalArgumentException ("El DNI ingresado no es valido");
		}
		
		if(!this.usuarios.containsKey(dniUsuario)) {
			
			throw new IllegalArgumentException ("No existe usuario registrado con DNI: "+dniUsuario);
		}
		
		Usuario u = this.usuarios.get(dniUsuario);
		
		return u.consultarCuentas();
	}

	@Override
	public double obtenerSaldoDisponible(String cvu) {

		if (cvu==null || cvu.isBlank()) {
			
			throw new IllegalArgumentException ("Por favor, ingrese un CVU valido");
		}
		
		for(Usuario u: this.usuarios.values()) {
			
			if(u.existeCuenta(cvu)) {
				
				Cuenta c = u.obtenerCuenta(cvu);
				
				return c.consultarSaldo();
			}
			
		}
		
		throw new IllegalArgumentException ("No existe una cuenta con el CVU: "+cvu);
	
	}

	@Override
	public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {
		
		if(cvuOrigen==null || cvuOrigen.isBlank()) {
			throw new IllegalArgumentException ("El CVU de origen no es valido");
		}
		
		if(cvuDestino==null || cvuDestino.isBlank()) {
			throw new IllegalArgumentException ("El CVU de destino no es valido");
		}
		
		if(monto<0) {
			throw new IllegalArgumentException("Por favor, ingrese un monto valido.");
		}
		
		Cuenta origen = null;
		Cuenta destino = null;
		String dniOrigen = null;
		String dniDestino = null;	
		
		//Busco cuenta de ORIGEN
		
		for(Usuario u: this.usuarios.values()) {
			
			if(u.existeCuenta(cvuOrigen)) {
				
				origen = u.obtenerCuenta(cvuOrigen);
				dniOrigen = u.consultarDni();
				
			}
		}
		
		if (origen==null) {
			
			throw new IllegalArgumentException ("No existe una cuenta asociada al CVU: "+cvuOrigen);
		}
		
		//Busco cuenta DESTINO
		
		for(Usuario u: this.usuarios.values()) {
			
			if(u.existeCuenta(cvuDestino)) {
				
				destino = u.obtenerCuenta(cvuDestino);
				dniDestino = u.consultarDni();
				
			}
		}
		
		if (destino==null) {
			
			throw new IllegalArgumentException ("No existe una cuenta asociada al CVU: "+cvuDestino);
		}
		
		if(!origen.puedeDebitar(monto)) {
			
			origen.agregarActividad(crearActividad(dniOrigen, dniDestino, origen.consultarCVU(), destino.consultarCVU(),monto,"Rechazado"));
			destino.agregarActividad(crearActividad(dniOrigen, dniDestino, origen.consultarCVU(), destino.consultarCVU(),monto,"Rechazado"));
			throw new IllegalArgumentException ("La cuenta de origen no posee saldo suficiente para debitar");
		}
		
		if(!destino.puedeAcreditar(monto)) {
			
			origen.agregarActividad(crearActividad(dniOrigen, dniDestino, origen.consultarCVU(), destino.consultarCVU(),monto,"Rechazado"));
			destino.agregarActividad(crearActividad(dniOrigen, dniDestino, origen.consultarCVU(), destino.consultarCVU(),monto,"Rechazado"));
			throw new IllegalArgumentException ("La cuenta destino no puede acreditar el saldo deseado");
		}
		
		origen.debitar(monto);
		destino.acreditar(monto);
		origen.agregarActividad(crearActividad(dniOrigen, dniDestino, origen.consultarCVU(), destino.consultarCVU(),monto,"Aprobado"));
		destino.agregarActividad(crearActividad(dniOrigen, dniDestino, origen.consultarCVU(), destino.consultarCVU(),monto,"Aprobado"));
		
		
		
	}

	@Override
	public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
		
		if(dni==null||dni.isBlank()) {
			
			throw new IllegalArgumentException("Por favor, ingrese un DNI valido");
		}
		
		if(!this.usuarios.containsKey(dni)) {
			
			throw new IllegalArgumentException("El DNI ingresado no esta registado en el sistema");
		}
		
		if(cvu==null||cvu.isBlank()) {
			
			throw new IllegalArgumentException("Por favor, ingrese un CVU valido");
		}
		
		if(monto<0) {
			
			throw new IllegalArgumentException("Por favor, ingrese un monto valido");
		}
		
		if(plazoDias<=0) {
			
			throw new IllegalArgumentException("Por favor, ingrese un plazo valido");
		}
		
		Cuenta c = buscarCuenta(cvu);
		
		Inversion inversion = c.crearInversionRentaFija(monto, plazoDias);
		
		return inversion.getId();
			

	}

	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa, double tasa) {
		
		if(dni==null||dni.isBlank()) {
			
			throw new IllegalArgumentException("Por favor, ingrese un DNI valido");
		}
		
		if(!this.usuarios.containsKey(dni)) {
			
			throw new IllegalArgumentException("El DNI ingresado no esta registado en el sistema");
		}
		
		if(cvu==null||cvu.isBlank()) {
			
			throw new IllegalArgumentException("Por favor, ingrese un CVU valido");
		}
		
		if(monto<0) {
			
			throw new IllegalArgumentException("Por favor, ingrese un monto valido");
		}
		
		if(plazoDias<=0) {
			
			throw new IllegalArgumentException("Por favor, ingrese un plazo valido");
		}
		
		if(tasa<0) {
			
			throw new IllegalArgumentException("Por favor, ingrese una tasa");
		}
		
		if(divisa==null||divisa.isBlank()) {
			
			throw new IllegalArgumentException("Por favor, ingrese una divisa valida");
		}
		
		Cuenta c = buscarCuenta(cvu);
		
		Inversion inversion = c.crearInversionDivisa(monto, plazoDias, tasa, divisa);
		
		return inversion.getId();

	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
		
		if(dni==null||dni.isBlank()) {
			
			throw new IllegalArgumentException("Por favor, ingrese un DNI valido");
		}
		
		if(!this.usuarios.containsKey(dni)) {
			
			throw new IllegalArgumentException("El DNI ingresado no esta registado en el sistema");
		}
		
		if(cvu==null||cvu.isBlank()) {
			
			throw new IllegalArgumentException("Por favor, ingrese un CVU valido");
		}
		
		if(monto<0) {
			
			throw new IllegalArgumentException("Por favor, ingrese un monto valido");
		}
		
		if(plazoDias<=0) {
			
			throw new IllegalArgumentException("Por favor, ingrese un plazo valido");
		}
		
		Cuenta c = buscarCuenta(cvu);
		
		if (c instanceof CuentaCorporativa) {
			
			Inversion inversion = c.crearInversionLiquidez(monto, plazoDias);
			return inversion.getId();	}
		
		throw new RuntimeException("La inversión en liquidez empresarial solo se puede realizar desde una cuenta corporativa");

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
		
		if(cvu==null || cvu.isBlank()) {
			throw new IllegalArgumentException("El CVU no es valido");
			
		}
		
		Cuenta c = buscarCuenta(cvu);
		
		List <String> lista = new ArrayList();
		
		for(Actividad act : c.consultarActividades()) {
			
			lista.add(act.toString());
					
		}
		
		
		return lista;
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
	
	private Cuenta buscarCuenta(String cvu) {
		
		if(cvu==null || cvu.isBlank()) {
			
			throw new IllegalArgumentException("Por favor, ingrese un cvu valido");
		}
		
		for(Usuario usuario : usuarios.values()) {
			Cuenta cuenta = usuario.buscarCuenta(cvu);
			if (cuenta!=null) {
				return cuenta;
			}
		}
		
		throw new IllegalArgumentException("Cuenta con CVU" + cvu + "no encontrada");
	}

	
	private Actividad crearActividad(String dniOrigen, String dniDestino, String cvuOrigen, String cvuDestino, double monto, String estado) {
		
		Actividad act = new Act_Transferencia (dniOrigen, dniDestino, cvuOrigen, cvuDestino, monto, estado);
		
		return act;
	}
	
	private Actividad crearActividad(String dniUsuario, String cvu, String tipoInversion, int plazo, double monto, String estado) {
		
		Actividad act = new Act_Inversion (dniUsuario,cvu,tipoInversion,plazo, monto, estado);
		
		return act;
	}
	
	

	

}
