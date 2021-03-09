package _01_model._02_supply;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "member_supply")
public class Supply {
	private int supply_id;
	private String supply_acc;
	private String supply_pwd;

	private String supply_name;
	private String supply_ph;
	private String supply_address;

	private String supply_contact;
	private String supply_contactphnum;
	private String supply_email;

	private String supply_conumber;

	private String supply_bankaccount;
	private String supply_invoice;
	private String supply_vip;

	private int limit;

	private String supply_image;

	public Supply() {
	}

	public Supply(int supply_id) {
		this.supply_id = supply_id;
	}

	public Supply(String supply_acc, String supply_pwd) {
		this.supply_acc = supply_acc;
		this.supply_pwd = supply_pwd;
	}

	public Supply(String supply_acc, String supply_pwd, String supply_name, String supply_ph, String supply_address,
			String supply_contact, String supply_contactphnum, String supply_email, String supply_conumber,
			String supply_invoice) {
		this.supply_acc = supply_acc;
		this.supply_pwd = supply_pwd;
		this.supply_name = supply_name;
		this.supply_ph = supply_ph;
		this.supply_address = supply_address;
		this.supply_contact = supply_contact;
		this.supply_contactphnum = supply_contactphnum;
		this.supply_email = supply_email;
		this.supply_conumber = supply_conumber;
		this.supply_invoice = supply_invoice;
	}

	public Supply(int supply_id, String supply_name, String supply_ph, String supply_address, String supply_contact, String supply_contactphnum,
			String supply_email, String supply_conumber, String supply_bankaccount, String supply_invoice , int limit) {
		this.supply_id = supply_id;
		this.supply_name = supply_name;
		this.supply_ph = supply_ph;
		this.supply_address = supply_address;
		this.supply_contact = supply_contact;
		this.supply_contactphnum = supply_contactphnum;
		this.supply_email = supply_email;
		this.supply_conumber = supply_conumber;
		this.supply_bankaccount = supply_bankaccount;
		this.supply_invoice = supply_invoice;
		this.limit = limit;
	}

	@Id
	@Column(name = "supply_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getSupply_id() {
		return supply_id;
	}

	public void setSupply_id(int supply_id) {
		this.supply_id = supply_id;
	}

	@Column(name = "supply_acc", nullable = false)
	public String getSupply_acc() {
		return supply_acc;
	}

	public void setSupply_acc(String supply_acc) {
		this.supply_acc = supply_acc;
	}

	@Column(name = "supply_pwd", nullable = false)
	public String getSupply_pwd() {
		return supply_pwd;
	}

	public void setSupply_pwd(String supply_pwd) {
		this.supply_pwd = supply_pwd;
	}

	@Column(name = "supply_name", nullable = false, columnDefinition = "nvarchar(50)")
	public String getSupply_name() {
		return supply_name;
	}

	public void setSupply_name(String supply_name) {
		this.supply_name = supply_name;
	}

	@Column(name = "supply_ph", nullable = false, length = 12)
	public String getSupply_ph() {
		return supply_ph;
	}

	public void setSupply_ph(String supply_ph) {
		this.supply_ph = supply_ph;
	}

	@Column(name = "supply_address", nullable = false, columnDefinition = "nvarchar(50)")
	public String getSupply_address() {
		return supply_address;
	}

	public void setSupply_address(String supply_address) {
		this.supply_address = supply_address;
	}

	@Column(name = "supply_contact", nullable = false, columnDefinition = "nvarchar(50)")
	public String getSupply_contact() {
		return supply_contact;
	}

	public void setSupply_contact(String supply_contact) {
		this.supply_contact = supply_contact;
	}

	@Column(name = "supply_contactphnum", nullable = false, length = 10)
	public String getSupply_contactphnum() {
		return supply_contactphnum;
	}

	public void setSupply_contactphnum(String supply_contactphnum) {
		this.supply_contactphnum = supply_contactphnum;
	}

	@Column(name = "supply_email", nullable = false)
	public String getSupply_email() {
		return supply_email;
	}

	public void setSupply_email(String supply_email) {
		this.supply_email = supply_email;
	}

	@Column(name = "supply_conumber", nullable = false, length = 8)
	public String getSupply_conumber() {
		return supply_conumber;
	}

	public void setSupply_conumber(String supply_conumber) {
		this.supply_conumber = supply_conumber;
	}

	@Column(name = "supply_bankaccount")
	public String getSupply_bankaccount() {
		return supply_bankaccount;
	}

	public void setSupply_bankaccount(String supply_bankaccount) {
		this.supply_bankaccount = supply_bankaccount;
	}

	@Column(name = "supply_invoice", nullable = false, columnDefinition = "nvarchar(50)")
	public String getSupply_invoice() {
		return supply_invoice;
	}

	public void setSupply_invoice(String supply_invoice) {
		this.supply_invoice = supply_invoice;
	}

	@Column(name = "limit")
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Column(name = "supply_vip", nullable = false, length = 16)
	public String getSupply_vip() {
		return supply_vip;
	}

	public void setSupply_vip(String supply_vip) {
		this.supply_vip = supply_vip;
	}
	
	@Column(name = "supply_image", length = 50)
	public String getSupply_image() {
		return supply_image;
	}

	public void setSupply_image(String supply_image) {
		this.supply_image = supply_image;
	}
}
