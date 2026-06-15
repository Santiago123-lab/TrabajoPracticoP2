
package clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Billetera implements IBilletera {
	
	private HashMap<String, Usuario> usuarios;
	private HashMap<String, Empresa> empresas;
	private HashMap<String, String> aliasCvu;
	private static int idInversion;

	
	public Billetera() {
		
        this.usuarios = new HashMap<>();
        this.empresas = new HashMap<>();
        this.aliasCvu= new HashMap<>();
        this.idInversion = 1;



	}
	

	@Override
	public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email,
			String nombreContacto) {
		

		
		if(this.empresas.containsKey(cuit)) {
			
			throw new IllegalArgumentException("El CUIT: "+cuit+", ya esta registrado en el sistema");
		}
		
		Empresa nuevaEmpresa = new Empresa(cuit,nombreFantasia,telefono,email,nombreContacto);
		this.empresas.put(cuit, nuevaEmpresa);
		
		
	}

	@Override
	public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) {
		
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
		
		Usuario u = this.usuarios.get(dniUsuario);
		Cuenta c = new CuentaRegular (alias);
		u.agregarCuenta(c.consultarCVU(), c); //Asocio la cuenta con el usuario
		this.aliasCvu.put(alias, c.consultarCVU());
		
		return c.consultarCVU();
	}

	@Override
	public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {
		
		if(this.aliasCvu.containsKey(alias)) {
			
			throw new IllegalArgumentException ("El alias ya esta registrado");
		}
		
		if(dniUsuario==null || dniUsuario.isBlank()) {
			
			throw new IllegalArgumentException ("El DNI ingresado no es valido");
		}
		
		if(!this.usuarios.containsKey(dniUsuario)) {
			
			throw new IllegalArgumentException ("No existe usuario registrado con DNI: "+dniUsuario);
		}
		
		
		Cuenta c = new CuentaPremium (alias, depositoInicial);
		Usuario u = this.usuarios.get(dniUsuario);
		u.agregarCuenta(c.consultarCVU(), c);
		this.aliasCvu.put(alias, c.consultarCVU());
		
		
		return c.consultarCVU();
	}

	@Override
	public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
		
		if(this.aliasCvu.containsKey(alias)) {
			
			throw new IllegalArgumentException ("El alias ya esta registrado");
		}
		
		
		if(dniUsuario==null || dniUsuario.isBlank()) {
			
			throw new IllegalArgumentException ("El DNI ingresado no es valido");
		}
		
		if(!this.usuarios.containsKey(dniUsuario)) {
			
			throw new IllegalArgumentException ("No existe usuario registrado con DNI: "+dniUsuario);
		}
		
		
		if(!this.empresas.containsKey(cuitEmpresa)) {
			
			throw new IllegalArgumentException ("La empresa no se encuentra registrada");
		}
		

		
		Empresa e = this.empresas.get(cuitEmpresa);
		
		if (e.estaAutorizado(dniUsuario)) {
			
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
		
		return u.obtenerCuentas();
	}

	@Override
	public double obtenerSaldoDisponible(String cvu) {

		if (cvu==null || cvu.isBlank()) {
			
			throw new IllegalArgumentException ("Por favor, ingrese un CVU valido");
		}
		
		for(Usuario u: this.usuarios.values()) {
			
			if(u.existeCuenta(cvu)) {
				
				return u.obtenerSaldoCuenta(cvu);
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
		
		Usuario usuarioOrigen = null;
		Usuario usuarioDestino = null;
		
		//Busco usuario de ORIGEN
		
		for(Usuario u: this.usuarios.values()) {
			
			if(u.existeCuenta(cvuOrigen)) {
				
				usuarioOrigen = u;
				
			}
		}
		
		if (usuarioOrigen==null) {
			
			throw new IllegalArgumentException ("No existe una cuenta asociada al CVU: "+cvuOrigen);
		}
		
		//Busco usuario DESTINO
		
		for(Usuario u: this.usuarios.values()) {
			
			if(u.existeCuenta(cvuDestino)) {
				
				usuarioDestino= u;
				
			}
		}
		
		if (usuarioDestino==null) {
			
			throw new IllegalArgumentException ("No existe una cuenta asociada al CVU: "+cvuDestino);
		}
		
		usuarioOrigen.hacerTransferencia(cvuOrigen, cvuDestino, usuarioDestino, monto);
		
	}
	
	@Override
	public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
		
		if(dni==null||dni.isBlank()) {
			
			throw new IllegalArgumentException("Por favor, ingrese un DNI valido");
		}
		
		if(!this.usuarios.containsKey(dni)) {
			
			throw new IllegalArgumentException("El DNI ingresado no esta registado en el sistema");
		}
		
		
		if(monto<0) {
			
			throw new IllegalArgumentException("Por favor, ingrese un monto valido");
		}
		
		if(plazoDias<=0) {
			
			throw new IllegalArgumentException("Por favor, ingrese un plazo valido");
		}
		
		Usuario u = usuarios.get(dni); 
		
		int id = idInversion++;
		Inversion i = new RentaFija(id, plazoDias, monto);
		u.agregarInversion(cvu, i);

		return i.consultarId();	
	}

	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa, double tasa) {
		
		if(dni==null||dni.isBlank()) {
			
			throw new IllegalArgumentException("Por favor, ingrese un DNI valido");
		}
		
		if(!this.usuarios.containsKey(dni)) {
			
			throw new IllegalArgumentException("El DNI ingresado no esta registado en el sistema");
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
		
		Usuario u = usuarios.get(dni); 
		
		int id = idInversion++;
		Inversion i = new Divisa(id, plazoDias, monto, tasa, divisa);
		u.agregarInversion(cvu, i);

		return i.consultarId();	
	}
	
	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
		
		if(dni==null||dni.isBlank()) {
			
			throw new IllegalArgumentException("Por favor, ingrese un DNI valido");
		}
		
		if(!this.usuarios.containsKey(dni)) {
			
			throw new IllegalArgumentException("El DNI ingresado no esta registado en el sistema");
		}
		
		
		if(monto<0) {
			
			throw new IllegalArgumentException("Por favor, ingrese un monto valido");
		}
		
		if(plazoDias<=0) {
			
			throw new IllegalArgumentException("Por favor, ingrese un plazo valido");
		}
		
		Usuario u = usuarios.get(dni); 
		
		int id = idInversion++;
		Inversion i = new Liquidez(id, plazoDias, monto);
		u.agregarInversion(cvu, i);

		return i.consultarId();	
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) {
		if(dni == null || dni.isBlank()) {
			throw new IllegalArgumentException("Por favor, ingrese un DNI valido"); 
		}
		if(!usuarios.containsKey(dni)) {
			throw new IllegalArgumentException("Usuario inexistente"); 
		}
		if(cvu == null || cvu.isBlank()) {
			throw new IllegalArgumentException("Por favor, ingrese un cvu valido"); 
		}
		
		Cuenta cuenta = buscarCuenta(cvu); 
		
		Inversion inversion = cuenta.obtenerInversion(idInversion); 
		
		if(inversion == null) {
			throw new IllegalArgumentException("Inversion inexistente"); 
		}
		
		if(!inversion.esPrecancelable()) {
			throw new IllegalArgumentException("La inversion no es precancelable"); 
		}
		
		double monto = inversion.consultarMonto(); 
		 
		double gananciaFinal = inversion.precancelar();
		
		cuenta.acreditar(gananciaFinal); 
		
		System.out.println("saldo despues = " + cuenta.consultarSaldo());
		
		cuenta.descontarSaldoInvertido(monto); 
		
		cuenta.eliminarInversiones(idInversion); 
		
		

		
		
	}

	@Override
	public String consultarCvu(String alias) {
		
		if(alias == null || alias.isBlank()) { 
			throw new IllegalArgumentException("Alias invalido"); 
		}
		if(!aliasCvu.containsKey(alias)) {
			throw new IllegalArgumentException("Alias inexistente"); 
		}
		return aliasCvu.get(alias); 

	}

	@Override
	public List<String> consultarHistorialGlobal() {
		
		List <Actividad>listaAct = new ArrayList<>();
		
		for(Usuario u: this.usuarios.values()) {
			
			listaAct.addAll(u.consultarActividades());
			
		}
		
		List <String> listaStr = new ArrayList<>();
		
		for(Actividad act : listaAct) {
			
			listaStr.add(act.toString());
			
		}
		
		return listaStr;
		


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

		if(dniUsuario == null || dniUsuario.isBlank()) {
			throw new IllegalArgumentException("DNI Invalido"); 
		}
		if(!usuarios.containsKey(dniUsuario)) {
			throw new IllegalArgumentException("Usuario inexistente"); 
		}
		
		Usuario u = usuarios.get(dniUsuario); 
		
		List<String> historial = new ArrayList<>(); 
		
		for(Actividad act : u.consultarActividades()) {
			historial.add(act.toString()); 
		}
		return historial;
	}

	@Override
	public double obtenerTotalInvertido(String dniUsuario) {
		
		if(dniUsuario == null || dniUsuario.isBlank()) {
			throw new IllegalArgumentException("DNI Invalido"); 
		}
		if(!usuarios.containsKey(dniUsuario)) {
			throw new IllegalArgumentException("Usuario inexistente"); 
		}
		
		Usuario u = this.usuarios.get(dniUsuario);
		
		
		return u.consultarSaldoInvertido();
	}

	@Override
	public List<String> cuentasConMayorVolumen(int cantidadTop) {

		if(cantidadTop<1) {
			throw new IllegalArgumentException ("Ingrese un valor mayor o igual a 1");
		}
		
		if(cantidadTop>cantidadCuentasTotales()) {
			
			throw new IllegalArgumentException ("Ingrese un numero menor.");
		}
		
		List <Cuenta> listaCuentas = new ArrayList <>();
		Cuenta max = null;
		
		while (listaCuentas.size()<cantidadTop) {
			
			for(Usuario u : this.usuarios.values()) {
				
				for(Cuenta c : u.consultarCuentas()) {
					
					if(max==null) {
						
						if(!listaCuentas.contains(c)) {
							
							max=c;
						}
					}
					else {
						
						if(!listaCuentas.contains(c) && max.consultarActividades().size() < c.consultarActividades().size()) {
							
							max = c;
						}
						
					}
				}
			}
			
			listaCuentas.add(max);
			max=null;
					
		}
		
		List <String> listaTop = new ArrayList<>();
		
		for(Cuenta c: listaCuentas) {
			
			listaTop.add(c.toString());
			
		}
		
		return listaTop;
	}
	
	public String toString() {
		
		StringBuilder st = new StringBuilder();
		
		st.append("\n");
		st.append("- Cantidad de usuarios del sistema: ");
		st.append(this.usuarios.size());
		st.append("\n");
		st.append("- Cantidad de empresas del sistema: ");
		st.append(this.empresas.size());
		st.append("\n");
		st.append("- Cantidad de cuentas del sistema: ");
		st.append(this.aliasCvu.size());
		st.append("\n");
		
		return st.toString();
	}
	
	private Cuenta buscarCuenta(String cvu) {
		
		if(cvu==null || cvu.isBlank()) {
			
			throw new IllegalArgumentException("Por favor, ingrese un cvu valido");
		}
		
		for(Usuario usuario : usuarios.values()) {
			Cuenta cuenta = usuario.obtenerCuenta(cvu);
			if (cuenta!=null) {
				return cuenta;
			}
		}
		
		throw new IllegalArgumentException("Cuenta con CVU" + cvu + "no encontrada");
	}

	private int cantidadCuentasTotales() {

	    int total = 0;

	    for(Usuario u : this.usuarios.values()) {

	        total += u.consultarCuentas().size();
	    }

	    return total;
	}
	
	

	

}
