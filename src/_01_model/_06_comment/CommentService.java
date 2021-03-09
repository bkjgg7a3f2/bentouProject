package _01_model._06_comment;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import _01_model._06_comment.Interface.ICommentService;


@Service
public class CommentService implements ICommentService {
	private CommentDao cDao;
	
	@Autowired
	public CommentService(CommentDao cDao) {
		this.cDao=cDao;
	}

	@Override
	public Comment add(Comment co) {
		return cDao.add(co);
	}

	@Override
	public Comment update(Comment co) {
		return cDao.update(co);
	}

	@Override
	public void delete(String order_id) {
		cDao.delete(order_id);
	}

	@Override
	public ArrayList<Comment> selectAllbySupply_id(Comment co) {
		return cDao.selectAllbySupply_id(co);
	}
	
//	@Override
	public Comment selectOne(String comment_id) {
		return cDao.selectOne(comment_id);
	}

}
