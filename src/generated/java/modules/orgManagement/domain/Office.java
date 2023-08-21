package modules.orgManagement.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import modules.orgManagement.Staff.StaffExtension;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.impl.domain.AbstractPersistentBean;

/**
 * Office
 * 
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class Office extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "orgManagement";

	/** @hidden */
	public static final String DOCUMENT_NAME = "Office";

	/** @hidden */
	public static final String levelUnitPropertyName = "levelUnit";

	/** @hidden */
	public static final String buildingNamePropertyName = "buildingName";

	/** @hidden */
	public static final String streetAddressPropertyName = "streetAddress";

	/** @hidden */
	public static final String suburbPropertyName = "suburb";

	/** @hidden */
	public static final String postCodePropertyName = "postCode";

	/** @hidden */
	public static final String phonePropertyName = "phone";

	/** @hidden */
	public static final String imagePropertyName = "image";

	/** @hidden */
	public static final String officeStaffPropertyName = "officeStaff";

	/**
	 * Level Unit
	 **/
	private String levelUnit;

	/**
	 * Building Name
	 **/
	private String buildingName;

	/**
	 * Street Address
	 **/
	private String streetAddress;

	/**
	 * Suburb
	 **/
	private String suburb;

	/**
	 * PostCode
	 **/
	private String postCode;

	/**
	 * Phone
	 **/
	private String phone;

	/**
	 * Image
	 **/
	private String image;

	/**
	 * Office Staff
	 **/
	private List<StaffExtension> officeStaff = new ArrayList<>();

	@Override
	@XmlTransient
	public String getBizModule() {
		return Office.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return Office.DOCUMENT_NAME;
	}

	public static Office newInstance() {
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
			return org.skyve.util.Binder.formatMessage("Office - {levelUnit} {buildingName} {streetAddress}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof Office) && 
					this.getBizId().equals(((Office) o).getBizId()));
	}

	/**
	 * {@link #levelUnit} accessor.
	 * @return	The value.
	 **/
	public String getLevelUnit() {
		return levelUnit;
	}

	/**
	 * {@link #levelUnit} mutator.
	 * @param levelUnit	The new value.
	 **/
	@XmlElement
	public void setLevelUnit(String levelUnit) {
		preset(levelUnitPropertyName, levelUnit);
		this.levelUnit = levelUnit;
	}

	/**
	 * {@link #buildingName} accessor.
	 * @return	The value.
	 **/
	public String getBuildingName() {
		return buildingName;
	}

	/**
	 * {@link #buildingName} mutator.
	 * @param buildingName	The new value.
	 **/
	@XmlElement
	public void setBuildingName(String buildingName) {
		preset(buildingNamePropertyName, buildingName);
		this.buildingName = buildingName;
	}

	/**
	 * {@link #streetAddress} accessor.
	 * @return	The value.
	 **/
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * {@link #streetAddress} mutator.
	 * @param streetAddress	The new value.
	 **/
	@XmlElement
	public void setStreetAddress(String streetAddress) {
		preset(streetAddressPropertyName, streetAddress);
		this.streetAddress = streetAddress;
	}

	/**
	 * {@link #suburb} accessor.
	 * @return	The value.
	 **/
	public String getSuburb() {
		return suburb;
	}

	/**
	 * {@link #suburb} mutator.
	 * @param suburb	The new value.
	 **/
	@XmlElement
	public void setSuburb(String suburb) {
		preset(suburbPropertyName, suburb);
		this.suburb = suburb;
	}

	/**
	 * {@link #postCode} accessor.
	 * @return	The value.
	 **/
	public String getPostCode() {
		return postCode;
	}

	/**
	 * {@link #postCode} mutator.
	 * @param postCode	The new value.
	 **/
	@XmlElement
	public void setPostCode(String postCode) {
		preset(postCodePropertyName, postCode);
		this.postCode = postCode;
	}

	/**
	 * {@link #phone} accessor.
	 * @return	The value.
	 **/
	public String getPhone() {
		return phone;
	}

	/**
	 * {@link #phone} mutator.
	 * @param phone	The new value.
	 **/
	@XmlElement
	public void setPhone(String phone) {
		preset(phonePropertyName, phone);
		this.phone = phone;
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
	 * {@link #officeStaff} accessor.
	 * @return	The value.
	 **/
	@XmlElement
	public List<StaffExtension> getOfficeStaff() {
		return officeStaff;
	}

	/**
	 * {@link #officeStaff} accessor.
	 * @param bizId	The bizId of the element in the list.
	 * @return	The value of the element in the list.
	 **/
	public StaffExtension getOfficeStaffElementById(String bizId) {
		return getElementById(officeStaff, bizId);
	}

	/**
	 * {@link #officeStaff} mutator.
	 * @param bizId	The bizId of the element in the list.
	 * @param element	The new value of the element in the list.
	 **/
	public void setOfficeStaffElementById(String bizId, StaffExtension element) {
		setElementById(officeStaff, element);
	}

	/**
	 * {@link #officeStaff} add.
	 * @param element	The element to add.
	 **/
	public boolean addOfficeStaffElement(StaffExtension element) {
		boolean result = false;
		if (getElementById(officeStaff, element.getBizId()) == null) {
			result = officeStaff.add(element);
		}
		element.setBaseOffice(this);
		return result;
	}

	/**
	 * {@link #officeStaff} add.
	 * @param index	The index in the list to add the element to.
	 * @param element	The element to add.
	 **/
	public void addOfficeStaffElement(int index, StaffExtension element) {
		officeStaff.add(index, element);
		element.setBaseOffice(this);
	}

	/**
	 * {@link #officeStaff} remove.
	 * @param element	The element to remove.
	 **/
	public boolean removeOfficeStaffElement(StaffExtension element) {
		boolean result = officeStaff.remove(element);
		if (result) {
			element.nullBaseOffice();
		}
		return result;
	}

	/**
	 * {@link #officeStaff} remove.
	 * @param index	The index in the list to remove the element from.
	 **/
	public StaffExtension removeOfficeStaffElement(int index) {
		StaffExtension result = officeStaff.remove(index);
		result.nullBaseOffice();
		return result;
	}
}
