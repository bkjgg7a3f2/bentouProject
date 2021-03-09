package _01_model._06_comment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {
	private String comment_id; // 即order_id
	private int comment_supply_id;
	private int comment_cstmr_id;
	private int cstmr_fraction;
	private String cstmr_evaluation;
	private String supply_evaluation;
	private Date msgTime;
	private Date replyTime;

	public Comment() {

	}

	public Comment(int comment_supply_id) {
		this.comment_supply_id = comment_supply_id;
	}

	@Id
	@Column(name = "comment_id")
	public String getComment_id() {
		return comment_id;
	}

	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}

	@Column(name = "comment_supply_id")
	public int getComment_supply_id() {
		return comment_supply_id;
	}

	public void setComment_supply_id(int comment_supply_id) {
		this.comment_supply_id = comment_supply_id;
	}

	@Column(name = "comment_cstmr_id")
	public int getComment_cstmr_id() {
		return comment_cstmr_id;
	}

	public void setComment_cstmr_id(int comment_cstmr_id) {
		this.comment_cstmr_id = comment_cstmr_id;
	}

	@Column(name = "cstmr_fraction")
	public int getCstmr_fraction() {
		return cstmr_fraction;
	}

	public void setCstmr_fraction(int cstmr_fraction) {
		this.cstmr_fraction = cstmr_fraction;
	}

	@Column(name = "cstmr_evaluation", columnDefinition = "nvarchar(max)")
	public String getCstmr_evaluation() {
		return cstmr_evaluation;
	}

	public void setCstmr_evaluation(String cstmr_evaluation) {
		this.cstmr_evaluation = cstmr_evaluation;
	}

	@Column(name = "supply_evaluation", columnDefinition = "nvarchar(max)")
	public String getSupply_evaluation() {
		return supply_evaluation;
	}

	public void setSupply_evaluation(String supply_evaluation) {
		this.supply_evaluation = supply_evaluation;
	}

	@Column(name = "msgTime")
	public Date getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(Date msgTime) {
		this.msgTime = msgTime;
	}

	@Column(name = "replyTime")
	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

}
