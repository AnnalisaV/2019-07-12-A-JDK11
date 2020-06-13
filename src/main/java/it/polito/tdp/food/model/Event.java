package it.polito.tdp.food.model;

public class Event implements Comparable<Event>{

	private Double time; 
	private Food food; 
	private StazioneLavoro stazione; 
	private EventType type; 
	
	public enum EventType {
		CIBO_DA_PREPARARE, 
		CIBO_PREPARATO, 
	}

	/**
	 * @param time
	 * @param food
	 * @param stazione
	 * @param type
	 */
	public Event(Double time, Food food, StazioneLavoro stazione, EventType type) {
		super();
		this.time = time;
		this.food = food;
		this.stazione = stazione;
		this.type = type;
	}

	public Double getTime() {
		return time;
	}

	public Food getFood() {
		return food;
	}

	public StazioneLavoro getStazione() {
		return stazione;
	}

	public EventType getType() {
		return type;
	}

	//time crescente 
	@Override
	public int compareTo(Event o) {
		
		return this.time.compareTo(o.time);
	}
	
	
}

