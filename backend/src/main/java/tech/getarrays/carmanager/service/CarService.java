package tech.getarrays.carmanager.service;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.getarrays.carmanager.SVM.CarSvm;
import tech.getarrays.carmanager.exception.UserNotFoundException;
import tech.getarrays.carmanager.model.Car;
import tech.getarrays.carmanager.repo.CarRepo;


@Service
public class CarService {
	
	private final CarRepo carRepo;
	CarSvm svm;
	
    @Autowired
    public CarService(CarRepo carRepo) {
        this.carRepo = carRepo;
    }
    //add new car
    public Car addCar(Car car){
    	car.setCarCode(UUID.randomUUID().toString());
        System.out.println(car);
        return carRepo.save(car);

    }
    //find all cars
    public List<Car> findAllCars(){
        return carRepo.findAll();
    }
    //update existing car
    public Car updateCar(Car car){
        return carRepo.save(car);
    }
    //find car by id
    public Car findCarById(Long id)  {
        return carRepo.findById(id)
                .orElseThrow(()->new UserNotFoundException("Car by id "+ id + "was not found"));
    }
    //delete car
    public void deleteCar(Long id){
         carRepo.deleteById(id);
    }
 
    //get all query: call function: getAllQuery(Car car)
    public List<Car> getAllQuery(Car car){
    	
    	List<Car> carList = carRepo.getCustomQuery(car.getManufacturer(), car.getModel(), car.getHand(),
        		car.getYear(), car.getMonth());
  
		return carList;
    } 
    //get all query: call function: getAllQueryPredict(Car car)
    public List<Car> getAllQueryPredict(Car car){
    	
    	List<Car> carList = carRepo.getCustomQueryPredict(car.getManufacturer(), car.getModel(), car.getHand(),
        		car.getYear(), car.getMonth(), car.getDate());
  
		return carList;
    }
    //the algorithm SVM, getting reference vector points 
    public Object [][] getQuery(Car car){
	    svm = new CarSvm();
	    double [][][] ansArray = new double [5][2][2];
	    Object [][] finalArray = new Object [5][2];
	    ansArray = svm.svmManager(getAllQuery(car), car);
	    for (int i = 0; i < ansArray.length; i++) {
	    	ansArray[i][0][0]= ansArray[i][0][0]*10000;
	    	ansArray[i][0][1]= ansArray[i][0][1]*10000;
		}
	    
	    List<Car> carList = new ArrayList<>();
		for (int i = 0; i < ansArray.length; i++) 
	       carList.addAll(carRepo.getCustomByParameters((int)ansArray[i][0][1], (int)ansArray[i][0][0]));
	    
	    for (int i = 0; i < finalArray.length; i++) {	
	       finalArray[i][0] = carRepo.getCustomByParameters((int)ansArray[i][0][1], (int)ansArray[i][0][0]);
	       finalArray[i][1] = ansArray[i][1][0];
	    }	 
	    System.out.println("car_list"+ Arrays.deepToString(finalArray));
	    return finalArray;
 }
  @SuppressWarnings("static-access")
  //the algorithm SVM, price classifier
  public Object[] getQueryClassify(Car car) {
	CarSvm svmQueryClassify = new CarSvm();
    Object [] ansVar = new Object [2];
    ansVar[0] = svmQueryClassify.carLocal.getPrice();
    ansVar[1] = svmQueryClassify.classifyVar;
    System.out.println("ans array"+ ansVar[0]);
    System.out.println("ans array"+ ansVar[1]);
	return ansVar;
}

  @SuppressWarnings("static-access")
  // get relation between km /price
  public  double [][][] getRelation(Car car){
    CarSvm svmgetRelation = new CarSvm();
    svmgetRelation.svmManager(getAllQuery(car), car);
    System.out.println("length = "+svm.TRAINING_DATA.length);
   return  svm.TRAINING_DATA;
  }
  int year=0;
  LocalDate year_date;
  Double total = 0.0;
  int count=0;
  double priceStart = 0.0;
  double price0 = 0.0;
  double price1 = 0.0;
  double price2 = 0.0;
  double price3 = 0.0;
  double price4 = 0.0;
  double price5 = 0.0;
  LinkedList<Double> ansGetPredict = new LinkedList<Double>();
  LinkedList<Double> finalPredict = new LinkedList<Double>();
  
