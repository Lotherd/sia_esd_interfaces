package trax.aero.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="INTERFACE_LOCK_MASTER")
@NamedQuery(name="InterfaceLockMaster.findAll", query="SELECT i FROM InterfaceLockMaster i")
public class InterfaceLockMaster implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="INTERFACE_TYPE")
	private String interfaceType;

	@Column(name="CURRENT_SERVER")
	private String currentServer;

	@Column(name="EXEC_DELAY")
	private BigDecimal execDelay;

	private BigDecimal locked;

	@Column(name="LOCKED_DATE")
	private Date lockedDate;

	@Column(name="MAX_LOCK")
	private BigDecimal maxLock;

	@Column(name="UNLOCKED_DATE")
	private Date unlockedDate;

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getCurrentServer() {
		return currentServer;
	}

	public void setCurrentServer(String currentServer) {
		this.currentServer = currentServer;
	}

	public BigDecimal getExecDelay() {
		return execDelay;
	}

	public void setExecDelay(BigDecimal execDelay) {
		this.execDelay = execDelay;
	}

	public BigDecimal getLocked() {
		return locked;
	}

	public void setLocked(BigDecimal locked) {
		this.locked = locked;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public BigDecimal getMaxLock() {
		return maxLock != null ? this.maxLock : BigDecimal.ZERO;
	}

	public void setMaxLock(BigDecimal maxLock) {
		this.maxLock = maxLock;
	}

	public Date getUnlockedDate() {
		return unlockedDate;
	}

	public void setUnlockedDate(Date unlockedDate) {
		this.unlockedDate = unlockedDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	


}
