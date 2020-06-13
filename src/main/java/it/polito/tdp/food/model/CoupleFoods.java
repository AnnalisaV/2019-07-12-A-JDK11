package it.polito.tdp.food.model;

public class CoupleFoods {

	private Food f1; 
	private Food f2; 
	private Double  peso; //media delle calorie degli ingredienti in comune
	/**
	 * @param f1
	 * @param f2
	 * @param peso
	 */
	public CoupleFoods(Food f1, Food f2, double peso) {
		super();
		this.f1 = f1;
		this.f2 = f2;
		this.peso = peso;
	}
	public Food getF1() {
		return f1;
	}
	public Food getF2() {
		return f2;
	}
	public double getPeso() {
		return peso;
	}
	@Override
	public String toString() {
		return f1.toString()+" "+f2.toString()+" "+peso+" cal";
	}
	
	
}
