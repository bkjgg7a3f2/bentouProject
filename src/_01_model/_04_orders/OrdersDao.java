package _01_model._04_orders;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import _01_model._04_orders.Interface.IOrders;

@Repository
public class OrdersDao implements IOrders {
	private SessionFactory sessionFactory;

	@Autowired
	public OrdersDao(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Orders add(Orders od) {
		Session session = sessionFactory.getCurrentSession();
		session.save(od);
		return od;
	}

	@Override
	public Orders update(Orders od) {
		return null;
	}

	@Override
	public void delete(int order_id) {
		Session session = sessionFactory.getCurrentSession();
		Orders ods = session.get(Orders.class, order_id);
		if (ods != null) {
			session.delete(ods);
		}
	}

	@Override
	public Orders selectOne(String order_id) {
		Session session = sessionFactory.getCurrentSession();
		Orders ods = session.get(Orders.class, order_id);
		return ods;
	}

	// "消費者"看到的訂單紀錄
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Orders> selectAll_cstmr_view(int order_cstmr_id) {
		Session session = sessionFactory.getCurrentSession();

		String hql = "From Orders where order_cstmr_id = :order_cstmr_id order by order_date desc, order_number asc";
		Query query = session.createQuery(hql);
		query.setParameter("order_cstmr_id", order_cstmr_id);

		ArrayList<Orders> x = (ArrayList<Orders>) query.list();
		return x;
	}

	// "廠商"看到的訂單紀錄
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Orders> selectAll_supply_view(int order_supply_id) {
		Session session = sessionFactory.getCurrentSession();

		String hql = "From Orders where order_supply_id = :order_supply_id order by expected_arrivetime desc";
		Query query = session.createQuery(hql);
		query.setParameter("order_supply_id", order_supply_id);

		ArrayList<Orders> y = (ArrayList<Orders>) query.list();

		return y;
	}
}
