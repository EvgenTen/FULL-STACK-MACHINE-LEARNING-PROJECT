package tech.getarrays.carmanager.SVM;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.*;

import javafx.application.Application;
import javafx.stage.Stage;
import tech.getarrays.carmanager.model.Car;
import java.util.Random;
import java.util.stream.IntStream;

import org.apache.commons.math3.linear.MatrixUtils;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.LineChart;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


@SuppressWarnings("restriction")
public class CarSvm extends Application {
    public static double [][][] TRAINING_DATA = {{{0,0},{0}},
		    {{0,0},{0}},{{0,0},{0}},{{0,0},{0}},
			{{0,0},{0}},{{0,0},{0}},{{0,0},{0}},
			{{0,0},{0}},{{0,0},{0}},{{0,0},{0}},
			{{0,0},{0}},{{0,0},{0}},{{0,0},{0}},
			{{0,0},{0}},{{0,0},{0}},{{0,0},{0}},
			{{0,0},{0}},{{0,0},{0}},{{0,0},{0}},
			{{0,0},{0}}};
//	static final double [][][] TRAINING_DATA = {{{9.123456,3.123456},{+1}},
//			{{9.123456,5.123456},{+1}},{{5.123456,5.123456},{-1}},{{8.123456,6.654321},{+1}},
//			{{4.654321,4.123456},{-1}},{{2.123456,4.123456},{-1}},{{9.123456,7.123456},{+1}},
//			{{4.123456,4.654321},{-1}},{{8.654321,2.123456},{+1}},{{2.123456,2.123456},{-1}},
//			{{3.123456,3.123456},{-1}},{{8.654321,4.123456},{+1}},{{7.123456,6.123456},{+1}},
//			{{4.123456,7.123456},{-1}},{{6.923456,4.623456},{-1}},{{8.123456,5.123456},{+1}},
//			{{3.123456,4.123456},{-1}}
//	};
	Car car = new Car();
	double sumPrice = 0;
	double sumDistance = 0;
	double avgPrice = 0;
	double avgDistance = 0;
	public List<Car> svmManager (List<Car> cars) {
		
		for (int i = 0; i < cars.size(); i++) {
			this.car = cars.get(i);
			sumPrice =  Double.sum(this.car.getPrice(), sumPrice);
			sumDistance =  Double.sum(this.car.getDistance(), sumDistance);
		}
		avgPrice = sumPrice/cars.size();
		avgDistance = sumDistance/cars.size();
		System.out.println(cars.size());
		Random rand = new Random();
		for (int i = 0; i < 20; i++) {
			 //Car randomElement = cars.get(rand.nextInt(40 - 0) + 0);
			Car randomElement = cars.get(i);
			 
		        if ((randomElement.getPrice() <= avgPrice && randomElement.getDistance() <= avgDistance) || (randomElement.getPrice() <= avgPrice+0.3 && randomElement.getDistance() <= avgDistance))	{
		    	 TRAINING_DATA [i][0][0]= randomElement.getDistance()/10000.0;
		    	 TRAINING_DATA [i][0][1]= randomElement.getPrice()/10000.0;
		    	 TRAINING_DATA [i][1][0]= -1;
		        }
		     
		     else if(randomElement.getPrice() <= avgPrice &&  randomElement.getDistance() >= avgDistance)	{
		    	 TRAINING_DATA [i][0][0]= randomElement.getDistance()/10000.0;;
		    	 TRAINING_DATA [i][0][1]= randomElement.getPrice()/10000.0;;
		    	 TRAINING_DATA [i][1][0]= -1;
		     }
		    
		     else {
		    	 TRAINING_DATA [i][0][0]= randomElement.getDistance()/10000.0;;
		    	 TRAINING_DATA [i][0][1]= randomElement.getPrice()/10000.0;;
		    	 TRAINING_DATA [i][1][0]= +1;
		    	 
		     }}
		     for (int p = 0; p < 20; p++)
		            for (int j = 0; j < 2; j++)
		                for (int z = 0; z <1 ; z++)
		                    System.out.println("arr[" + p
		                                       + "]["
		                                       + j + "]["
	                                       + z + "] = "
		                                       + TRAINING_DATA[p][j][z]);
		
		
		
//	   
//		
//		for (Car car : cars) {
//			//System.out.println("svmManager " + car);
//		}
	    startSVM();
	   
		return null;
		
	}
//	private final CarService carService;
  //  carService.
	static double ZERO = 0.000000009;
	static SupportVectorMachines svm = null;
    public void startSVM() {
    	double [][] xArray = new double[TRAINING_DATA.length][2];
    	double [][] yArray = new double[TRAINING_DATA.length][1];
    	for (int i = 0; i < TRAINING_DATA.length; i++) {
    		xArray[i][0] = TRAINING_DATA [i][0][0];
    		xArray[i][1] = TRAINING_DATA [i][0][1];
    		yArray[i][0] = TRAINING_DATA [i][1][0];
		}
    	svm = new SupportVectorMachines(MatrixUtils.createRealMatrix(xArray),MatrixUtils.createRealMatrix(yArray));
    	displayInfoTables(xArray, yArray);
    	launch();
    }
    
