package model;

import java.util.ArrayList;

public class ModeloParticipacion {
	
	private ModeloDeportista deportista;
	private ModeloEvento evento;
	private ModeloEquipo equipo;
	private int edad;
	private String medalla;
	
	public ModeloParticipacion(ModeloDeportista deportista,ModeloEvento evento,ModeloEquipo equipo, int edad, String medalla) {
		this.deportista=deportista;
		this.evento=evento;
		this.equipo = equipo;
		this.edad = edad;
		this.medalla = medalla;
	}
	
	@Override
	public String toString() {
		return this.deportista.getNombreDeportista()+","+this.deportista.getAltura()+","+this.deportista.getPeso()+
				","+this.edad+","+this.equipo.getNombreEquipo()+","+this.medalla;
	}

	public ModeloDeportista getDeportista() {
		return deportista;
	}

	public ModeloEvento getEvento() {
		return evento;
	}

	public ModeloEquipo getEquipo() {
		return equipo;
	}

	public int getEdad() {
		return edad;
	}

	public String getMedalla() {
		return medalla;
	}
	
}
