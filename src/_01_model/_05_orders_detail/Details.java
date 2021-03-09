package _01_model._05_orders_detail;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "orders_details")
@Component
public class Details {
	private int orders_details_id;// 自建
	private String order_id; // 即Orders的 order_id

	private int order_supply_id; // 從Orders對應
	private String commodity_name;// 從購物車取出

	private int commodity_price;// 從資料庫取出並計算
	private int order_quantity;// 從購物車取出

	private String order_confirm;// 廠商決定 預設null

	public Details() {
	}

	public Details(String order_id) {
		this.order_id = order_id;
	}

	public Details(int orders_details_id, String order_confirm) {
		this.orders_details_id = orders_details_id;
		this.order_confirm = order_confirm;
	}

	@Id
	@Column(name = "orders_details_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getOrders_details_id() {
		return orders_details_id;
	}

	public void setOrders_details_id(int orders_details_id) {
		this.orders_details_id = orders_details_id;
	}

	@Column(name = "order_id", nullable = false)
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	@Column(name = "order_supply_id", nullable = false)
	public int getOrder_supply_id() {
		return order_supply_id;
	}

	public void setOrder_supply_id(int order_supply_id) {
		this.order_supply_id = order_supply_id;
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

	@Column(name = "order_quantity", nullable = false)
	public int getOrder_quantity() {
		return order_quantity;
	}

	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}

	@Column(name = "order_confirm", nullable = false)
	public String getOrder_confirm() {
		return order_confirm;
	}

	public void setOrder_confirm(String order_confirm) {
		this.order_confirm = order_confirm;
	}

//	@ManyToOne(fetch = FetchType.EAGER)
//	public Orders getOrders() {
//		return orders;
//	}
//
//	public void setOrders(Orders orders) {
//		this.orders = orders;
//	}

}
