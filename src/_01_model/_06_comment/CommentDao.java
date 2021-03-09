package _01_model._06_comment;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import _01_model._06_comment.Interface.ICommentDao;

@Repository
public class CommentDao implements ICommentDao {
	private SessionFactory sessionFactory;

	@Autowired
	public CommentDao(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Comment add(Comment co) {
		Session session = sessionFactory.getCurrentSession();
		session.save(co);
		return co;
	}

	@Override
	public Comment update(Comment co) {
		Session session = sessionFactory.getCurrentSession();
		Comment com = session.get(Comment.class, co.getComment_id());
		com.setSupply_evaluation(co.getSupply_evaluation());
		com.setReplyTime(co.getReplyTime());

		session.update(com);

		return com;

	}

	@Override
	public void delete(String order_id) {
		Session session = sessionFactory.getCurrentSession();
		Comment bs = session.get(Comment.class, order_id);
		if (bs != null) {
			session.delete(bs);
		}

	}

	@Override
	public Comment selectOne(String comment_id) {
		Session session = sessionFactory.getCurrentSession();
		Comment com = session.get(Comment.class, comment_id);
		return com;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList<Comment> selectAllbySupply_id(Comment co) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Comment where comment_supply_id = :comment_supply_id order by msgTime desc";
		Query query = session.createQuery(hql);
		query.setParameter("comment_supply_id", co.getComment_supply_id());

		ArrayList<Comment> y = (ArrayList<Comment>) query.list();
		return y;

	}

}
