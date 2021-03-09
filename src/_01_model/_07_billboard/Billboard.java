package _01_model._07_billboard;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "billboard")
public class Billboard {

	private int billboard_id;
	private String title;
	private String content;
	private String picture1;
	private String picture2;
	private String picture3;
	private Date time;
	private String status;

	public Billboard() {
	}

	public Billboard(int billboard_id) {
		this.billboard_id = billboard_id;
	}

	public Billboard(String title, String content, String picture1, String picture2, String picture3, Date time,
			String status) {
		this.title = title;
		this.content = content;
		this.picture1 = picture1;
		this.picture2 = picture2;
		this.picture3 = picture3;
		this.time = time;
		this.status = status;
	}

	public Billboard(int billboard_id, String title, String content, String picture1, String picture2, String picture3,
			String status) {
		this.billboard_id = billboard_id;
		this.title = title;
		this.content = content;
		this.picture1 = picture1;
		this.picture2 = picture2;
		this.picture3 = picture3;
		this.status = status;
	}

	@Id
	@Column(name = "billboard_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getBillboard_id() {
		return billboard_id;
	}

	public void setBillboard_id(int billboard_id) {
		this.billboard_id = billboard_id;
	}

	@Column(name = "title", nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "board_content", nullable = false, columnDefinition = "nvarchar(max)")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "picture1")
	public String getPicture1() {
		return picture1;
	}

	public void setPicture1(String picture1) {
		this.picture1 = picture1;
	}

	@Column(name = "picture2")
	public String getPicture2() {
		return picture2;
	}

	public void setPicture2(String picture2) {
		this.picture2 = picture2;
	}

	@Column(name = "picture3")
	public String getPicture3() {
		return picture3;
	}

	public void setPicture3(String picture3) {
		this.picture3 = picture3;
	}

	@Column(name = "time", nullable = false)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column(name = "status", nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
