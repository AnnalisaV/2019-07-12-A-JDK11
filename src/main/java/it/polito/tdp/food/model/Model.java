package it.polito.tdp.food.model;

import java.util.List;
import java.util.Map;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao; 
	private List<Food> foods; 
	
	public Model() {
		
	}
	
	public List<Food> getFoods(int portion){
		this.dao= new FoodDao(); 
		this.foods=  dao.getFoodByPortions(portion); 
		return foods; 
	}
	

}
