package _01_model._03_sales;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "sales")
@Component
public class Sales {
	private int commodity_id;
	
	private int supply_id;
	private String commodity_name;
	private int commodity_price;
	private int commodity_discount_type;
	private int commodity_discount_number;
	private String Vegan;
	private Date discount_timeini;
	private Date discount_timefini;

	private String commodity_image;
	private String commodity_onsale;

	private Date update_date;

	public Sales() {
	}

	public Sales(int supply_id) {
		this.supply_id = supply_id;
	}

	@Id
	@Column(name = "commodity_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getCommodity_id() {
		return commodity_id;
	}

	public void setCommodity_id(int commodity_id) {
		this.commodity_id = commodity_id;
	}

	@Column(name = "supply_id", nullable = false)
	public int getSupply_id() {
		return supply_id;
	}

	public void setSupply_id(int supply_id) {
		this.supply_id = supply_id;
	}

	@Column(name = "commodity_name", nullable = false, columnDefinition = "nvarchar(50)")
	public String getCommodity_name() {
		return commodity_name;
	}

	public void setCommodity_name(String commodity_name) {
		this.commodity_name = commodity_name;
	}

	@Column(name = "commodity_price", nullable = false)
	public int getCommodity_price() {
		return commodity_price;
	}

	public void setCommodity_price(int commodity_price) {
		this.commodity_price = commodity_price;
	}

	@Column(name = "commodity_discount_type", nullable = false)
	public int getCommodity_discount_type() {
		return commodity_discount_type;
	}

	public void setCommodity_discount_type(int commodity_discount_type) {
		this.commodity_discount_type = commodity_discount_type;
	}

	@Column(name = "commodity_discount_number")
	public int getCommodity_discount_number() {
		return commodity_discount_number;
	}

	public void setCommodity_discount_number(int commodity_discount_number) {
		this.commodity_discount_number = commodity_discount_number;
	}

	@Column(name = "Vegan", nullable = false, length = 1)
	public String getVegan() {
		return Vegan;
	}

	public void setVegan(String vegan) {
		Vegan = vegan;
	}

	@Column(name = "discount_timeini")
	public Date getDiscount_timeini() {
		return discount_timeini;
	}

	public void setDiscount_timeini(Date discount_timeini) {
		this.discount_timeini = discount_timeini;
	}

	@Column(name = "discount_timefini")
	public Date getDiscount_timefini() {
		return discount_timefini;
	}

	public void setDiscount_timefini(Date discount_timefini) {
		this.discount_timefini = discount_timefini;
	}

	@Column(name = "commodity_image", columnDefinition = "nvarchar(100)")
	public String getCommodity_image() {
		return commodity_image;
	}

	public void setCommodity_image(String commodity_image) {
		this.commodity_image = commodity_image;
	}

	@Column(name = "commodity_onsale", nullable = false, length = 1)
	public String getCommodity_onsale() {
		return commodity_onsale;
	}

	public void setCommodity_onsale(String commodity_onsale) {
		this.commodity_onsale = commodity_onsale;
	}

	@Column(name = "update_date")
	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

}
