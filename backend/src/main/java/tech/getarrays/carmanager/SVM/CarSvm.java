package tech.getarrays.carmanager.SVM;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import tech.getarrays.carmanager.model.Car;
import java.util.stream.IntStream;
import org.apache.commons.math3.linear.MatrixUtils;



@SuppressWarnings("restriction")
public class CarSvm extends Application {
    public static double [][][] TRAINING_DATA;
    double[][][] lastArray ;
    double[][][] lastArrayAns ;
    	    
	Car car = new Car();
	public static Car carLocal = new Car();
	double sumPrice = 0;
	double sumDistance = 0;
	double avgPrice = 0;
	double avgDistance = 0;
	
	int size = 20;
    
    //the algorithm SVM
	public double[][][] svmManager (List<Car> cars, Car carStart) {
		carLocal = carStart;
		System.out.println("carLocal "+ carLocal );
		
		if (cars.size()==0) {
			System.out.println("No training data");
	        }
		else
		   lastArray = new double[cars.size()][2][2];
		   lastArrayAns = new double[cars.size()][2][2];
		   TRAINING_DATA = new double [cars.size()][2][2]; 
		
		for (int i = 0; i < cars.size(); i++) {
			this.car = cars.get(i);
			sumPrice =  Double.sum(this.car.getPrice(), sumPrice);
			sumDistance =  Double.sum(this.car.getDistance(), sumDistance);
		}
		avgPrice = sumPrice/cars.size();
		avgDistance = sumDistance/cars.size();
		
		for (int i = 0; i < cars.size(); i++) {
			
			Car randomElement = cars.get(i);
			 
		        if ((randomElement.getPrice() <= avgPrice && randomElement.getDistance() <= avgDistance) || (randomElement.getPrice() <= avgPrice+3000 && randomElement.getDistance() <= avgDistance))	{
		    	 TRAINING_DATA [i][0][0]= randomElement.getDistance()/10000.0;
		    	 TRAINING_DATA [i][0][1]= randomElement.getPrice()/10000.0;
		    	 TRAINING_DATA [i][1][0]= -1;
		        }
		     
	      else if(randomElement.getPrice() <= avgPrice &&  randomElement.getDistance() <= 50000.0 )	{
		    	 TRAINING_DATA [i][0][0]= randomElement.getDistance()/10000.0;
		   	     TRAINING_DATA [i][0][1]= randomElement.getPrice()/10000.0;
		    	 TRAINING_DATA [i][1][0]= -1;
		     }
		    
		     else {
		    	 TRAINING_DATA [i][0][0]= randomElement.getDistance()/10000.0;
		    	 TRAINING_DATA [i][0][1]= randomElement.getPrice()/10000.0;
		    	 TRAINING_DATA [i][1][0]= +1;
		    	 
		     }}
		  //   for (int p = 0; p < cars.size(); p++)
		   //         for (int j = 0; j < 2; j++)
		     //           for (int z = 0; z <2 && (j!=1 || z!=1); z++)
		       //             System.out.println("arr[" + p
		      //                                 + "]["
		        //                               + j + "]["
	            //                           + z + "] = "
		          //                            + TRAINING_DATA[p][j][z]);
		
		
		

	    startSVM();
	    this.lastArrayAns= this.lastArray;
	    lastArray = new double[cars.size()][2][2];
	    return lastArrayAns;
	
		
	}


	
	double [][] xArray;
	static double ZERO = 0.000000009;
	static SupportVectorMachines svm = null;
	public static String classifyVar = "";
    public String startSVM() {
    	xArray = new double[TRAINING_DATA.length][2];
    	double [][] yArray = new double[TRAINING_DATA.length][1];
    	for (int i = 0; i < TRAINING_DATA.length; i++) {
    		xArray[i][0] = TRAINING_DATA [i][0][0];
    		xArray[i][1] = TRAINING_DATA [i][0][1];
    		yArray[i][0] = TRAINING_DATA [i][1][0];
		}
    	svm = new SupportVectorMachines(MatrixUtils.createRealMatrix(xArray),MatrixUtils.createRealMatrix(yArray));
    	displayInfoTables(xArray, yArray);
    	
    return classifyVar;
    	
    }
    
    public String displayInfoTables(double [][] xArray, double [][] yArray) {
    	try {
    	System.out.println("Support Vector     |   label | alpha");
    	IntStream.range(0, 50).forEach(i-> System.out.print("-"));System.out.println();
    	int j = 0;
    	for (int i = 0; i < xArray.length; i++) {
			if(svm.getAlpha().getData()[i][0] > ZERO && svm.getAlpha().getData()[i][0]!= SupportVectorMachines.C) {
				StringBuffer ySB = new StringBuffer(String.valueOf(yArray[i][0]));
				ySB.setLength(5);
				System.out.println(Arrays.toString(xArray[i]) +"|"+ ySB+ "|" +
				     new String(String.format("%.10f", svm.getAlpha().getData()[i][0])));
				
				
				 lastArray[j][0][0] = xArray[i][0];
				 lastArray[j][0][1] = xArray[i][1];
				 lastArray[j][1][0] = Double.valueOf(ySB.toString());
				 j+=1;
				
				
			}
			
			
			
		}
    	
    	System.out.println("\n              wT             |   b   " );
    	IntStream.range(0, 50).forEach(i -> System.out.print("-"));
    	System.out.println();
    	System.out.println("<" + (new String(String.format("%.9f", svm.getW().getData()[0][0])) + ","
    			+ new String(String.format("%.9f",  svm.getW().getData()[1][0]))) +">     | "+ svm.getB());
    	handleCommandLine();} catch (Exception e1) { e1.printStackTrace();};
		return classifyVar;
    	
    }
  
    public String handleCommandLine() throws IOException {
    	
    			try {
    				
    				classifyVar = svm.classify(MatrixUtils.createRealMatrix(new double [][] {{ Double.valueOf(carLocal.getDistance()/10000.0),Double.valueOf(carLocal.getPrice()/10000.0)}}));
    				System.out.println("classify " + classifyVar);
    			  
    			
    			System.out.println(classifyVar);
    			
    			
    			}
    			catch(Exception e) {
    				
    				System.out.println("invalid input");
    	
    	}
				return classifyVar;
    	
    	
    	
    }

	@Override
	public void start(Stage arg0) throws Exception {
		
	}
    }
