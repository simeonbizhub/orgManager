package modules.orgManagement.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.domain.types.DateTime;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.impl.domain.types.jaxb.DateTimeMapper;

/**
 * AbstractLastChanged
 * 
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public abstract class AbstractLastChanged extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "orgManagement";

	/** @hidden */
	public static final String DOCUMENT_NAME = "AbstractLastChanged";

	/** @hidden */
	public static final String lastChangedDateTimePropertyName = "lastChangedDateTime";

	/**
	 * Last Changed
	 **/
	private DateTime lastChangedDateTime;

	@Override
	@XmlTransient
	public String getBizModule() {
		return AbstractLastChanged.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return AbstractLastChanged.DOCUMENT_NAME;
	}

	public static AbstractLastChanged newInstance() {
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
			return org.skyve.util.Binder.formatMessage("AbstractLastChanged", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof AbstractLastChanged) && 
					this.getBizId().equals(((AbstractLastChanged) o).getBizId()));
	}

	/**
	 * {@link #lastChangedDateTime} accessor.
	 * @return	The value.
	 **/
	public DateTime getLastChangedDateTime() {
		return lastChangedDateTime;
	}

	/**
	 * {@link #lastChangedDateTime} mutator.
	 * @param lastChangedDateTime	The new value.
	 **/
	@XmlElement
	@XmlSchemaType(name = "dateTime")
	@XmlJavaTypeAdapter(DateTimeMapper.class)
	public void setLastChangedDateTime(DateTime lastChangedDateTime) {
		preset(lastChangedDateTimePropertyName, lastChangedDateTime);
		this.lastChangedDateTime = lastChangedDateTime;
	}
}
