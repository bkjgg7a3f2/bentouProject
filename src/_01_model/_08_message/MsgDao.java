package _01_model._08_message;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Repository;

import _01_model._08_message.Interface.IMsgDao;

@Repository
public class MsgDao implements IMsgDao {
	public SessionFactory sessionfactory;
	public HttpRequest requestsession;

	@Autowired
	public MsgDao(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		this.sessionfactory = sessionFactory;
	}

	@Override
	public MsgBean add(MsgBean Msg) {
		Session session = sessionfactory.getCurrentSession();
		session.save(Msg);
		return Msg;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<MsgBean> showAllbySupply_id(int supply_id) {
		Session session = sessionfactory.getCurrentSession();
		String hql = "From MsgBean where supply_id= :Supply_ID order by msg_id asc";
		Query query = session.createQuery(hql);
		query.setParameter("Supply_ID", supply_id);
		return (ArrayList<MsgBean>) query.list();
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<MsgBean> showAllbyBoth_id(MsgBean mb) {
		Session session = sessionfactory.getCurrentSession();
		String hql = "From MsgBean where cstmr_id= :specifycstmr_id and supply_id= :specifysupply_id order by msg_id asc";
		Query query = session.createQuery(hql);
		query.setParameter("specifycstmr_id", mb.getCstmr_id());
		query.setParameter("specifysupply_id", mb.getSupply_id());
		return (ArrayList<MsgBean>) query.list();
	}

}
