package it.polito.tdp.food.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model(); 
		
		List<FoodCalories> lista= model.cibiConnessiCalorieMax(new Food(16126, "Tofu, firm")); 

		System.out.println(lista.size()); 
		for (FoodCalories f : lista) {
			System.out.println(f.toString()+"\n"); 
		}
	}

}
