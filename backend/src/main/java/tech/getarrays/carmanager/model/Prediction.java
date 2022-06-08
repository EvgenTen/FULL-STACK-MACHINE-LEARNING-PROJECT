package tech.getarrays.carmanager.model;

public class Prediction {

	private String time;
	private int price;
	
	
	public Prediction(String time, int price) {
		super();
		this.time = time;
		this.price = price;
	}
	
	
	public Prediction() {
		super();
	}


	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	

}
