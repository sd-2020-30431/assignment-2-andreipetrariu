package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="groceries")
public class ShopItem {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_groceries")
	private int id;
	
	@Column(name="item_name")
	private String name;
	
	@Column(name="calories")
	private int calories;
	
	@Column(name="days")
	private int days;
	
	public ShopItem() {	}
	public ShopItem(int id, String name, int calories, int days) {
		super();
		this.id = id;
		this.name = name;
		this.calories = calories;
		this.days = days;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getCalories() {
		return calories;
	}
	public int getDays() {
		return days;
	}
}
