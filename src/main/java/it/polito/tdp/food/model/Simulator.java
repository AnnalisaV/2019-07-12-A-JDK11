package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Event.EventType;
import it.polito.tdp.food.model.Food.statoPreparazione;

public class Simulator {

	
	//mondo
	private List<Stazione> listaStazioni;
	private List<Food> foods; 
	
	private Graph <Food, DefaultWeightedEdge> graph; 
	private Model model; //mi servono alcuni suoi metodi
	
	
	//parametri di simulazione
    private int K =5; //numero di Stazioni disponibili , metto un valore di default che puo' essere modificato obv
	
	public void setK(int k) {
		K = k;
	}
	
    public int getK() {
		return K;
	}

	//output
    private Double tempoPreparazione; 
    private int foodPreparati; 
    

	public Double getTempoPreparazione() {
		return tempoPreparazione;
	}
	public Integer getFoodPreparati() {
		return this.foodPreparati; 
	}



	
	// coda eventi
    private PriorityQueue<Event> queue; 
	



	public Simulator(Graph<Food, DefaultWeightedEdge> graph,Model model) {
		this.graph= graph; 
		this.model= model; 
		
	}
	
	
	public void init(Food partenza) {
		this.foods= new ArrayList<>(this.graph.vertexSet()); //per comodita' li ho ho come List anziche' Set
		//tutti da preparare 
		for (Food cibo : this.foods) {
			cibo.setPreparazione(statoPreparazione.DA_PREPARARE);
		}
		
		this.listaStazioni= new ArrayList<>(); 
		//metto le stazioni, ora tutte disponibili con nessun Food associato
		for(int i=0; i<this.K; i++) {
			this.listaStazioni.add(new Stazione(true, null)); 
		}
		
		this.tempoPreparazione=0.0; 
		this.foodPreparati=0; 
		
		this.queue= new PriorityQueue<Event>(); 
		//creo eventi 
		List<FoodCalories> vicini= model.cibiConnessiCalorieMax(partenza); 
		
		for (int i=0; i<this.K && i<vicini.size(); i++) {
			this.listaStazioni.get(i).setLibera(false); //diventa occupata 
			this.listaStazioni.get(i).setFood(vicini.get(i).getFood()); //da questo cibo 
			
			                        //minuti
			Event e = new Event (vicini.get(i).getCal(),this.listaStazioni.get(i), 
					vicini.get(i).getFood(),EventType.FINE_PREPARAZIONE) ; 
			
		    this.queue.add(e); 
		}
		
		
		
	}
	
	public void run() {
		
		while(!queue.isEmpty()) {
			Event e= this.queue.poll(); 
			processEvent(e); 
		}
		
	}

	private void processEvent(Event e) {
		
		switch(e.getType()) {
		
		case INIZIO_PREPARAZIONE:
			
			List<FoodCalories> vicini = model.cibiConnessiCalorieMax(e.getFood()); 
			FoodCalories prossimo =null; 
			for (FoodCalories f : vicini) {
				if(f.getFood().getPreparazione()==statoPreparazione.DA_PREPARARE) {
					prossimo= f; 
					break; //non vado avanti
				}
				
			}
			
			if(prossimo!=null) {
				prossimo.getFood().setPreparazione(statoPreparazione.IN_CORSO);
				e.getStaz().setLibera(false);
				e.getStaz().setFood(prossimo.getFood());
				                   //time+il peso dell'arco
				Event e2= new Event(e.getTime()+prossimo.getCal(),e.getStaz(),
						prossimo.getFood(), EventType.FINE_PREPARAZIONE ); 
				this.queue.add(e2); 
			}
			break; 
		case FINE_PREPARAZIONE: 
			         //finita la preparazione del cibo e si e' liberata una stazione
			this.foodPreparati++; 
			this.tempoPreparazione= e.getTime(); 
			e.getStaz().setLibera(true);
			e.getFood().setPreparazione(statoPreparazione.PREPARATO);
			
			                  //stesso time
			Event e2= new Event(e.getTime(), e.getStaz(), e.getFood(), EventType.INIZIO_PREPARAZIONE); 
			this.queue.add(e2); 
			break; 
		
		}
		
	}
}
