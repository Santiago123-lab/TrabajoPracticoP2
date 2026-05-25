package clases;

public class Act_Transferencia extends Actividad{
	
	private String dniDestino;
	private String cvuOrigen;
	private String cvuDestino;
	
	public Act_Transferencia(String dniOrigen, String dniDestino, String cvuOrigen, String cvuDestino, double monto, String estado) {
<<<<<<< HEAD
		super(dniOrigen, monto, estado);
=======
		super(dniOrigen,monto,estado);
>>>>>>> 652244d6399da5c92ec8def31cb63c0b12a5a5ca
		this.dniDestino=dniDestino;
		this.cvuOrigen=cvuOrigen;
		this.cvuDestino=cvuDestino;
		
	}
	
	public String toString() {
		
		StringBuilder st= new StringBuilder();
		
		st.append("===============\n");
		st.append(" Transferencia: \n");
		st.append("===============\n");
<<<<<<< HEAD
//		st.append("fecha: ["); 
//		st.append(this.fecha); 
//		st.append("]\\n"); 
		st.append("origen: [");
=======
		st.append("Origen: [");
>>>>>>> 652244d6399da5c92ec8def31cb63c0b12a5a5ca
		st.append(this.dniOrigen);
		st.append("] ([");
		st.append(this.cvuOrigen);
		st.append("])\n");
		
<<<<<<< HEAD
		st.append("destino: [");
=======
		st.append("Destino: [");
>>>>>>> 652244d6399da5c92ec8def31cb63c0b12a5a5ca
		st.append(this.dniDestino);
		st.append("] ([");
		st.append(this.cvuDestino);
		st.append("])\n");
		
<<<<<<< HEAD
		st.append("monto: [");
=======
		st.append("Monto: [");
>>>>>>> 652244d6399da5c92ec8def31cb63c0b12a5a5ca
		st.append(this.monto);
		st.append("]\n");
		
		st.append("[");
		st.append(this.estado);
		st.append("]\n");
		
		st.append("===============\n");
		
		return st.toString();

		
	}

}
