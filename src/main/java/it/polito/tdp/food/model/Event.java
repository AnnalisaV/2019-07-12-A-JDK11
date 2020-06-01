package it.polito.tdp.food.model;

public class Event implements Comparable<Event> {

	private EventType type; 
	
	public enum EventType{
		INIZIO_PREPARAZIONE,  // cibo assegnato alla stazione (libera)
		FINE_PREPARAZIONE,    // cibo pronto in quella stazione 
	}
	
	private Double time; //in min
	
	private Stazione staz; 
	private Food food;
	
	
	
	/**
	 * @param time
	 * @param staz
	 * @param food
	 */
	public Event(Double time, Stazione staz, Food food, EventType type) {
		super();
		this.time = time;
		this.staz = staz;
		this.food = food;
		this.type= type; 
	}
	public double getTime() {
		return time;
	}
	public void setTime(Double time) {
		this.time = time;
	}
	public Stazione getStaz() {
		return staz;
	}
	public void setStaz(Stazione staz) {
		this.staz = staz;
	}
	public Food getFood() {
		return food;
	}
	public void setFood(Food food) {
		this.food = food;
	}
	public EventType getType() {
		return this.type; 
	}
	
	//sul time 
	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.time);
	} 
	
	
	
	
	
}
