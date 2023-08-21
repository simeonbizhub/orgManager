package modules.orgManagement.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import modules.orgManagement.Staff.StaffExtension;
import modules.orgManagement.domain.Staff.Status;
import org.skyve.CORE;
import org.skyve.domain.Bean;
import org.skyve.domain.ChildBean;
import org.skyve.domain.messages.DomainException;
import org.skyve.domain.types.Timestamp;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.impl.domain.types.jaxb.TimestampMapper;

/**
 * StaffStatusHistory
 * 
 * @depend - - - Status
 * @stereotype "persistent child"
 */
@XmlType
@XmlRootElement
public class StaffStatusHistory extends AbstractPersistentBean implements ChildBean<StaffExtension> {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "orgManagement";

	/** @hidden */
	public static final String DOCUMENT_NAME = "StaffStatusHistory";

	/** @hidden */
	public static final String statusPropertyName = "status";

	/** @hidden */
	public static final String statusTimeStampPropertyName = "statusTimeStamp";

	/**
	 * Status
	 **/
	private Status status;

	/**
	 * Time Stamp
	 **/
	private Timestamp statusTimeStamp;

	private StaffExtension parent;

	private Integer bizOrdinal;

	@Override
	@XmlTransient
	public String getBizModule() {
		return StaffStatusHistory.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return StaffStatusHistory.DOCUMENT_NAME;
	}

	public static StaffStatusHistory newInstance() {
		try {
			return CORE.getUser().getCustomer().getModule(MODULE_NAME).getDocument(CORE.getUser().getCustomer(), DOCUMENT_NAME).newInstance(CORE.getUser());
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new DomainException(e);
		}
	}

	@Override
	@XmlTransient
	public String getBizKey() {
		try {
			return org.skyve.util.Binder.formatMessage("StaffStatusHistory", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof StaffStatusHistory) && 
					this.getBizId().equals(((StaffStatusHistory) o).getBizId()));
	}

	/**
	 * {@link #status} accessor.
	 * @return	The value.
	 **/
	public Status getStatus() {
		return status;
	}

	/**
	 * {@link #status} mutator.
	 * @param status	The new value.
	 **/
	@XmlElement
	public void setStatus(Status status) {
		preset(statusPropertyName, status);
		this.status = status;
	}

	/**
	 * {@link #statusTimeStamp} accessor.
	 * @return	The value.
	 **/
	public Timestamp getStatusTimeStamp() {
		return statusTimeStamp;
	}

	/**
	 * {@link #statusTimeStamp} mutator.
	 * @param statusTimeStamp	The new value.
	 **/
	@XmlElement
	@XmlSchemaType(name = "dateTime")
	@XmlJavaTypeAdapter(TimestampMapper.class)
	public void setStatusTimeStamp(Timestamp statusTimeStamp) {
		preset(statusTimeStampPropertyName, statusTimeStamp);
		this.statusTimeStamp = statusTimeStamp;
	}

	@Override
	public StaffExtension getParent() {
		return parent;
	}

	@Override
	@XmlElement
	public void setParent(StaffExtension parent) {
		if (this.parent != parent) {
			preset(ChildBean.PARENT_NAME, parent);
			this.parent = parent;
		}
	}

	@Override
	public Integer getBizOrdinal() {
		return bizOrdinal;
	}

	@Override
	@XmlElement
	public void setBizOrdinal(Integer bizOrdinal) {
		preset(Bean.ORDINAL_NAME, bizOrdinal);
		this.bizOrdinal =  bizOrdinal;
	}
}
