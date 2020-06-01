package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	/**
	 * {@code Food} con un particolare quantita' di porzione
	 */
	public List<Food> getFoodByPortions(int portion){
		String sql="SELECT food.food_code,food.display_name, COUNT(DISTINCT portion_id) AS n " + 
				"FROM food, `portion` " + 
				"WHERE food.food_code=portion.food_code " + 
				"GROUP BY food.food_code " + 
				"HAVING n = ? "
				+ "ORDER BY food.display_name ASC " ; 
		
		List<Food> result= new ArrayList<>(); 
		
		try {
			Connection conn = DBConnect.getConnection() ;
            PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1,  portion);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Food food= new Food(res.getInt("food_code"), res.getString("display_name")); 
				result.add(food); 			}
			
			conn.close();
			return result ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
		}

	/**
	 * Collegamento fra i vertici {@code Food}
	 * @param f1
	 * @param f2
	 * @return
	 */
	public Double calorieCongiunte(Food f1, Food f2) {
		
		String sql="SELECT f1.food_code,f2.food_code,AVG(condiment_calories) as cal " + 
				"FROM food_condiment AS f1, food_condiment AS f2, condiment " + 
				"WHERE f1.condiment_code=f2.condiment_code AND f1.id!=f2.id " + 
				"AND f2.condiment_code=condiment.condiment_code "
				+ "AND f1.food_code=? AND f2.food_code=? " + 
				"group BY f1.food_code, f2.food_code " ; 
		
		Double peso=null; 
		try {
			Connection conn = DBConnect.getConnection() ;
            PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, f1.getFood_code());
			st.setInt(2,  f2.getFood_code());
			ResultSet res = st.executeQuery() ;
			
			//mi posiziono sulla prima riga del risultato : se vero -> c'e' risultato
			if(res.first()) {
				peso= res.getDouble("cal"); 
			}
			
			conn.close();
			return peso ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	
		/* In alternativa per ottenere le coppie 
		 SELECT f1.food_code,f2.food_code,AVG(condiment_calories)
FROM food_condiment AS f1, food_condiment AS f2, condiment
WHERE f1.condiment_code=f2.condiment_code AND f1.id!=f2.id
AND f2.condiment_code=condiment.condiment_code 
group BY f1.food_code, f2.food_code
		 */
	}
	
	
	
}
