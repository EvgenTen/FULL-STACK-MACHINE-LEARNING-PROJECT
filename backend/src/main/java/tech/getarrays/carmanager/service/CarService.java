package tech.getarrays.carmanager.service;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.getarrays.carmanager.SVM.CarSvm;
import tech.getarrays.carmanager.exception.UserNotFoundException;
import tech.getarrays.carmanager.model.Car;
import tech.getarrays.carmanager.model.Prediction;
import tech.getarrays.carmanager.repo.CarRepo;

@Service
public class CarService {
	private final CarRepo carRepo;
	CarSvm svm;
    @Autowired
    public CarService(CarRepo carRepo) {
        this.carRepo = carRepo;
    }
    public Car addCar(Car car){
    	car.setCarCode(UUID.randomUUID().toString());
        System.out.println(car);
        return carRepo.save(car);

    }
    public List<Car> findAllCars(){
        return carRepo.findAll();
    }
    public Car updateCar(Car car){
        return carRepo.save(car);
    }
    public Car findCarById(Long id)  {
        return carRepo.findById(id)
                .orElseThrow(()->new UserNotFoundException("Car by id "+ id + "was not found"));
    }
    public void deleteCar(Long id){
         carRepo.deleteById(id);
    }
 
 
    public List<Car> getAllQuery(Car car){
    	
    	List<Car> carList = carRepo.getCustomQuery(car.getManufacturer(), car.getModel(), car.getHand(),
        		car.getYear(), car.getMonth());
  //  	svm.svmManager(carList);
    //	System.out.println("carList = ");
		return carList;
    } 
    
 public Car getQuery(Car car){
	    svm = new CarSvm();
	    svm.svmManager(getAllQuery(car));
	    
		return null;
 }
}
