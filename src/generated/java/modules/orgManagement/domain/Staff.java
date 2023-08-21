package modules.orgManagement.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import modules.orgManagement.Staff.StaffExtension;
import org.locationtech.jts.geom.Geometry;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.domain.types.DateOnly;
import org.skyve.domain.types.Enumeration;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.impl.domain.types.jaxb.DateOnlyMapper;
import org.skyve.impl.domain.types.jaxb.GeometryMapper;
import org.skyve.metadata.model.document.Bizlet.DomainValue;
import org.skyve.util.Util;

/**
 * Staff
 * 
 * @depend - - - Status
 * @depend - - - CurrentActivity
 * @navhas n baseOffice 0..1 Office
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public abstract class Staff extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "orgManagement";

	/** @hidden */
	public static final String DOCUMENT_NAME = "Staff";

	/** @hidden */
	public static final String codePropertyName = "code";

	/** @hidden */
	public static final String namePropertyName = "name";

	/** @hidden */
	public static final String dateOfBirthPropertyName = "dateOfBirth";

	/** @hidden */
	public static final String ageInYearsPropertyName = "ageInYears";

	/** @hidden */
	public static final String locationPropertyName = "location";

	/** @hidden */
	public static final String imagePropertyName = "image";

	/** @hidden */
	public static final String statusPropertyName = "status";

	/** @hidden */
	public static final String baseOfficePropertyName = "baseOffice";

	/** @hidden */
	public static final String currentActivityPropertyName = "currentActivity";

	/**
	 * Status
	 **/
	@XmlEnum
	public static enum Status implements Enumeration {
		in("In", "In"),
		out("Out", "Out"),
		havingLunch("Out to Lunch", "Having Lunch");

		private String code;
		private String description;

		/** @hidden */
		private DomainValue domainValue;

		/** @hidden */
		private static List<DomainValue> domainValues;

		private Status(String code, String description) {
			this.code = code;
			this.description = description;
			this.domainValue = new DomainValue(code, description);
		}

		@Override
		public String toCode() {
			return code;
		}

		@Override
		public String toLocalisedDescription() {
			return Util.i18n(description);
		}

		@Override
		public DomainValue toDomainValue() {
			return domainValue;
		}

		public static Status fromCode(String code) {
			Status result = null;

			for (Status value : values()) {
				if (value.code.equals(code)) {
					result = value;
					break;
				}
			}

			return result;
		}

		public static Status fromLocalisedDescription(String description) {
			Status result = null;

			for (Status value : values()) {
				if (value.toLocalisedDescription().equals(description)) {
					result = value;
					break;
				}
			}

			return result;
		}

		public static List<DomainValue> toDomainValues() {
			if (domainValues == null) {
				Status[] values = values();
				domainValues = new ArrayList<>(values.length);
				for (Status value : values) {
					domainValues.add(value.domainValue);
				}
			}

			return domainValues;
		}
	}

	/**
	 * Current Activity
	 **/
	@XmlEnum
	public static enum CurrentActivity implements Enumeration {
		eating("eating", "eating"),
		parking("parking", "parking"),
		working("working", "working"),
		meeting("meeting", "meeting"),
		sleeping("sleeping", "sleeping");

		private String code;
		private String description;

		/** @hidden */
		private DomainValue domainValue;

		/** @hidden */
		private static List<DomainValue> domainValues;

		private CurrentActivity(String code, String description) {
			this.code = code;
			this.description = description;
			this.domainValue = new DomainValue(code, description);
		}

		@Override
		public String toCode() {
			return code;
		}

		@Override
		public String toLocalisedDescription() {
			return Util.i18n(description);
		}

		@Override
		public DomainValue toDomainValue() {
			return domainValue;
		}

		public static CurrentActivity fromCode(String code) {
			CurrentActivity result = null;

			for (CurrentActivity value : values()) {
				if (value.code.equals(code)) {
					result = value;
					break;
				}
			}

			return result;
		}

		public static CurrentActivity fromLocalisedDescription(String description) {
			CurrentActivity result = null;

			for (CurrentActivity value : values()) {
				if (value.toLocalisedDescription().equals(description)) {
					result = value;
					break;
				}
			}

			return result;
		}

		public static List<DomainValue> toDomainValues() {
			if (domainValues == null) {
				CurrentActivity[] values = values();
				domainValues = new ArrayList<>(values.length);
				for (CurrentActivity value : values) {
					domainValues.add(value.domainValue);
				}
			}

			return domainValues;
		}
	}

	/**
	 * Code
	 **/
	private String code;

	/**
	 * Name
	 **/
	private String name;

	/**
	 * Date of Birth
	 **/
	private DateOnly dateOfBirth;

	/**
	 * Age in Years
	 **/
	private Integer ageInYears;

	/**
	 * Location
	 **/
	private Geometry location;

	/**
	 * Image
	 **/
	private String image;

	/**
	 * Status
	 **/
	private Status status;

	/**
	 * Base Office
	 **/
	private Office baseOffice = null;

	/**
	 * Current Activity
	 **/
	private CurrentActivity currentActivity;

	@Override
	@XmlTransient
	public String getBizModule() {
		return Staff.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return Staff.DOCUMENT_NAME;
	}

	public static StaffExtension newInstance() {
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
			return org.skyve.util.Binder.formatMessage("{code}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof Staff) && 
					this.getBizId().equals(((Staff) o).getBizId()));
	}

	/**
	 * {@link #code} accessor.
	 * @return	The value.
	 **/
	public String getCode() {
		return code;
	}

	/**
	 * {@link #code} mutator.
	 * @param code	The new value.
	 **/
	@XmlElement
	public void setCode(String code) {
		preset(codePropertyName, code);
		this.code = code;
	}

	/**
	 * {@link #name} accessor.
	 * @return	The value.
	 **/
	public String getName() {
		return name;
	}

	/**
	 * {@link #name} mutator.
	 * @param name	The new value.
	 **/
	@XmlElement
	public void setName(String name) {
		preset(namePropertyName, name);
		this.name = name;
	}

	/**
	 * {@link #dateOfBirth} accessor.
	 * @return	The value.
	 **/
	public DateOnly getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * {@link #dateOfBirth} mutator.
	 * @param dateOfBirth	The new value.
	 **/
	@XmlElement
	@XmlSchemaType(name = "date")
	@XmlJavaTypeAdapter(DateOnlyMapper.class)
	public void setDateOfBirth(DateOnly dateOfBirth) {
		preset(dateOfBirthPropertyName, dateOfBirth);
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * {@link #ageInYears} accessor.
	 * @return	The value.
	 **/
	public Integer getAgeInYears() {
		return ageInYears;
	}

	/**
	 * {@link #ageInYears} mutator.
	 * @param ageInYears	The new value.
	 **/
	@XmlElement
	public void setAgeInYears(Integer ageInYears) {
		preset(ageInYearsPropertyName, ageInYears);
		this.ageInYears = ageInYears;
	}

	/**
	 * {@link #location} accessor.
	 * @return	The value.
	 **/
	public Geometry getLocation() {
		return location;
	}

	/**
	 * {@link #location} mutator.
	 * @param location	The new value.
	 **/
	@XmlElement
	@XmlJavaTypeAdapter(GeometryMapper.class)
	public void setLocation(Geometry location) {
		preset(locationPropertyName, location);
		this.location = location;
	}

	/**
	 * {@link #image} accessor.
	 * @return	The value.
	 **/
	public String getImage() {
		return image;
	}

	/**
	 * {@link #image} mutator.
	 * @param image	The new value.
	 **/
	@XmlElement
	public void setImage(String image) {
		preset(imagePropertyName, image);
		this.image = image;
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
	 * {@link #baseOffice} accessor.
	 * @return	The value.
	 **/
	public Office getBaseOffice() {
		return baseOffice;
	}

	/**
	 * {@link #baseOffice} mutator.
	 * @param baseOffice	The new value.
	 **/
	@XmlElement
	public void setBaseOffice(Office baseOffice) {
		if (this.baseOffice != baseOffice) {
			preset(baseOfficePropertyName, baseOffice);
			this.baseOffice = baseOffice;
		}
	}

	/**
	 * {@link #currentActivity} accessor.
	 * @return	The value.
	 **/
	public CurrentActivity getCurrentActivity() {
		return currentActivity;
	}

	/**
	 * {@link #currentActivity} mutator.
	 * @param currentActivity	The new value.
	 **/
	@XmlElement
	public void setCurrentActivity(CurrentActivity currentActivity) {
		preset(currentActivityPropertyName, currentActivity);
		this.currentActivity = currentActivity;
	}

	/**
	 * showComment
	 *
	 * @return The condition
	 */
	@XmlTransient
	public boolean isShowComment() {
		return (getStatus() != null && Status.in.equals(getStatus()));
	}

	/**
	 * {@link #isShowComment} negation.
	 *
	 * @return The negated condition
	 */
	public boolean isNotShowComment() {
		return (! isShowComment());
	}
}
