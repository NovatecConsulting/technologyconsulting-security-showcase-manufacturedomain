package de.novatec.showcase.manufacture.ejb.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Table(name = "M_WORKORDER")
@Entity
@NamedQueries(value = { @NamedQuery(name = "workorderQueryAll", query = WorkOrder.QUERY_ALL),
		@NamedQuery(name = "workorderQueryByStatus", query = WorkOrder.QUERY_BY_STATUS),
		@NamedQuery(name = "workorderQueryByOidOlid", query = WorkOrder.QUERY_BY_OID_OLID) })
public class WorkOrder implements Serializable {
	public static final String QUERY_ALL = "SELECT w FROM WorkOrder w";
	public static final String QUERY_BY_STATUS = "SELECT w FROM WorkOrder w WHERE w.status = :status";
	public static final String QUERY_BY_OID_OLID = "SELECT w FROM WorkOrder w WHERE w.salesId = :oid AND w.orderLineId = :olid";

	private static final long serialVersionUID = 6815526442903454957L;

	@Id
	@Column(name = "WO_NUMBER")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "WO_ID_GEN")
	@TableGenerator(name = "WO_ID_GEN", table = "U_SEQUENCES", pkColumnName = "s_id", valueColumnName = "s_nextnum", pkColumnValue = "WO_SEQ", allocationSize = 1)
	private int id;

	@Column(name = "WO_LOCATION")
	private int location;

	@Column(name = "WO_O_ID")
	private int salesId;

	@Column(name = "WO_OL_ID")
	private int orderLineId;

	@Column(name = "WO_STATUS")
	private WorkOrderStatus status;

	@Column(name = "WO_ORIG_QTY")
	private int originalQuantity;

	@Column(name = "WO_COMP_QTY")
	private int completedQuantity;

	@Column(name = "WO_DUE_DATE")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Calendar dueDate;

	@Column(name = "WO_START_DATE")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Calendar startDate;

	@Column(name = "WO_ASSEMBLY_ID")
	private String assemblyId;

	@Version
	@Column(name = "WO_VERSION")
	private int version;

	public WorkOrder() {
		super();
	}

	/**
	 * Constructor with fields that are known before start of work.<br>
	 * <br>
	 * Only for LargeOrders from the Order Domain.
	 * 
	 * @param location
	 * @param salesId
	 * @param orderLineId
	 * @param originalQuantity
	 * @param dueDate
	 * @param assemblyId
	 */
	public WorkOrder(int location, int salesId, int orderLineId, int originalQuantity, Calendar dueDate,
			String assemblyId) {
		super();
		this.location = location;
		this.salesId = salesId;
		this.orderLineId = orderLineId;
		this.status = WorkOrderStatus.OPEN;
		this.originalQuantity = originalQuantity;
		this.completedQuantity = -1;
		this.dueDate = dueDate;
		this.assemblyId = assemblyId;
		this.version = 0;
	}

	/**
	 * Constructor with fields that are known before start of work.<br>
	 * <br>
	 * Only for planned orders -> salesId and orderLineId is set to -1.
	 * 
	 * @param location
	 * @param originalQuantity
	 * @param dueDate
	 * @param assemblyId
	 */
	public WorkOrder(int location, int originalQuantity, Calendar dueDate, String assemblyId) {
		super();
		this.location = location;
		this.salesId = -1;
		this.orderLineId = -1;
		this.status = WorkOrderStatus.OPEN;
		this.originalQuantity = originalQuantity;
		this.completedQuantity = -1;
		this.dueDate = dueDate;
		this.assemblyId = assemblyId;
		this.version = 0;
	}

	/**
	 * Constructor with all fields.<br>
	 * <br>
	 * With this Constructor the id is explicitly set, i.e. it won't be generated by
	 * jpa.
	 * 
	 * @param location
	 * @param salesId
	 * @param orderLineId
	 * @param status
	 * @param originalQuantity
	 * @param completedQuantity
	 * @param dueDate
	 * @param startDate
	 * @param assemblyId
	 * @param version
	 */
	public WorkOrder(int id, int location, int salesId, int orderLineId, WorkOrderStatus status, int originalQuantity,
			int completedQuantity, Calendar dueDate, Calendar startDate, String assemblyId, int version) {
		super();
		this.id = id;
		this.location = location;
		this.salesId = salesId;
		this.orderLineId = orderLineId;
		this.status = status;
		this.originalQuantity = originalQuantity;
		this.completedQuantity = completedQuantity;
		this.dueDate = dueDate;
		this.startDate = startDate;
		this.assemblyId = assemblyId;
		this.version = version;
	}

	public int getId() {
		return id;
	}

	public String getAssemblyId() {
		return assemblyId;
	}

	public int getCompletedQuantity() {
		return completedQuantity;
	}

	public void setCompletedQuantity(int completedQuantity) {
		this.completedQuantity = completedQuantity;
	}

	public int getOrderLineId() {
		return orderLineId;
	}

	public Calendar getDueDate() {
		return dueDate;
	}

	public int getSalesId() {
		return salesId;
	}

	public int getOriginalQuantity() {
		return originalQuantity;
	}

	public int getLocation() {
		return location;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public WorkOrderStatus getStatus() {
		return status;
	}

	public void setStatus(WorkOrderStatus status) {
		this.status = status;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getVersion() {
		return version;
	}

	public void setStatusCompleted() {
		this.status = WorkOrderStatus.COMPLETED;
	}

	public void advanceStatus() {
		if ((this.status.ordinal() + 1) < WorkOrderStatus.values().length) {
			this.status = WorkOrderStatus.values()[this.status.ordinal() + 1];
		}
	}

	public void setStatusCancelled() {
		this.status = WorkOrderStatus.CANCELED;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public void setDueDate(Calendar dueDate) {
		this.dueDate = dueDate;
	}

	public void setAssemblyId(String assemblyId) {
		this.assemblyId = assemblyId;
	}

	public void setSalesId(int salesId) {
		this.salesId = salesId;
	}

	public void setOrderLineId(int orderLineId) {
		this.orderLineId = orderLineId;
	}

	public void setOriginalQuantity(int originalQuantity) {
		this.originalQuantity = originalQuantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(assemblyId, completedQuantity, dueDate, id, location, orderLineId, originalQuantity,
				salesId, startDate, status, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof WorkOrder)) {
			return false;
		}
		WorkOrder other = (WorkOrder) obj;
		return Objects.equals(assemblyId, other.assemblyId) && completedQuantity == other.completedQuantity
				&& Objects.equals(dueDate, other.dueDate) && id == other.id && location == other.location
				&& orderLineId == other.orderLineId && originalQuantity == other.originalQuantity
				&& salesId == other.salesId && Objects.equals(startDate, other.startDate) && status == other.status
				&& version == other.version;
	}

	@Override
	public String toString() {
		return "WorkOrder [id=" + id + ", location=" + location + ", salesId=" + salesId + ", orderLineId="
				+ orderLineId + ", status=" + status + ", originalQuantity=" + originalQuantity + ", completedQuantity="
				+ completedQuantity + ", dueDate=" + dueDate + ", startDate=" + startDate + ", assemblyId=" + assemblyId
				+ ", version=" + version + "]";
	}
}