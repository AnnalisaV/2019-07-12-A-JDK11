package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.CoupleFoods;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	
	public void listAllFoods(Map<Integer, Food>idMap){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			//List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if (!idMap.containsKey(res.getInt("food_code"))) {
					Food f= new Food(res.getInt("food_code"), res.getString("display_name") );
					idMap.put(f.getFood_code(), f); 
				}
			}
			
			conn.close();
			//return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			//return null ;
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
	 * Lista di {@code Food} con quel valore di portion distinte
	 * @param portion
	 * @param idMap
	 * @return
	 */
	public List<Food> getFoodByPortion(int portion, Map<Integer, Food> idMap){
		String sql="SELECT food_code, COUNT(DISTINCT portion_id) AS n " + 
				"FROM `portion` " + 
				"GROUP BY food_code " + 
				"HAVING n =? "; 
		List<Food> lista= new ArrayList<>(); 
		
		try {
			Connection conn = DBConnect.getConnection() ;
            PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, portion);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Food f=idMap.get(res.getInt("food_code")); 
				lista.add(f); 
			}
			conn.close();
			return lista ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
	}
	
	public List<CoupleFoods> getCouples(Map<Integer, Food> idMap){
		
		String sql="SELECT f1.food_code, f2.food_code, AVG(condiment.condiment_calories) as cal " + 
				"FROM food_condiment f1, food_condiment f2, condiment " + 
				"WHERE f1.food_code>f2.food_code AND " + 
				"f1.condiment_code=f2.condiment_code AND " + 
				"f1.condiment_code=condiment.condiment_code " + 
				"GROUP BY f1.food_code, f2.food_code"; 
		
		List<CoupleFoods> lista= new ArrayList<>(); 
		
		try {
			Connection conn = DBConnect.getConnection() ;
            PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Food f1= idMap.get(res.getInt("f1.food_code"));
				Food f2= idMap.get(res.getInt("f2.food_code"));
				
				lista.add(new CoupleFoods(f1, f2, res.getDouble("cal"))); 
			}
			conn.close();
			return lista ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
	}
}
