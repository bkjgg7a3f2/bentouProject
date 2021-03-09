package _01_model._08_message;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class MsgBean {
	private int msg_id;
	private String talker;// 即memberType
	private int cstmr_id;
	private int supply_id;
	private String msg;
	private Date sendTime;

	public MsgBean() {
	}

	public MsgBean(int msg_id, String talker, int cstmr_id, int supply_id, String msg, Date sendTime) {
		super();
		this.msg_id = msg_id;
		this.talker = talker;
		this.cstmr_id = cstmr_id;
		this.supply_id = supply_id;
		this.msg = msg;
		this.sendTime = sendTime;
	}

	@Id
	@Column(name = "msg_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	}

	@Column(name = "talker", nullable = false) // 即memberType
	public String getTalker() {
		return talker;
	}

	public void setTalker(String talker) {
		this.talker = talker;
	}

	@Column(name = "cstmr_id", nullable = false)
	public int getCstmr_id() {
		return cstmr_id;
	}

	public void setCstmr_id(int cstmr_id) {
		this.cstmr_id = cstmr_id;
	}

	@Column(name = "supply_id", nullable = false)
	public int getSupply_id() {
		return supply_id;
	}

	public void setSupply_id(int supply_id) {
		this.supply_id = supply_id;
	}

	@Column(name = "msg", nullable = false, columnDefinition = "nvarchar(max)")
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Column(name = "sendTime", nullable = false)
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

}
