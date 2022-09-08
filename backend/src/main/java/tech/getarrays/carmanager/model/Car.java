package tech.getarrays.carmanager.model;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;


@Entity
public class Car implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable=false, updatable=false)
	private Long id;
	private String manufacturer;
	private String model;
	private int year;
	private int month;
	private int distance;
	private int hand;
	private String imageUrl;
	 @Column(nullable=false, updatable=false)
	private String carCode;
	private int price;
	 @Column(nullable=false, updatable=false)
	 @CreationTimestamp
	private LocalDate date;

	
	

	public Car() {
		super();
	}
	public Car(String manufacturer, String model, int year, int month, int distance, int hand, String imageUrl,
			int price) {
		super();
		this.manufacturer = manufacturer;
		this.model = model;
		this.year = year;
		this.month = month;
		this.distance = distance;
		this.hand = hand;
		this.imageUrl = imageUrl;
		this.price = price;
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getHand() {
		return hand;
	}
	public void setHand(int hand) {
		this.hand = hand;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Car [id=" + id + ", manufacturer=" + manufacturer + ", model=" + model + ", year=" + year + ", month="
				+ month + ", distance=" + distance + ", hand=" + hand + ", imageUrl=" + imageUrl + ", carCode="
				 + ", price=" + price + ", date=" + date  + "]";
	}

	

}
