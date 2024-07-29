package trax.aero.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the TASK_CARD_KEYWORD database table.
 * 
 */
@Entity
@Table(name="TASK_CARD_KEYWORD")
@NamedQuery(name="TaskCardKeyword.findAll", query="SELECT t FROM TaskCardKeyword t")
public class TaskCardKeyword implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TaskCardKeywordPK id;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="KEYWORD_TEXT")
	private String keywordText;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	//bi-directional many-to-one association to TaskCard
	@ManyToOne
	@JoinColumn(name="TASK_CARD" , insertable=false, updatable=false)
	private TaskCard taskCardBean;

	public TaskCardKeyword() {
	}

	public TaskCardKeywordPK getId() {
		return this.id;
	}

	public void setId(TaskCardKeywordPK id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getKeywordText() {
		return this.keywordText;
	}

	public void setKeywordText(String keywordText) {
		this.keywordText = keywordText;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public TaskCard getTaskCardBean() {
		return this.taskCardBean;
	}

	public void setTaskCardBean(TaskCard taskCardBean) {
		this.taskCardBean = taskCardBean;
	}

}