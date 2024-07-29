package trax.aero.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * The persistent class for the INTERFACE_LOCK_MASTER database table.
 * 
 */
@Entity
@Table(name="INTERFACE_LOCK_MASTER")
@NamedQuery(name="InterfaceLockMaster.findAll", query="SELECT i FROM InterfaceLockMaster i")
public class InterfaceLockMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="INTERFACE_TYPE")
	private String interfaceType;

	private int locked;


	@Column(name="LOCKED_DATE")
	private Date lockedDate;


	@Column(name="UNLOCKED_DATE")
	private Date unlockedDate;
	
	@Column(name = "CURRENT_SERVER")
	private String server;
	
	@Column(name = "EXEC_DELAY")
	private long delay;
	
	@Column(name = "MAX_LOCK")
	private long max;
	
	public InterfaceLockMaster() {
	}

	

	public int getLocked() {
		return this.locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public Date getLockedDate() {
		return this.lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public Date getUnlockedDate() {
		return this.unlockedDate;
	}

	public void setUnlockedDate(Date unlockedDate) {
		this.unlockedDate = unlockedDate;
	}
	
	public void setServer(String server)
	{
		this.server = server;
	}
	public String getServer()
	{
		return server;
	}
	
	public long getMax()
	{
		return max;
	}
	
	public void setMax(long max)
	{
		this.max = max;
	}
	
	public long getDelay()
	{
		return delay;
	}
	
	public void setDelay(long delay)
	{
		this.delay = delay;
	}

}