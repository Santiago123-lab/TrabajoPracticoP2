
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
		
		List <String> cuentas = new ArrayList<>();
		
		for(Cuenta c : u.consultarCuentas()) {
			
			cuentas.add(c.toString());
			
		}
		
		return cuentas;
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
//			throw new IllegalArgumentException ("La cuenta destino no puede acreditar el saldo deseado");
			throw new IllegalStateException("La cuenta destino superaria el limite permitido"); 
		}
		
		origen.debitar(monto);
		destino.acreditar(monto);

		Actividad act = crearActividad(dniOrigen, dniDestino, origen.consultarCVU(), destino.consultarCVU(), monto, "Aprobado"); 
		origen.agregarActividad(act); 
		destino.agregarActividad(act); 
 
		
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
		
		Actividad actividad = crearActividad(dni, cvu, "RentaFija", plazoDias, monto, "Aprobado");
		
		c.agregarActividad(actividad);
		
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
		
		Actividad actividad = crearActividad(dni, cvu, "Divisa", plazoDias, monto, "Aprobado");
	    c.agregarActividad(actividad);
		
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
			
			Actividad actividad = crearActividad(dni, cvu, "Liquidez", plazoDias, monto, "Aprobado");
		    c.agregarActividad(actividad);
		        
			return inversion.getId();	}
		

//		throw new RuntimeException("La inversión en liquidez empresarial solo se puede realizar desde una cuenta corporativa");
		throw new IllegalArgumentException("La inversión en liquidez empresarial solo se puede realizar desde una cuenta corporativa");

//		throw new RuntimeException("La inversión en liquidez empresarial solo se puede realizar desde una cuenta corporativa");


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
		
		double monto = inversion.getMonto(); 
		
		cuenta.acreditar(monto);   

		double ganancia = inversion.calcularGanancia();
		
		double gananciaFinal = ganancia / 2;
		
		cuenta.acreditar(monto + gananciaFinal); 

		
		cuenta.descontarSaldoInvertido(monto); 
		
		cuenta.eliminarInversiones(idInversion); 
		
	}

	@Override
	public String consultarCvu(String alias) {
//		
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
	
	private int cantidadCuentasTotales() {

	    int total = 0;

	    for(Usuario u : this.usuarios.values()) {

	        total += u.consultarCuentas().size();
	    }

	    return total;
	}
	
	

	

}
