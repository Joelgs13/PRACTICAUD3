package model;

import java.util.Objects;

public class ModeloEvento {
	
	private String nombreEvento;
	private ModeloDeporte deporte;
	private ModeloOlimpiada olimpiada;
	
	public ModeloEvento(String nombreEvento, ModeloDeporte deporte, ModeloOlimpiada olimpiada) {
		this.nombreEvento = nombreEvento;
		this.deporte = deporte;
		this.olimpiada = olimpiada;
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public ModeloDeporte getDeporte() {
		return deporte;
	}

	public ModeloOlimpiada getOlimpiada() {
		return olimpiada;
	}
	
	@Override
	public String toString() {
		return this.nombreEvento+","+this.deporte.getNombreDeporte()+","+this.olimpiada.getNombreOlimpiada();
	}

	@Override
	public int hashCode() {
		return Objects.hash(deporte, nombreEvento, olimpiada);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModeloEvento other = (ModeloEvento) obj;
		return Objects.equals(deporte, other.deporte) && Objects.equals(nombreEvento, other.nombreEvento)
				&& Objects.equals(olimpiada, other.olimpiada);
	}
	
	
	
}
