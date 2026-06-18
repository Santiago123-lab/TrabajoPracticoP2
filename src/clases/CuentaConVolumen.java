package clases;

public class CuentaConVolumen implements Comparable<CuentaConVolumen> {

    private String cuenta;
    private int cantidadActividades;

    public CuentaConVolumen(String cuenta, int cantidadActividades) {
    	
    	if(cuenta==null || cuenta.isBlank()) {
    		
    		throw new IllegalArgumentException ("La informacion de la cuenta no es valida");
    		
    	}
    	
    	if(cantidadActividades<0) {
    		
    		throw new IllegalArgumentException ("La cantidad de actividades de la cuenta no es positiva.");
    		
    	}
    	
    	
        this.cuenta = cuenta;
        this.cantidadActividades = cantidadActividades;
    }

    public String consultarCuenta() {
        return cuenta;
    }

    public int consultarCantidadActividades() {
        return cantidadActividades;
    }

    @Override
    public int compareTo(CuentaConVolumen otra) {
        return Integer.compare(this.cantidadActividades, otra.cantidadActividades);
    }
}
