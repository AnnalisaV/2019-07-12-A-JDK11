package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {

	private FoodDao dao; 
	private Map<Integer, Food> idMapFoods; 
	private Graph<Food, DefaultWeightedEdge> graph; 
	
	private Simulator s; 
	
	public Model() {
		this.dao= new FoodDao(); 
		this.idMapFoods= new HashMap<>(); 
		this.dao.listAllFoods(idMapFoods);
	}
	
	public void creaGrafo(int portion) {
		this.graph= new SimpleWeightedGraph<Food, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		
		//vertex
		Graphs.addAllVertices(this.graph, this.dao.getFoodByPortion(portion, idMapFoods)); 
		
		//edges 
		for (CoupleFoods c : this.dao.getCouples(idMapFoods)) {
			if (this.graph.containsVertex(c.getF1()) && this.graph.containsVertex(c.getF2())) {
				Graphs.addEdgeWithVertices(this.graph, c.getF1(), c.getF2(),c.getPeso()); 
			}
		}
		
	}
	
	public List<Food> getFoodVertex(){
		List<Food> lista= new ArrayList<>(this.graph.vertexSet()); 
		Collections.sort(lista);
		return lista; 
	}
	
	public int nVertex() {
		return this.graph.vertexSet().size(); 
	}
	public int nArchi() {
		return this.graph.edgeSet().size(); 
	}
	
	public List<FoodCal> getCalorieCongiunteMax(Food selezionato){
		List<FoodCal> max= new ArrayList<>(); //da visualizzare
		
		List<FoodCal> viciniCal= new ArrayList<>(); //da riempire convertitendo i Food in FoodCal
		List<Food> vicini= Graphs.neighborListOf(this.graph, selezionato);
		
		for (Food food : vicini) {
			double peso= this.graph.getEdgeWeight(graph.getEdge(selezionato, food)); 
			viciniCal.add(new FoodCal(food, peso));
			}
		
		Collections.sort(viciniCal);
		
		if (viciniCal.size()<5) {
			max= viciniCal; 
		}
		else {
			for (int i=0; i<5; i++) {
				max.add(viciniCal.get(i)); 
			}
		}
		return max; 
		
}
	
	public void simula(int k, Food food) {
		this.s= new Simulator(); 
		s.init(k, this.graph, food, this);
		
		s.run();
	}
	
	public int cibiPreparati() {
		return this.s.getCibiPreparati().size(); 
	}
	
	public double tempoPreparazione() {
		return this.s.getTime(); 
	}
}
