package _01_model._01_customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "member_customer")
@Component
public class Customer {
	private int cstmr_id;

	private String cstmr_acc;
	private String cstmr_pwd;

	private String cstmr_name;
	private String cstmr_ph;
	private String cstmr_address;

	private String cstmr_contact;
	private String cstmr_contactphnum;
	private String cstmr_email;

	private String cstmr_conumber;

	private String cstmr_bankaccount;
	private String cstmr_invoice;
	private String cstmr_vip;

	private String cstmr_image;
	private String shopping_list;

	public Customer() {
	}

	public Customer(int cstmr_id) {
		this.cstmr_id = cstmr_id;
	}

	public Customer(String shopping_list) {
		this.shopping_list = shopping_list;
	}

	public Customer(int cstmr_id, String shopping_list) {
		this.cstmr_id = cstmr_id;
		this.shopping_list = shopping_list;
	}

	public Customer(String cstmr_acc, String cstmr_pwd) {

		this.cstmr_acc = cstmr_acc;
		this.cstmr_pwd = cstmr_pwd;
	}

	public Customer(String cstmr_acc, String cstmr_pwd, String cstmr_name, String cstmr_ph, String cstmr_address,
			String cstmr_contact, String cstmr_contactphnum, String cstmr_email, String cstmr_conumber,
			String cstmr_invoice) {
		this.cstmr_acc = cstmr_acc;
		this.cstmr_pwd = cstmr_pwd;
		this.cstmr_name = cstmr_name;
		this.cstmr_ph = cstmr_ph;
		this.cstmr_address = cstmr_address;
		this.cstmr_contact = cstmr_contact;
		this.cstmr_contactphnum = cstmr_contactphnum;
		this.cstmr_email = cstmr_email;
		this.cstmr_conumber = cstmr_conumber;
		this.cstmr_invoice = cstmr_invoice;
	}

	public Customer(int cstmr_id, String cstmr_name, String cstmr_ph, String cstmr_address, String cstmr_contact,
			String cstmr_contactphnum, String cstmr_email, String cstmr_conumber, String cstmr_bankaccount,String cstmr_invoice) {
		this.cstmr_id=cstmr_id;
		this.cstmr_name = cstmr_name;
		this.cstmr_ph = cstmr_ph;
		this.cstmr_address = cstmr_address;
		this.cstmr_contact = cstmr_contact;
		this.cstmr_contactphnum = cstmr_contactphnum;
		this.cstmr_email = cstmr_email;
		this.cstmr_conumber = cstmr_conumber;
		this.cstmr_bankaccount=cstmr_bankaccount;
		this.cstmr_invoice = cstmr_invoice;
	}

	@Id
	@Column(name = "cstmr_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getCstmr_id() {
		return cstmr_id;
	}

	public void setCstmr_id(int cstmr_id) {
		this.cstmr_id = cstmr_id;
	}

	@Column(name = "cstmr_acc", nullable = false)
	public String getCstmr_acc() {
		return cstmr_acc;
	}

	public void setCstmr_acc(String cstmr_acc) {
		this.cstmr_acc = cstmr_acc;
	}

	@Column(name = "cstmr_pwd", nullable = false)
	public String getCstmr_pwd() {
		return cstmr_pwd;
	}

	public void setCstmr_pwd(String cstmr_pwd) {
		this.cstmr_pwd = cstmr_pwd;
	}

	@Column(name = "cstmr_name", nullable = false, columnDefinition = "nvarchar(50)")
	public String getCstmr_name() {
		return cstmr_name;
	}

	public void setCstmr_name(String cstmr_name) {
		this.cstmr_name = cstmr_name;
	}

	@Column(name = "cstmr_ph", nullable = false, length = 12)
	public String getCstmr_ph() {
		return cstmr_ph;
	}

	public void setCstmr_ph(String cstmr_ph) {
		this.cstmr_ph = cstmr_ph;
	}

	@Column(name = "cstmr_conumber", nullable = false, length = 8)
	public String getCstmr_conumber() {
		return cstmr_conumber;
	}

	public void setCstmr_conumber(String cstmr_conumber) {
		this.cstmr_conumber = cstmr_conumber;
	}

	@Column(name = "cstmr_address", nullable = false, columnDefinition = "nvarchar(50)")
	public String getCstmr_address() {
		return cstmr_address;
	}

	public void setCstmr_address(String cstmr_address) {
		this.cstmr_address = cstmr_address;
	}

	@Column(name = "cstmr_contact", nullable = false, columnDefinition = "nvarchar(50)")
	public String getCstmr_contact() {
		return cstmr_contact;
	}

	public void setCstmr_contact(String cstmr_contact) {
		this.cstmr_contact = cstmr_contact;
	}

	@Column(name = "cstmr_bankaccount")
	public String getCstmr_bankaccount() {
		return cstmr_bankaccount;
	}

	public void setCstmr_bankaccount(String cstmr_bankaccount) {
		this.cstmr_bankaccount = cstmr_bankaccount;
	}

	@Column(name = "cstmr_contactphnum", nullable = false, length = 10)
	public String getCstmr_contactphnum() {
		return cstmr_contactphnum;
	}

	public void setCstmr_contactphnum(String cstmr_contactphnum) {
		this.cstmr_contactphnum = cstmr_contactphnum;
	}

	@Column(name = "cstmr_email", nullable = false)
	public String getCstmr_email() {
		return cstmr_email;
	}

	public void setCstmr_email(String cstmr_email) {
		this.cstmr_email = cstmr_email;
	}

	@Column(name = "cstmr_invoice", columnDefinition = "nvarchar(50)")
	public String getCstmr_invoice() {
		return cstmr_invoice;
	}

	public void setCstmr_invoice(String cstmr_invoice) {
		this.cstmr_invoice = cstmr_invoice;
	}

	@Column(name = "cstmr_vip", nullable = false, length = 16)
	public String getCstmr_vip() {
		return cstmr_vip;
	}

	public void setCstmr_vip(String cstmr_vip) {
		this.cstmr_vip = cstmr_vip;
	}

	@Column(name = "cstmr_image", length = 50)
	public String getCstmr_image() {
		return cstmr_image;
	}

	public void setCstmr_image(String cstmr_image) {
		this.cstmr_image = cstmr_image;
	}

	@Column(name = "shopping_list", columnDefinition = "varchar(max)")
	public String getShopping_list() {
		return shopping_list;
	}

	public void setShopping_list(String shopping_list) {
		this.shopping_list = shopping_list;
	}
}