  /////////////////////////////the car price prediction//////////////////////////////////////////////////////////
  public LinkedList<Double> getPredict(Car car){
	
	//start point 
	car.setHand(0);
	year=car.getYear();
	car.setDate(LocalDate.of(year,01,01));
	priceStart = new Double(getAllQueryPredict(car).get(0).getPrice());
	System.out.println("priceStart="+priceStart);
	year = Year.now().getValue();
	// year 0
	year_date = LocalDate.of(year-5, 01, 01);
	car.setYear(year-5);
	car.setDate(year_date);
	price0 = new Double(getAllQueryPredict(car).get(0).getPrice());
	System.out.println("price0="+price0);
	//year 1
	year_date = LocalDate.of(year-4, 01, 01);
	car.setDate(year_date);
	car.setHand(1);
	svm = new CarSvm();
    double [][][] ansArray1 = new double [5][2][2];
    
    ansArray1 = svm.svmManager(getAllQueryPredict(car), car);
    for (int i = 0; i < ansArray1.length; i++) {
    	ansArray1[i][0][1]= ansArray1[i][0][1]*10000;
    	 total = total + ansArray1[i][0][1];
    	 if(ansArray1[i][0][1] != 0.0) {
    		 count += 1;
    	 }
    	ansArray1[i][0][1]= 0.0;
	}
    price1= total/count;
    System.out.println("price1="+price1);
    count=0;
    total=0.0;
    //year 2
    year_date = LocalDate.of(year-3, 01, 01);
	car.setDate(year_date);
	car.setHand(1);
	svm = new CarSvm(); 
    ansArray1 = svm.svmManager(getAllQueryPredict(car), car);
    for (int i = 0; i < ansArray1.length; i++) {
    	ansArray1[i][0][1]= ansArray1[i][0][1]*10000;
    	 total = total + ansArray1[i][0][1];
    	 if(ansArray1[i][0][1] != 0.0) {
    		 count += 1;
    	 }
    	 
    	ansArray1[i][0][1]= 0.0;
	}
    price2= total/count;
    System.out.println("price2="+price2);
    count=0;
    total=0.0;
    //year 3
    year_date = LocalDate.of(year-2, 01, 01);
	car.setDate(year_date);
	car.setHand(1);
	svm = new CarSvm(); 
    ansArray1 = svm.svmManager(getAllQueryPredict(car), car);
    for (int i = 0; i < ansArray1.length; i++) {
    	ansArray1[i][0][1]= ansArray1[i][0][1]*10000;
    	 total = total + ansArray1[i][0][1];
    	 if(ansArray1[i][0][1] != 0.0) {
    		 count += 1;
    	 }
    	 
    	ansArray1[i][0][1]= 0.0;
	}
    price3= total/count;
    System.out.println("price3="+price3);
    count=0;
    total=0.0;
  //year 4
    year_date = LocalDate.of(year-1, 01, 01);
	car.setDate(year_date);
	car.setHand(1);
	svm = new CarSvm(); 
    ansArray1 = svm.svmManager(getAllQueryPredict(car), car);
    for (int i = 0; i < ansArray1.length; i++) {
    	ansArray1[i][0][1]= ansArray1[i][0][1]*10000;
    	 total = total + ansArray1[i][0][1];
    	 if(ansArray1[i][0][1] != 0.0) {
    		 count += 1;
    	 }
    	 
    	ansArray1[i][0][1]= 0.0;
	}
    price4= total/count;
    System.out.println("price4="+price4);
    count=0;
    total=0.0;
  //year 5
    year_date = LocalDate.of(year, 01, 01);
	car.setDate(year_date);
	car.setHand(1);
	svm = new CarSvm(); 
    ansArray1 = svm.svmManager(getAllQueryPredict(car), car);
    for (int i = 0; i < ansArray1.length; i++) {
    	ansArray1[i][0][1]= ansArray1[i][0][1]*10000;
    	 total = total + ansArray1[i][0][1];
    	 if(ansArray1[i][0][1] != 0.0) {
    		 count += 1;
    	 }
    	 
    	ansArray1[i][0][1]= 0.0;
	}
    price5= total/count;
    System.out.println("price5="+price5);
    count=0;
    total=0.0;
    ansGetPredict = getCalculationPredict();
    finalPredict.clear();
    finalPredict.add(0,new Double(new DecimalFormat("##").format(priceStart*((100-ansGetPredict.getFirst())*0.01))));
    for (int i = 1; i < 5; i++) {
    finalPredict.add(i,new Double(new DecimalFormat("##").format(finalPredict.get(i-1)*((100-ansGetPredict.get(i))*0.01))));
    System.out.println("before="+finalPredict.get(i-1));
    System.out.println("after="+finalPredict.get(i));
    System.out.println("percentage="+ansGetPredict.get(i));}
    finalPredict.addAll(ansGetPredict);
    ansGetPredict.clear();
    System.out.println("finalPredict=price+percentage="+ finalPredict);
	return finalPredict;
    }
    
  double ansPrice1= 0;
  double ansPrice2= 0;
  double ansPrice3= 0;
  double ansPrice4= 0;
  double ansPrice5= 0;
  double difference=0;
  LinkedList<Double> ansPredict = new LinkedList<Double>();
  
  //the helper function for getPredict(Car car)
  private LinkedList<Double> getCalculationPredict() {
	  difference= price0-price1;
	  ansPrice1=percentage(price0,difference);
	  ansPredict.add(0,new Double(new DecimalFormat("##.##").format(ansPrice1)));
	  difference=0;
	  difference= price1-price2;
	  ansPrice2=percentage(price1,difference);
	  ansPredict.add(1,new Double(new DecimalFormat("##.##").format(ansPrice2)));
	  difference=0;
	  difference= price2-price3;
	  ansPrice3=percentage(price2,difference);
	  ansPredict.add(2,new Double(new DecimalFormat("##.##").format(ansPrice3)));
	  difference=0;
	  difference= price3-price4;
	  ansPrice4=percentage(price3,difference);
	  ansPredict.add(3,new Double(new DecimalFormat("##.##").format(ansPrice4)));
	  difference=0;
	  difference= price4-price5;
	  ansPrice5=percentage(price4,difference);
	  ansPredict.add(4,new Double(new DecimalFormat("##.##").format(ansPrice5)));
	  difference=0;
	  System.out.printf("ansPredict"+ansPredict);
	  return ansPredict;
}
  //the helper function for getPredict(Car car)
  private double percentage(double price, double difference ) {
    double var = (difference * 100)/price;
	return var;
}
  
}
