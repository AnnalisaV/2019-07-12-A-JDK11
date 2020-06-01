package it.polito.tdp.food.model;

import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao; 
	private List<Food> foods; 
	private Graph<Food, DefaultWeightedEdge> graph; 
	
	
	public Model() {
		
	}
	
	/**
	 * 
	 * @param portion porzione da considerare 
	 * @return lista di {@code Food} con porzione richiesta 
	 */
	public List<Food> getFoods(int portion){
		this.dao= new FoodDao(); 
		this.foods=  dao.getFoodByPortions(portion); 
		return foods; 
	}
	

	public void creaGrafo(int portion) {
		this.graph= new SimpleWeightedGraph<>(DefaultWeightedEdge.class); 
		
		//vertici
		Graphs.addAllVertices(this.graph, this.foods); 
		
		//archi 
		// qui va bene il primo metodo perche' sono pochi i vertici, max un minuto ed ho il risultato
		for (Food f1 : this.foods) {
			for (Food f2 : this.foods) {
				                       // non orientato
				if (!f1.equals(f2) && f1.getFood_code()<f2.getFood_code()) {
					Double peso= dao.calorieCongiunte(f1, f2); 
					if (peso!=null) {
						Graphs.addEdge(this.graph, f1, f2, peso); 
					}
					
				}
			}
		}
	}
	
	public int nVertex() {
		return this.graph.vertexSet().size(); 
	}
	
	public int nEdges() {
		return this.graph.edgeSet().size(); 
	}
}