    public void displayInfoTables(double [][] xArray, double [][] yArray) {
    	System.out.println("Support Vector     |   label | alpha");
    	IntStream.range(0, 50).forEach(i-> System.out.print("-"));System.out.println();
    	for (int i = 0; i < xArray.length; i++) {
			if(svm.getAlpha().getData()[i][0] > ZERO && svm.getAlpha().getData()[i][0]!= SupportVectorMachines.C) {
				
				StringBuffer ySB = new StringBuffer(String.valueOf(yArray[i][0]));
				ySB.setLength(5);
				System.out.println(Arrays.toString(xArray[i]) +"|"+ ySB+ "|" +
				     new String(String.format("%.10f", svm.getAlpha().getData()[i][0])));
			}
			
		}
    	System.out.println("\n              wT             |   b   " );
    	IntStream.range(0, 50).forEach(i -> System.out.print("-"));
    	System.out.println();
    	System.out.println("<" + (new String(String.format("%.9f", svm.getW().getData()[0][0])) + ","
    			+ new String(String.format("%.9f",  svm.getW().getData()[1][0]))) +">     | "+ svm.getB());
    	
    	
    }
    public void handleCommandLine() throws IOException {
    	BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
    	while(true) {
    		System.out.println("\n> to classify new car enter km and price 1 & 2 (or exit):");
    		String[] values = (bufferReader.readLine().split(" "));
    		if (values[0].equals("exit"))  { TRAINING_DATA= null;svm = null;System.exit(0);}
    		
    		else {
    			try {
    				System.out.println("classify " + svm.classify(MatrixUtils.createRealMatrix(new double [][] {{ Double.valueOf(values[0]),Double.valueOf(values[1])}})));}
    			catch(Exception e) {
    				System.out.println("invalid input");
    			}
    		}

    	}
    	
    	
    	
    }
	
	public void start(Stage stage) throws Exception {
		Platform.setImplicitExit(false);
		XYChart.Series<Number,Number> series01 = new XYChart.Series<Number,Number>();
		series01.setName("try to search another car");
		XYChart.Series<Number,Number> series02 = new XYChart.Series<Number,Number>();
		series02.setName("good car");
		IntStream.range(0, CarSvm.TRAINING_DATA.length).forEach(i ->{
			double x = CarSvm.TRAINING_DATA[i][0][0], y = CarSvm.TRAINING_DATA[i][0][1];
			if(CarSvm.TRAINING_DATA[i][1][0] == -1.0)
				series01.getData().add(new XYChart.Data<Number,Number>(x,y));
			else series02.getData().add(new XYChart.Data<Number,Number>(x,y));
		});
		NumberAxis xAxis = new NumberAxis(0, 10 , 1.0);
		xAxis.setLabel("km");
		NumberAxis yAxis = new NumberAxis(6, 15, 1.0);
		yAxis.setLabel("price");
		ScatterChart<Number,Number> scatterChart = new ScatterChart <Number,Number>(xAxis,yAxis);
		scatterChart.getData().add(series01);
		scatterChart.getData().add(series02);
		double m = -(svm.getW().getData()[0][0]/svm.getW().getData()[1][0]);
		double b = -(svm.getB()/svm.getW().getData()[1][0]);
		double score1X = 0.00 ,  score1Y = m*score1X + b, score2X = 10.00, score2Y = m*score2X + b;
		XYChart.Series<Number,Number> series03 = new XYChart.Series<Number,Number>();
		series03.getData().add(new XYChart.Data<Number,Number>(score1X,score1Y));
		series03.getData().add(new XYChart.Data<Number,Number>(score2X,score2Y));
		LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
		lineChart.getData().add(series03);
		lineChart.setOpacity(0.4);
		Pane pane = new Pane();
		pane.getChildren().addAll(scatterChart,lineChart);
		stage.setScene(new Scene(pane,550,420));
		stage.setTitle("Support Vector Machines");
		stage.show();
		stage.setOnHidden(e -> {try {handleCommandLine();} catch (Exception e1) { e1.printStackTrace();}});
		
		
		
	}
    
	

}
