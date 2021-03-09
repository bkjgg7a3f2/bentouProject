package _01_model._02_supply;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import _01_model._02_supply.Interface.ISupplyDAO;

@Repository
public class SupplyDAO implements ISupplyDAO {
	private SessionFactory sessionFactory;

	@Autowired
	public SupplyDAO(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Supply insert(Supply sup) {
		Session session = sessionFactory.getCurrentSession();
		session.save(sup);
		return sup;
	}

	@Override
	public Supply update_data(Supply s) {
		Session session = sessionFactory.getCurrentSession();
		Supply sup = session.get(Supply.class, s.getSupply_id());

		sup.setSupply_name(s.getSupply_name());
		sup.setSupply_ph(s.getSupply_ph());
		sup.setSupply_address(s.getSupply_address());
		sup.setSupply_contact(s.getSupply_contact());
		sup.setSupply_contactphnum(s.getSupply_contactphnum());
		sup.setSupply_email(s.getSupply_email());
		sup.setSupply_conumber(s.getSupply_conumber());
		sup.setSupply_bankaccount(s.getSupply_bankaccount());
		sup.setSupply_invoice(s.getSupply_invoice());
		sup.setLimit(s.getLimit());

		session.update(sup);

		return sup;
	}

	public Supply update_image(Supply s) {
		Session session = sessionFactory.getCurrentSession();
		Supply sup = session.get(Supply.class, s.getSupply_id());
		sup.setSupply_image(s.getSupply_image());

		session.update(sup);

		return sup;
	}

	@Override
	public Supply update_pwd(Supply s) {
		Session session = sessionFactory.getCurrentSession();
		Supply sup = session.get(Supply.class, s.getSupply_id());
		sup.setSupply_pwd(s.getSupply_pwd());
		session.update(sup);

		return sup;
	}
	
	public Supply update_vip(Supply s) {
		Session session = sessionFactory.getCurrentSession();
		Supply sup = session.get(Supply.class, s.getSupply_id());
		sup.setSupply_vip(s.getSupply_vip());
		session.update(sup);

		return sup;
	}

	@Override
	public Supply selectOne(int supply_id) {
		Session session = sessionFactory.getCurrentSession();
		Supply supply = session.get(Supply.class, supply_id);
		return supply;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList<Supply> selectall() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Supply order by supply_id";
		Query query = session.createQuery(hql);
		return (ArrayList<Supply>) query.list();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Supply selectOnebyacc_pwd(Supply s) {
		Session session = sessionFactory.getCurrentSession();
		String hqlStr = "From Supply Where supply_acc=:user and supply_pwd=:pwd";
		Query query = session.createQuery(hqlStr);
		query.setParameter("user", s.getSupply_acc());
		query.setParameter("pwd", s.getSupply_pwd());
		Supply sup = (Supply) query.uniqueResult();

		return sup;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Supply selectOnebyacc(String useracc) {
		Session session = sessionFactory.getCurrentSession();
		String hqlStr = "From Supply Where supply_acc=:user";
		Query query = session.createQuery(hqlStr);
		query.setParameter("user", useracc);
		Supply sup = (Supply) query.uniqueResult();

		return sup;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int select_newest_id() {
		Session session = sessionFactory.getCurrentSession();
		String hqlStr = "From Supply order by supply_id desc";
		Query query = session.createQuery(hqlStr);
		ArrayList<Supply> s = (ArrayList<Supply>) query.list();

		return s.get(0).getSupply_id();
	}

	// 廠商關鍵字查詢(回傳其id)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<Integer> search_Supply(String txt) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Supply where supply_name like: supply_name or supply_address like: supply_address order by supply_id asc";
		Query query = session.createQuery(hql);
		query.setParameter("supply_name", "%" + txt + "%");
		query.setParameter("supply_address", "%" + txt + "%");
		ArrayList<Supply> s = (ArrayList<Supply>) query.list();
		ArrayList<Integer> id_list = new ArrayList<Integer>();
		for (int i = 0; i < s.size(); i++) {
			Supply sup = s.get(i);
			id_list.add(sup.getSupply_id());
		}

		return id_list;
	}

}
