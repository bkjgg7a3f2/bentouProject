package _01_model._03_sales;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import _01_model._03_sales.Interface.ISalesDAO;
import _01_model._05_orders_detail.Details;

@Repository
public class SalesDAO implements ISalesDAO {

	private SessionFactory sessionFactory;

	@Autowired
	public SalesDAO(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Sales insert(Sales sal) {
		Session session = sessionFactory.getCurrentSession();
		session.save(sal);
		return sal;
	}

	@Override
	public Sales update(Sales sal) {
		Session session = sessionFactory.getCurrentSession();
		Sales sales = session.get(Sales.class, sal.getCommodity_id());
		sales.setSupply_id(sal.getSupply_id());
		sales.setCommodity_name(sal.getCommodity_name());
		sales.setCommodity_price(sal.getCommodity_price());
		sales.setCommodity_discount_type(sal.getCommodity_discount_type());
		sales.setCommodity_discount_number(sal.getCommodity_discount_number());
		sales.setVegan(sal.getVegan());
		sales.setDiscount_timeini(sal.getDiscount_timeini());
		sales.setDiscount_timefini(sal.getDiscount_timefini());
		sales.setCommodity_image(sal.getCommodity_image());
		sales.setCommodity_onsale(sal.getCommodity_onsale());
		sales.setUpdate_date(sal.getUpdate_date());
		
		session.update(sales);
		return sales;
	}

	@Override
	public void delete(int commodity_id) {
		Session session = sessionFactory.getCurrentSession();
		Sales sales = session.get(Sales.class, commodity_id);
		if (sales != null) {
			session.delete(sales);
		}
	}

	@Override
	public Sales selectOne(int commodity_id) {
		Session session = sessionFactory.getCurrentSession();
		Sales sales = session.get(Sales.class, commodity_id);
		return sales;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<Sales> selectAllbySupplyId(Sales sa) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Sales where supply_id=:supply_id order by commodity_id asc";
		Query query = session.createQuery(hql);
		query.setParameter("supply_id", sa.getSupply_id());
		return (ArrayList<Sales>) query.list();
	}

	// Map<key>: 同一廠商登錄之第幾個商品
	// Map<value>: 實際commodity_id
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Integer, Integer> map_sales(Sales sa) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Sales where supply_id=:supply_id order by commodity_id asc";
		Query query = session.createQuery(hql);
		query.setParameter("supply_id", sa.getSupply_id());
		ArrayList<Sales> s = (ArrayList<Sales>) query.list();

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < s.size(); i++) {
			Sales sales = s.get(i);
			int commodity_id = sales.getCommodity_id();
			map.put(i + 1, commodity_id);
		}

		return map;
	}

	// 從購物清單整理出來之 "廠商id清單"(僅出現1次)
	@Override
	public ArrayList<Integer> supply_id_appear_once_list(String id_list) {
		Session session = sessionFactory.getCurrentSession();

		JSONArray jsonArray = new JSONArray(id_list);

		ArrayList<Integer> list = new ArrayList<>();

		// 篩選重複商家id
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			int commodity_id = jsonObject.getInt("commodity_id");

			Sales obj = session.get(Sales.class, commodity_id);
			int supply_id = obj.getSupply_id();

			if (list.contains(supply_id) == false) {
				list.add(supply_id);
			}
		}

		return list;
	}

	// 設定每個訂單明細為Bean + 判斷實際價錢
	@Override
	public ArrayList<Details> insertbySupply_id(int order_supply_id, String id_list) {
		Session session = sessionFactory.getCurrentSession();

		JSONArray jsonArray = new JSONArray(id_list);

		ArrayList<Details> list = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			int commodity_id = jsonObject.getInt("commodity_id");

			Sales obj = session.get(Sales.class, commodity_id);
			int supply_id = obj.getSupply_id();

			// 篩選相同廠商id的商品
			if (supply_id == order_supply_id) {
				Details de = new Details();

				String commodity_name = obj.getCommodity_name();
				de.setCommodity_name(commodity_name);

				int order_quantity = jsonObject.getInt("quantity");
				de.setOrder_quantity(order_quantity);

				int commodity_price = obj.getCommodity_price();
				int commodity_discount_type = obj.getCommodity_discount_type();
				int commodity_discount_number = obj.getCommodity_discount_number();

				Date initime = obj.getDiscount_timeini();
				Date finitime = obj.getDiscount_timefini();
				Date now_time = new Date();

				int alter_price;

				switch (commodity_discount_type) {
				case 1:
					de.setCommodity_price(commodity_price);
					break;
				case 2:
					if (now_time.after(initime) && now_time.before(finitime)) {
						alter_price = commodity_price * commodity_discount_number / 100;
						de.setCommodity_price(alter_price);
					} else {
						de.setCommodity_price(commodity_price);
					}
					break;
				case 3:
					if (now_time.after(initime) && now_time.before(finitime)) {
						alter_price = commodity_price - commodity_discount_number;
						de.setCommodity_price(alter_price);
					} else {
						de.setCommodity_price(commodity_price);
					}
					break;
				}
				list.add(de);
			}
		}

		return list;
	}

	// 計算購物清單中選定廠商之總花費
	public int supply_limit_sum(int supply_id, String list) {
		Session session = sessionFactory.getCurrentSession();
		JSONArray jsonArray = new JSONArray(list);

		int sum = 0;
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			int commodity_id = jsonObject.getInt("commodity_id");

			Sales sales = session.get(Sales.class, commodity_id);
			int sales_supply_id = sales.getSupply_id();

			if (sales_supply_id == supply_id) {//如果我購物車清單裡有的商品是其他店家的商品，那我現在這個店家的小計就會是0
				int commodity_price = sales.getCommodity_price();
				int commodity_discount_type = sales.getCommodity_discount_type();
				int commodity_discount_number = sales.getCommodity_discount_number();
				Date initime = sales.getDiscount_timeini();
				Date finitime = sales.getDiscount_timefini();
				Date now_time = new Date();

				int alter_price = commodity_price;
				int quantity = jsonObject.getInt("quantity");
				switch (commodity_discount_type) {
				case 1:
					break;
				case 2:
					if (now_time.after(initime) && now_time.before(finitime)) {
						alter_price = commodity_price * commodity_discount_number / 100;
					}
					break;
				case 3:
					if (now_time.after(initime) && now_time.before(finitime)) {
						alter_price = commodity_price - commodity_discount_number;
					}
					break;
				}

				sum = sum + alter_price * quantity;
			}
		}
		return sum;
	}

	// 商品關鍵字查詢
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<Sales> search_Sales(String txt) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Sales where commodity_name like: commodity_name order by commodity_id asc";
		Query query = session.createQuery(hql);
		query.setParameter("commodity_name", "%" + txt + "%");
		return (ArrayList<Sales>) query.list();
	}

}
