package tech.getarrays.carmanager.repo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.getarrays.carmanager.model.Car;

//Long= id
public interface CarRepo extends JpaRepository<Car, Long>  {
	  //JpaRepository
	  @Query(value = "SELECT * FROM car  WHERE "
	  		+ " manufacturer = ?1 "
	  		+ " AND model = ?2"
	  		+ " AND hand = ?3"
	  		+ " AND year = ?4"
	  		+ " AND month = ?5"
	  	
	  	    , nativeQuery = true)
	  //make,model,year,month,hand
	    List<Car> getCustomQuery(
	    		String make, 
	    		String model, 
	    		int year, 
	    		int month, 
	    		int hand); 
	    		
	    		

       @Query(value = "SELECT * FROM car  WHERE "
  		+ " price = ?1 "
  		+ " AND distance = ?2"
  	    , nativeQuery = true)
       //price,distance
       List<Car> getCustomByParameters( 
    		int price,  
    		int distance);
       
       
       @Query(value = "SELECT * FROM car  WHERE "
   	  		+ " manufacturer = ?1 "
   	  		+ " AND model = ?2"
   	  		+ " AND hand = ?3"
   	  		+ " AND year = ?4"
   	  		+ " AND month = ?5"
   	  		+ " AND date = ?6"
   	  	    , nativeQuery = true)
       //make, model, year, month, hand, date
   	    List<Car> getCustomQueryPredict(
   	    		String make, 
   	    		String model, 
   	    		int year, 
   	    		int month, 
   	    		int hand,
   	    		LocalDate date);	
}
