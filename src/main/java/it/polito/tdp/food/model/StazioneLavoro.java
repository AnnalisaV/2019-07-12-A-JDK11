package it.polito.tdp.food.model;

public class StazioneLavoro {

	private boolean libera; 
	private Food food; //riferimento al food nella StazioneLavoro
	
	public StazioneLavoro(Food f) {
		this.libera= true; 
		this.food=f; 
	}

	public boolean isLibera() {
		return libera;
	}

	public void setLibera(boolean libera) {
		this.libera = libera;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}
	
	
	
}

