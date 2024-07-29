package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TASK_CARD_EXECUTION database table.
 * 
 */
@Entity
@Table(name="TASK_CARD_EXECUTION")
@NamedQuery(name="TaskCardExecution.findAll", query="SELECT t FROM TaskCardExecution t")
public class TaskCardExecution implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TaskCardExecutionPK id;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private BigDecimal notes;

	@Column(name="TASK_CARD_EXECUTION_ORDER")
	private BigDecimal taskCardExecutionOrder;

	@Column(name="TASK_CARD_PREDECESSORS")
	private String taskCardPredecessors;

	//bi-directional many-to-one association to TaskCard
	@ManyToOne
	@JoinColumn(name="TASK_CARD" , insertable=false, updatable=false)
	private TaskCard taskCardBean;

	public TaskCardExecution() {
	}

	public TaskCardExecutionPK getId() {
		return this.id;
	}

	public void setId(TaskCardExecutionPK id) {
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

	public BigDecimal getNotes() {
		return this.notes;
	}

	public void setNotes(BigDecimal notes) {
		this.notes = notes;
	}

	public BigDecimal getTaskCardExecutionOrder() {
		return this.taskCardExecutionOrder;
	}

	public void setTaskCardExecutionOrder(BigDecimal taskCardExecutionOrder) {
		this.taskCardExecutionOrder = taskCardExecutionOrder;
	}

	public String getTaskCardPredecessors() {
		return this.taskCardPredecessors;
	}

	public void setTaskCardPredecessors(String taskCardPredecessors) {
		this.taskCardPredecessors = taskCardPredecessors;
	}

	public TaskCard getTaskCardBean() {
		return this.taskCardBean;
	}

	public void setTaskCardBean(TaskCard taskCardBean) {
		this.taskCardBean = taskCardBean;
	}

}