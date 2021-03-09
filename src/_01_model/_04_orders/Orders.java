package _01_model._04_orders;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "orders")
@Component
public class Orders {
	private String order_id; // 下面4個總和

	private int order_cstmr_id; // 登入後抓attribute
	private int order_supply_id;// 從商品抓隸屬廠商
	private int order_number;// controller自建編號

	private Date order_date;// 系統時間
	private String cstmr_name;// 輸入設定or預設
	private String order_ph;// 輸入設定or預設
	private String order_address;// 輸入設定or預設
	private Date expected_arrivetime;// 輸入設定
	private String cstmr_conumber;// 輸入設定or預設
	private String cstmr_invoice;// 輸入設定or預設

	public Orders() {
	}

	public Orders(String order_id) {
		this.order_id = order_id;
	}

	public Orders(int order_cstmr_id, Date order_date, Date expected_arrivetime, String cstmr_name,
			String order_ph, String order_address, String cstmr_conumber, String cstmr_invoice) {
		this.order_cstmr_id = order_cstmr_id;
		this.order_date = order_date;
		this.expected_arrivetime = expected_arrivetime;
		this.cstmr_name = cstmr_name;
		this.order_ph = order_ph;
		this.order_address = order_address;
		this.cstmr_conumber = cstmr_conumber;
		this.cstmr_invoice = cstmr_invoice;
	}

	@Id
	@Column(name = "order_id")
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	@Column(name = "order_cstmr_id", nullable = false)
	public int getOrder_cstmr_id() {
		return order_cstmr_id;
	}

	public void setOrder_cstmr_id(int order_cstmr_id) {
		this.order_cstmr_id = order_cstmr_id;
	}

	@Column(name = "order_supply_id", nullable = false)
	public int getOrder_supply_id() {
		return order_supply_id;
	}

	public void setOrder_supply_id(int order_supply_id) {
		this.order_supply_id = order_supply_id;
	}

	@Column(name = "order_number", nullable = false)
	public int getOrder_number() {
		return order_number;
	}

	public void setOrder_number(int order_number) {
		this.order_number = order_number;
	}

	@Column(name = "order_date", nullable = false)
	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	@Column(name = "cstmr_name", nullable = false, columnDefinition = "nvarchar(50)")
	public String getCstmr_name() {
		return cstmr_name;
	}

	public void setCstmr_name(String cstmr_name) {
		this.cstmr_name = cstmr_name;
	}

	@Column(name = "order_ph", nullable = false, length = 10)
	public String getOrder_ph() {
		return order_ph;
	}

	public void setOrder_ph(String order_ph) {
		this.order_ph = order_ph;
	}

	@Column(name = "order_address", nullable = false, columnDefinition = "nvarchar(50)")
	public String getOrder_address() {
		return order_address;
	}

	public void setOrder_address(String order_address) {
		this.order_address = order_address;
	}

	@Column(name = "expected_arrivetime", nullable = false)
	public Date getExpected_arrivetime() {
		return expected_arrivetime;
	}

	public void setExpected_arrivetime(Date expected_arrivetime) {
		this.expected_arrivetime = expected_arrivetime;
	}

	@Column(name = "cstmr_conumber", nullable = false, length = 8)
	public String getCstmr_conumber() {
		return cstmr_conumber;
	}

	public void setCstmr_conumber(String cstmr_conumber) {
		this.cstmr_conumber = cstmr_conumber;
	}

	@Column(name = "cstmr_invoice", nullable = false, columnDefinition = "nvarchar(50)")
	public String getCstmr_invoice() {
		return cstmr_invoice;
	}

	public void setCstmr_invoice(String cstmr_invoice) {
		this.cstmr_invoice = cstmr_invoice;
	}

//	@OneToMany(fetch=FetchType.EAGER,mappedBy = "orders",cascade = CascadeType.ALL)
//	public Set<Details> getDetails() {
//		return details;
//	}
//
//	public void setDetails(Set<Details> details) {
//		this.details = details;
//	}
}
