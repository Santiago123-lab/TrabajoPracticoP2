package clases;

public class Act_Inversion extends Actividad {
	
	private String tipoInversion;
	private int plazo;
	private String cvu;
	
	public Act_Inversion(String dniUsuario, String cvu, String tipoInversion, int plazo, double monto, String estado) {
		
		super(dniUsuario,monto,estado);
		
		this.tipoInversion=tipoInversion;
		this.cvu=cvu;
		this.plazo=plazo;
		
	}
	
	public String toString() {
		
//	     * - inversion:
//	         * ```
//	         * origen: [dni] ([cvu])
//	         * desc: [tipo inversion]
//	         * monto: [monto]
//	         * plazo: [plazo]
//	         * [Aprovado/Rechazado]
		
		StringBuilder st= new StringBuilder();
		
		
		st.append("===============\n");
		st.append("       Inversion: \n");
		st.append("      ===============\n");
		st.append("       Origen: [");
		st.append(this.dniOrigen);
		st.append("] ([");
		st.append(this.cvu);
		st.append("])\n");
		
		st.append("       Desc: [");
		st.append(this.tipoInversion);
		st.append("]\n");
		
		st.append("       Monto: [");
		st.append(this.monto);
		st.append("]\n");
		
		st.append("       Plazo: [");
		st.append(this.plazo);
		st.append("]\n");
		
		st.append("       [");
		st.append(this.estado);
		st.append("]\n");
		
		st.append("      ===============\n");
		
		return st.toString();

		
	}

}
