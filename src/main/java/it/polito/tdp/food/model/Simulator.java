package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Event.EventType;

public class Simulator {

	// parametri di simulazione
	private int K; //numero di stazioni di lavoro
	private Graph<Food, DefaultWeightedEdge> graph; 
	private Model model; 
	
	//modello del mondo
	private List<StazioneLavoro> listaStazioniLavoro; 
	
	//output
	private List<Food> cibiPreparati; 
	private double time;  //tempo necessario alla preparazione
	
	
	
	public List<Food> getCibiPreparati() {
		return cibiPreparati;
	}

	public double getTime() {
		return time;
	}

	//coda degli eventi
	private PriorityQueue<Event> queue; 
	
	
	public void init(int k, Graph<Food, DefaultWeightedEdge> graph, Food foodPartenza, Model m) {
		
		//impostazione parametri
		this.K=k; 
		this.graph=graph; 
		this.model=m; 
		
	this.queue= new PriorityQueue<>(); 
	this.cibiPreparati= new ArrayList<>(); //nuova all'inizio non ci sono cibi preparati
	this.time=0.0; 
		
	// all'inizio tutti i possibili cibi sono da preparare 
	 for (Food f : this.model.getFoodVertex()) {
		 f.setDaPreparare(true);
	 }
		this.listaStazioniLavoro= new ArrayList<>(); 
		for (int i=0; i<K; i++) {
			listaStazioniLavoro.add(new StazioneLavoro(null)); 
		}
		
		List<FoodCal> cibi= this.model.getCalorieCongiunteMax(foodPartenza); 
		
		// lo faccio per le K stazioneLavoro ma se vi fossero meno cibi allora sto sotto cibi.size()
		for (int i=0;i<K && i<cibi.size(); i++) {
			double time= cibi.get(i).getCal(); 
			Event e = new Event(time, cibi.get(i).getFood(), listaStazioniLavoro.get(i),EventType.CIBO_DA_PREPARARE); 
		    this.queue.add(e); 
		    
		    //la stazioneLavoro risulta occupata da un cibo
		    listaStazioniLavoro.get(i).setLibera(false);
		    listaStazioniLavoro.get(i).setFood(cibi.get(i).getFood()); 
		}
	
		
		
	}
	
	public void run() {
		while(!this.queue.isEmpty()){
			Event e= this.queue.poll(); 
			processEvent(e); 
		}
	}

	private void processEvent(Event e) {


		switch(e.getType()) {
		case CIBO_DA_PREPARARE:
			
			List<FoodCal> vicini= this.model.getCalorieCongiunteMax(e.getFood()); //tra cui scegliere quello che sara preparato
			FoodCal next= null; 
			for (FoodCal food : vicini) {
				//se e' da preparare ancora l'ho trovato come next
				if (food.getFood().isDaPreparare()==true) {
					next= food; 
					break; 
				}
			}
			
			//c'e' un next da preparare
			if(next!=null) {
				
				Event event= new Event(e.getTime()+next.getCal(), next.getFood(), e.getStazione(), EventType.CIBO_PREPARATO); 
				this.queue.add(event); 
				
				e.getStazione().setLibera(false);
				e.getStazione().setFood(next.getFood());
			}
			
			break; 
			
		case CIBO_PREPARATO : 
			e.getStazione().setLibera(true);
			cibiPreparati.add(e.getFood()); 
			e.getFood().setDaPreparare(false);
			
			//passo il riferimento a quello appena preparato epr poter scegliere cosa preparare dopo
			Event event= new Event(e.getTime(), e.getFood(), e.getStazione(), EventType.CIBO_DA_PREPARARE);
			this.queue.add(event); 
			
			this.time= e.getTime(); 
			break; 
		}
		
	}
}
