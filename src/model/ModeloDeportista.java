package model;

public class ModeloDeportista {
	
	private String nombreDeportista;
	private char sexo;
	private int altura;
	private float peso;
	
	public ModeloDeportista(String nombreDeportista, char sexo, int altura, float peso) {
		this.nombreDeportista = nombreDeportista;
		this.sexo = sexo;
		this.altura = altura;
		this.peso = peso;
	}

	public String getNombreDeportista() {
		return nombreDeportista;
	}

	public char getSexo() {
		return sexo;
	}

	public int getAltura() {
		return altura;
	}

	public float getPeso() {
		return peso;
	}
	
	@Override
	public String toString() {
		return this.nombreDeportista+","+this.sexo+","+this.altura+","+this.peso;
	}
	
}
