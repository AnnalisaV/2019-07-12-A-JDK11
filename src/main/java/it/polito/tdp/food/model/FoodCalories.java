package it.polito.tdp.food.model;

public class FoodCalories implements Comparable<FoodCalories> {

	private Food food; 
	private Double cal;
	/**
	 * @param food
	 * @param cal
	 */
	public FoodCalories(Food food, Double cal) {
		super();
		this.food = food;
		this.cal = cal;
	}
	
	
	
	public Food getFood() {
		return food;
	}

    public void setFood(Food food) {
		this.food = food;
	}

    public double getCal() {
		return cal;
	}

    public void setCal(Double cal) {
		this.cal = cal;
	}



	//sulle calorie in ordine decrescente 
	@Override
	public int compareTo(FoodCalories other) {
		return -(this.cal.compareTo(other.cal));
		//NO this.cal - other.cal 
	}



	@Override
	public String toString() {
		return this.food.toString()+ " "+this.cal;
	}
	
	
}
