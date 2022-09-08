package tech.getarrays.carmanager;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.getarrays.carmanager.model.Car;
import tech.getarrays.carmanager.service.CarService;

@RestController
@RequestMapping("/car")
public class CarResouce {
	private final CarService carService;

	public CarResouce(CarService carService) {
		this.carService = carService;
	}

	//get all cars
	@GetMapping("/all")
	public ResponseEntity<List<Car>> getAllEmployees() {
		List<Car> cars = carService.findAllCars();
		return new ResponseEntity<>(cars, HttpStatus.OK);
	}
    //find car by id
	@GetMapping("/find/{id}")
	public ResponseEntity<Car> getEmployeeById(@PathVariable("id") Long id) {
		Car car = carService.findCarById(id);
		return new ResponseEntity<>(car, HttpStatus.OK);
	}

	//create new car
	@PostMapping("/add")
	public ResponseEntity<Car> addCar(@RequestBody Car car) {
		Car newCar = carService.addCar(car);
		return new ResponseEntity<>(newCar, HttpStatus.CREATED);
	}
    //update car
	@PutMapping("/update")
	public ResponseEntity<Car> updateCar(@RequestBody Car car) {
		Car updateCar = carService.updateCar(car);
		return new ResponseEntity<>(updateCar, HttpStatus.OK);
	}
    //delete car by id
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCar(@PathVariable("id") Long id) {
		carService.deleteCar(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//general query
	@PostMapping("/query")
	public ResponseEntity<Object> queryCar(@RequestBody Car car) {
		Object[][] carq = carService.getQuery(car);
		
		return new ResponseEntity<>(carq, HttpStatus.OK);

	}
	//classifier 
	@PostMapping("/classify")
	public ResponseEntity<Object> queryClassify(@RequestBody Car car) {
		Object[] var = carService.getQueryClassify(car);
		return new ResponseEntity<>(var, HttpStatus.OK);

	}
	//relation between km /price
	@PostMapping("/relation")
	public ResponseEntity<?> relation(@RequestBody Car car) {
		double [][][] var = carService.getRelation(car);
		return new ResponseEntity<>(var, HttpStatus.OK);

	}
	//prediction
	@PostMapping("/predict")
	public ResponseEntity<?> predict(@RequestBody Car car) {
		List<Double> var = carService.getPredict(car);
		return new ResponseEntity<>(var, HttpStatus.OK);
	}
	
}
