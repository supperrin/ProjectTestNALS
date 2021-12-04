package net.codejava;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Todo {
	private Long id;
	private String workname;
	private String startingdate;
	private String endingdate;
	private String status;

	protected Todo() {
	}

	protected Todo(Long id, String workname,String startingdate, String endingdate, String status) {
		super();
		this.id = id;
		this.workname = workname;
		this.startingdate = startingdate;
		this.endingdate = endingdate;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getWorkname() {
		return workname;
	}

	public void setWorkname(String workname) {
		this.workname = workname;
	}

	public String getStartingdate() {
		return startingdate;
	}

	public void setStartingdate(String startingdate) {
		this.startingdate = startingdate;
	}

	public String getEndingdate() {
		return endingdate;
	}

	public void setEndingdate(String endingdate) {
		this.endingdate = endingdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
