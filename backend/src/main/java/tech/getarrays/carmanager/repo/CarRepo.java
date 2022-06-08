package tech.getarrays.carmanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tech.getarrays.carmanager.model.Car;

//Long= id
public interface CarRepo extends JpaRepository<Car, Long>  {
	  
	  @Query(value = "SELECT * FROM car  WHERE "
	  		+ " manufacturer = ?1 "
	  		+ " AND model = ?2"
	  		+ " AND hand = ?3"
	  		+ " AND year = ?4"
	  		+ " AND month = ?5"
	  		//+ " AND distance = ?6", nativeQuery = true)
	  	    , nativeQuery = true)
	    List<Car> getCustomQuery(
	    		String make, 
	    		String model, 
	    		int year, 
	    		int month, 
	    		int hand); 
	    		//int distance);
	    		
}
