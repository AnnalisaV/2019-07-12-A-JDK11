package it.polito.tdp.food.model;

public class FoodCal implements Comparable<FoodCal> {

	private Food food; 
	private Double cal;
	/**
	 * @param food
	 * @param cal
	 */
	public FoodCal(Food food, Double cal) {
		super();
		this.food = food;
		this.cal = cal;
	}
	public Food getFood() {
		return food;
	}
	public Double getCal() {
		return cal;
	}
	@Override
	public String toString() {
		return food.toString()+" "+cal;
	}
	
	//cal decrescenti
	@Override
	public int compareTo(FoodCal o) {
		
		return o.cal.compareTo(this.cal);
	} 
	
	
}
