package _01_model._01_customer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import _01_model._01_customer.Interface.ICustomerService;

@Service
public class CustomerService implements ICustomerService {
	private CustomerDao cDao;

	@Autowired
	public CustomerService(CustomerDao cDao) {
		this.cDao = cDao;
	}

	@Override
	public Customer insert(Customer c) {
		return cDao.insert(c);
	}

	@Override
	public Customer update_data(Customer c) {
		return cDao.update_data(c);
	}

	public Customer update_image(Customer c) {
		return cDao.update_image(c);
	}

	@Override
	public Customer update_pwd(Customer c) {
		return cDao.update_pwd(c);
	}
	
	public Customer update_vip(Customer c) {
		return cDao.update_vip(c);
	}

	@Override
	public Customer selectOne(int cstmr_id) {
		return cDao.selectOne(cstmr_id);
	}
	
	@Override
	public ArrayList<Customer> selectAll() {
		return cDao.selectAll();
	}

	@Override
	public Customer selectOnebyacc(String useracc) {
		return cDao.selectOnebyacc(useracc);
	}

	@Override
	public Customer selectOnebyacc_pwd(Customer c) {
		return cDao.selectOnebyacc_pwd(c);
	}

	@Override
	public int select_newest_id() {
		return cDao.select_newest_id();
	}

	@Override
	public Customer update_shoppinglist(Customer c) {
		return cDao.update_shoppinglist(c);
	}

	@Override
	public JSONArray view_shoppinglist(Customer c) throws JSONException, ParseException {
		return cDao.view_shoppinglist(c);
	}

	@Override
	public Map<String, String> form_cstmr_data(Customer c) {
		return cDao.form_cstmr_data(c);
	}

}
