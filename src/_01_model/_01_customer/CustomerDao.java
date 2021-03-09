package _01_model._01_customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import _01_model._01_customer.Interface.ICustomerDao;
import _01_model._02_supply.Supply;
import _01_model._03_sales.Sales;

@Repository
public class CustomerDao implements ICustomerDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Customer insert(Customer c) {
		Session session = sessionFactory.getCurrentSession();
		session.save(c);
		return c;
	}

	@Override
	public Customer update_data(Customer c) {
		Session session = sessionFactory.getCurrentSession();
		Customer cus = session.get(Customer.class, c.getCstmr_id());

		cus.setCstmr_name(c.getCstmr_name());
		cus.setCstmr_ph(c.getCstmr_ph());
		cus.setCstmr_address(c.getCstmr_address());
		cus.setCstmr_contact(c.getCstmr_contact());
		cus.setCstmr_contactphnum(c.getCstmr_contactphnum());
		cus.setCstmr_email(c.getCstmr_email());
		cus.setCstmr_conumber(c.getCstmr_conumber());
		cus.setCstmr_bankaccount(c.getCstmr_bankaccount());
		cus.setCstmr_invoice(c.getCstmr_invoice());

		session.update(cus);

		return cus;
	}

	public Customer update_image(Customer c) {
		Session session = sessionFactory.getCurrentSession();
		Customer cus = session.get(Customer.class, c.getCstmr_id());
		cus.setCstmr_image(c.getCstmr_image());
		session.update(cus);

		return cus;
	}

	@Override
	public Customer update_pwd(Customer c) {
		Session session = sessionFactory.getCurrentSession();
		Customer cus = session.get(Customer.class, c.getCstmr_id());
		cus.setCstmr_pwd(c.getCstmr_pwd());
		session.update(cus);

		return cus;
	}
	
	public Customer update_vip(Customer c) {
		Session session = sessionFactory.getCurrentSession();
		Customer cus = session.get(Customer.class, c.getCstmr_id());
		cus.setCstmr_vip(c.getCstmr_vip());
		session.update(cus);

		return cus;
	}

	@Override
	public Customer selectOne(int cstmr_id) {
		Session session = sessionFactory.getCurrentSession();
		Customer c = session.get(Customer.class, cstmr_id);
		return c;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList<Customer> selectAll() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Customer order by cstmr_id";
		Query query = session.createQuery(hql);
		return (ArrayList<Customer>) query.list();
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public Customer selectOnebyacc(String useracc) {
		Session session = sessionFactory.getCurrentSession();
		String hqlStr = "From Customer Where cstmr_acc=:user";
		Query query = session.createQuery(hqlStr);
		query.setParameter("user", useracc);
		Customer cus = (Customer) query.uniqueResult();

		return cus;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Customer selectOnebyacc_pwd(Customer c) {
		Session session = sessionFactory.getCurrentSession();
		String hqlStr = "From Customer Where cstmr_acc=:user and cstmr_pwd=:pwd";
		Query query = session.createQuery(hqlStr);
		query.setParameter("user", c.getCstmr_acc());
		query.setParameter("pwd", c.getCstmr_pwd());
		Customer cus = (Customer) query.uniqueResult();
		return cus;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int select_newest_id() {
		Session session = sessionFactory.getCurrentSession();
		String hqlStr = "From Customer order by cstmr_id desc";
		Query query = session.createQuery(hqlStr);
		ArrayList<Customer> c = (ArrayList<Customer>) query.list();

		return c.get(0).getCstmr_id();
	}

	// 更改購物清單
	@Override
	public Customer update_shoppinglist(Customer c) {
		Session session = sessionFactory.getCurrentSession();
		Customer cus = session.get(Customer.class, c.getCstmr_id());
		cus.setShopping_list(c.getShopping_list());

		session.update(cus);

		return cus;
	}

	// 顯示購物清單
	// key: 設定按鈕用
	// "commodity_name","supply_name","quantity","order_date": 顯示
	// "original_price","discount_type","discount_number": 計算實價(script)
	// "discount_timeini","discount_timefini": 判斷是否於折價期間(script)
	@Override
	public JSONArray view_shoppinglist(Customer c) throws JSONException, ParseException {
		Session session = sessionFactory.getCurrentSession();
		Customer cstmr_obj = session.get(Customer.class, c.getCstmr_id());
		String list = cstmr_obj.getShopping_list();

		JSONArray jsonArray = new JSONArray(list);
		JSONArray jsonArray_name = new JSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			int commodity_id = jsonObject.getInt("commodity_id");

			Sales sales_obj = session.get(Sales.class, commodity_id);
			String commodity_name = sales_obj.getCommodity_name();

			int supply_id = sales_obj.getSupply_id();

			Supply supply_obj = session.get(Supply.class, supply_id);
			String supply_name = supply_obj.getSupply_name();

			int key = jsonObject.getInt("key");
			int order_quantity = jsonObject.getInt("quantity");

			String strdate = jsonObject.getString("date");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date order_date = sdf.parse(strdate);

			int original_price = sales_obj.getCommodity_price();
			int discount_type = sales_obj.getCommodity_discount_type();
			int discount_number = sales_obj.getCommodity_discount_number();
			Date discount_timeini = sales_obj.getDiscount_timeini();
			Date discount_timefini = sales_obj.getDiscount_timefini();

			JSONObject jsonObject_name = new JSONObject();
			jsonObject_name.put("key", key);

			jsonObject_name.put("commodity_name", commodity_name);
			jsonObject_name.put("supply_name", supply_name);
			jsonObject_name.put("quantity", order_quantity);
			jsonObject_name.put("order_date", order_date);

			jsonObject_name.put("original_price", original_price);
			jsonObject_name.put("discount_type", discount_type);
			jsonObject_name.put("discount_number", discount_number);
			jsonObject_name.put("discount_timeini", discount_timeini);
			jsonObject_name.put("discount_timefini", discount_timefini);

			jsonArray_name.put(jsonObject_name);
		}

		return jsonArray_name;

	}

	// 填寫表單之消費者資料
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, String> form_cstmr_data(Customer c) {
		Session session = sessionFactory.getCurrentSession();
		Customer obj = session.get(Customer.class, c.getCstmr_id());

		Map<String, String> map = new HashMap();
		map.put("cstmr_contact", obj.getCstmr_contact());
		map.put("order_ph", obj.getCstmr_contactphnum());
		map.put("order_address", obj.getCstmr_address());
		map.put("cstmr_conumber", obj.getCstmr_conumber());
		map.put("cstmr_invoice", obj.getCstmr_invoice());

		return map;
	}

}