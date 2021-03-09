package _01_model._05_orders_detail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import _01_model._05_orders_detail.Interface.IDetails;

@Repository
public class DetailsDao implements IDetails {
	private SessionFactory sessionFactory;

	@Autowired
	public DetailsDao(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Details add(Details de) {
		Session session = sessionFactory.getCurrentSession();
		session.save(de);
		return de;
	}

	@Override
	public Details update(Details de) {
		Session session = sessionFactory.getCurrentSession();
		Details des = session.get(Details.class, de.getOrders_details_id());

		des.setOrder_confirm(de.getOrder_confirm());
		session.update(des);

		return des;
	}

	@Override
	public void delete(int orders_details_id) {
		Session session = sessionFactory.getCurrentSession();
		Details des = session.get(Details.class, orders_details_id);
		if (des != null) {
			session.delete(des);
		}
	}

	@Override
	public Details selectOne(int orders_details_id) {
		Session session = sessionFactory.getCurrentSession();
		Details des = session.get(Details.class, orders_details_id);
		return des;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Details> selectAllbyOrder_id(Details de) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Details Where order_id =:order_id";
		Query query = session.createQuery(hql);
		query.setParameter("order_id", de.getOrder_id());

		return (ArrayList<Details>) query.list();
	}

	// Map<key>: 同一order_id之下第幾個明細，設定按鈕用
	// Map<value>: 實際orders_details_id
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Integer, Integer> map_details(String order_id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Details where order_id=:order_id order by orders_details_id asc";
		Query query = session.createQuery(hql);
		query.setParameter("order_id", order_id);
		ArrayList<Details> d = (ArrayList<Details>) query.list();

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < d.size(); i++) {
			Details details = d.get(i);
			int orders_details_id = details.getOrders_details_id();
			map.put(i + 1, orders_details_id);
		}

		return map;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Details> selectAllbySupply_id(int supply_id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Details Where order_supply_id =:order_supply_id";
		Query query = session.createQuery(hql);
		query.setParameter("order_supply_id", supply_id);

		return (ArrayList<Details>) query.list();
	}
}
