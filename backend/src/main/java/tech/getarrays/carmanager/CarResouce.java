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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.getarrays.carmanager.model.Car;
import tech.getarrays.carmanager.model.Prediction;
import tech.getarrays.carmanager.service.CarService;

@RestController
@RequestMapping("/car")
public class CarResouce {
	private final CarService carService;

	public CarResouce(CarService carService) {
		this.carService = carService;
	}

	// get all car
	@GetMapping("/all")
	public ResponseEntity<List<Car>> getAllEmployees() {
		List<Car> cars = carService.findAllCars();
		return new ResponseEntity<>(cars, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Car> getEmployeeById(@PathVariable("id") Long id) {
		Car car = carService.findCarById(id);
		return new ResponseEntity<>(car, HttpStatus.OK);
	}

	// create car rest api
	@PostMapping("/add")
	public ResponseEntity<Car> addCar(@RequestBody Car car) {
		Car newCar = carService.addCar(car);
		return new ResponseEntity<>(newCar, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Car> updateCar(@RequestBody Car car) {
		Car updateCar = carService.updateCar(car);
		return new ResponseEntity<>(updateCar, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCar(@PathVariable("id") Long id) {
		carService.deleteCar(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// @RequestMapping
// @RequestMapping("/request/{selectedMaker}/{selectedModel}/{selectedHand}/"
// 		+ "{selectedYear}/{selectedMonth}/{selectedDistance}")
// public void receiveArrayOfValues(@RequestParam String value1, @RequestParam String value2, @RequestParam String value3,
//		 @RequestParam String value4, @RequestParam String value5, @RequestParam String value6) 
// {
//    // Handle values here
// }

// @PostMapping("/query")
// public ResponseEntity<List<Car>> queryCar(@RequestBody Car car ) {
//	 List<Car> cars = carService.getAllQuery(car);
//	 System.out.println(cars);
//     return new ResponseEntity<>(cars, HttpStatus.OK);
//     
// }

	@PostMapping("/query")
	public ResponseEntity<Car> queryCar(@RequestBody Car car) {
		Car carq = carService.getQuery(car);
		//System.out.println(carq);
		
		return new ResponseEntity<>(carq, HttpStatus.OK);

	}


// @GetMapping("/ask/{make}//{model}//{year}")
// public ResponseEntity<List<Prediction>> getEmployeeById(@PathVariable("id") Long id){
//     List<Price> prices = carService.findAllCars();
//     return new ResponseEntity<>(prices, HttpStatus.OK);
// }	
}
