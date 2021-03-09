package _01_model._06_comment.Interface;

import java.util.ArrayList;

import _01_model._06_comment.Comment;

public interface ICommentDao {
	public Comment add (Comment co);
	public Comment update(Comment co);
	public void delete(String order_id);
	public ArrayList<Comment> selectAllbySupply_id(Comment co);
	public Comment selectOne(String comment_id);
}
