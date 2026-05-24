package clases;

public class Act_Transferencia extends Actividad{
	
	private String dniDestino;
	private String cvuOrigen;
	private String cvuDestino;
	
	public Act_Transferencia(String dniOrigen, String dniDestino, String cvuOrigen, String cvuDestino, double monto, String estado) {
		super(dniOrigen,monto,estado);
		this.dniDestino=dniDestino;
		this.cvuOrigen=cvuOrigen;
		this.cvuDestino=cvuDestino;
		
	}
	
	public String toString() {
		
		StringBuilder st= new StringBuilder();
		
		st.append("===============\n");
		st.append(" Transferencia: \n");
		st.append("===============\n");
		st.append("Origen: [");
		st.append(this.dniOrigen);
		st.append("] ([");
		st.append(this.cvuOrigen);
		st.append("])\n");
		
		st.append("Destino: [");
		st.append(this.dniDestino);
		st.append("] ([");
		st.append(this.cvuDestino);
		st.append("])\n");
		
		st.append("Monto: [");
		st.append(this.monto);
		st.append("]\n");
		
		st.append("[");
		st.append(this.estado);
		st.append("]\n");
		
		st.append("===============\n");
		
		return st.toString();

		
	}

}
