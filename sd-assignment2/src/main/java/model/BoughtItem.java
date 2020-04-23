package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bought_groceries")
public class BoughtItem{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_bought")
	private int id;
	
	@Column(name="item_name")
	private String name;
	
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="calories")
	private int calories;

	@Column(name="purchase_date")
	private Date purchaseDate;
	
	@Column(name="expiration_date")
	private Date expirationDate;
	
	@Column(name="consumption_date")
	private Date consumptionDate;
	
	@Column(name="fk_user")
	private int idUser;
	
	public BoughtItem() {}
	public BoughtItem(String name, int quantity, int calories, Date purchaseDate, Date expirationDate, Date consumptionDate) {
		this.name = name;
		this.quantity = quantity;
		this.calories = calories;
		this.purchaseDate = purchaseDate;
		this.expirationDate = expirationDate;
		this.consumptionDate = consumptionDate;
	}
	
	public String getName() {
		return name;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public Date getConsumptionDate() {
		return consumptionDate;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public int getCalories() {
		return calories;
	}

	public int getIdUser() {
		return idUser;
	}
	
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public void setConsumptionDate(Date date) {
		this.consumptionDate = date;
	}
	
	@Override
	public String toString() {
		return "ShopItem [id=" + id + ", name=" + name + ", quantity=" + quantity + ", calories=" + calories
				+ ", purchaseDate=" + purchaseDate + ", expirationDate=" + expirationDate + ", consumptionDate="
				+ consumptionDate + "]";
	}
}
