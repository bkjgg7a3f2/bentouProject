package _01_model._07_billboard;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import _01_model._07_billboard.Interface.IBillboardDao;

@Repository
public class BillboardDao implements IBillboardDao {
	private SessionFactory sessionFactory;

	@Autowired
	public BillboardDao(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Billboard add(Billboard bil) {
		Session session = sessionFactory.getCurrentSession();
		session.save(bil);
		return bil;
	}

	@Override
	public Billboard update(Billboard bil) {
		Session session = sessionFactory.getCurrentSession();
		Billboard bb = session.get(Billboard.class, bil.getBillboard_id());

		bb.setTitle(bil.getTitle());
		bb.setContent(bil.getContent());
		bb.setPicture1(bil.getPicture1());
		bb.setPicture2(bil.getPicture2());
		bb.setPicture3(bil.getPicture3());
		bb.setStatus(bil.getStatus());

		session.update(bb);

		return bb;
	}

	@Override
	public void delete(int billboard_id) {
		Session session = sessionFactory.getCurrentSession();
		Billboard bi = session.get(Billboard.class, billboard_id);
		if (bi != null) {
			session.delete(bi);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<Billboard> selectAll() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Billboard order by time desc";
		Query query = session.createQuery(hql);
		return (ArrayList<Billboard>) query.list();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<Billboard> Billshow(Billboard bil) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Billboard where status = :status order by time desc";

		Query query = session.createQuery(hql);

		query.setParameter("status", bil.getStatus());

		ArrayList<Billboard> y = (ArrayList<Billboard>) query.list();
		return y;
	}

	@Override
	public Billboard selectOne(int billboard_id) {
		Session session = sessionFactory.getCurrentSession();
		Billboard b = session.get(Billboard.class, billboard_id);
		return b;
	}

}
