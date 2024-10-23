package model;

import java.util.Objects;

public class ModeloOlimpiada {
	
	@Override
	public int hashCode() {
		return Objects.hash(anio, ciudad, nombreOlimpiada, temporada);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModeloOlimpiada other = (ModeloOlimpiada) obj;
		return anio == other.anio && Objects.equals(ciudad, other.ciudad)
				&& Objects.equals(nombreOlimpiada, other.nombreOlimpiada) && Objects.equals(temporada, other.temporada);
	}

	private String nombreOlimpiada;
	private int anio;
	private String temporada;
	private String ciudad;
	
	public String getNombreOlimpiada() {
		return nombreOlimpiada;
	}

	public int getAnio() {
		return anio;
	}

	public String getTemporada() {
		return temporada;
	}

	public String getCiudad() {
		return ciudad;
	}

	public ModeloOlimpiada(String nombreOlimpiada, int anio, String temporada, String ciudad) {
		this.nombreOlimpiada = nombreOlimpiada;
		this.anio = anio;
		this.temporada = temporada;
		this.ciudad = ciudad;
	}
	
	@Override
	public String toString() {
		return this.nombreOlimpiada+","+this.anio+","+this.temporada+","+this.ciudad;
	}
	
}
