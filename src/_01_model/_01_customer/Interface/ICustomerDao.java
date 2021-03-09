package _01_model._01_customer.Interface;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import _01_model._01_customer.Customer;

public interface ICustomerDao {
	public Customer insert(Customer c);

	public Customer update_data(Customer c);

	public Customer update_pwd(Customer c);

	public Customer selectOne(int cstmr_id);
	
	public ArrayList<Customer> selectAll();

	public Customer selectOnebyacc(String useracc);

	public Customer selectOnebyacc_pwd(Customer c);

	public int select_newest_id();

	public Customer update_shoppinglist(Customer c);

	public JSONArray view_shoppinglist(Customer c) throws JSONException, ParseException;

	public Map<String, String> form_cstmr_data(Customer c);
}
