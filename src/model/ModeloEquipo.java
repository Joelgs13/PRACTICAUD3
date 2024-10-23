package model;

public class ModeloEquipo {
	
	private String nombreEquipo;
	private String iniciales;
	
	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public String getIniciales() {
		return iniciales;
	}

	public ModeloEquipo(String nombreEquipo, String iniciales) {
		this.nombreEquipo = nombreEquipo;
		this.iniciales = iniciales;
	}
	
}
