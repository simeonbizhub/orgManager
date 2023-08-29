package modules.orgManagement.Staff;

import java.util.ArrayList;
import java.util.List;

import org.skyve.CORE;
import org.skyve.domain.Bean;
import org.skyve.domain.messages.Message;
import org.skyve.domain.messages.ValidationException;
import org.skyve.domain.types.DateOnly;
import org.skyve.domain.types.Timestamp;
import org.skyve.metadata.controller.ImplicitActionName;
import org.skyve.metadata.model.document.Bizlet;
import org.skyve.util.Util;
import org.skyve.web.WebContext;

import modules.admin.ModulesUtil;
import modules.orgManagement.AbstractLastChanged.AbstractLastChangedBizlet;
import modules.orgManagement.domain.Office;
import modules.orgManagement.domain.Staff;
import modules.orgManagement.domain.Staff.CurrentActivity;
import modules.orgManagement.domain.StaffStatusHistory;

public class StaffBizlet extends AbstractLastChangedBizlet<StaffExtension> {

	@Override
	public List<String> complete(String attributeName, String value, StaffExtension bean) throws Exception {
		/*
		 * List<String> results = new ArrayList<>();
		 * if(Staff.namePropertyName.equals(attributeName)) { if(value == null) {
		 * results.add("apple"); results.add("banana"); } else if(value.endsWith("a")) {
		 * results.add(value + "apple"); } else if(value.endsWith("b")) {
		 * results.add(value + "banana"); } }
		 */
		return ModulesUtil.getCompleteSuggestions(Office.MODULE_NAME, Office.DOCUMENT_NAME, Office.suburbPropertyName, value);
//		return super.complete(attributeName, value, bean);
	}

	private static final String STAFF_CODE_PREFIX = "S";

	@Override
	public void validate(StaffExtension bean, ValidationException e) throws Exception {
		// Auto-generated method stub
		DateOnly today = new DateOnly();
		if (bean.getDateOfBirth() != null && bean.getDateOfBirth().after(today)) {
			String msg = Util.i18n("orgmanagement.staff.edit.futureDateOfBirthInvalid");
			e.getMessages().add(new Message(Staff.dateOfBirthPropertyName, msg));
		}
		super.validate(bean, e);
	}

	@Override
	public void preSave(StaffExtension bean) throws Exception {
		// Generate prefix code
		super.preSave(bean);
	}

	@Override
	public StaffExtension preExecute(ImplicitActionName actionName, StaffExtension bean, Bean parentBean,
			WebContext webContext) throws Exception {
		// Generate prefix code
		if (ImplicitActionName.Save.equals(actionName) || ImplicitActionName.OK.equals(actionName)) {
			if (bean.getCode() == null) {
				String code = CORE.getNumberGenerator().next(STAFF_CODE_PREFIX, Staff.MODULE_NAME, Staff.DOCUMENT_NAME,
						Staff.codePropertyName, 4);
				bean.setCode(code);
			}
		}
		if (ImplicitActionName.Edit.equals(actionName)) {
			bean.calculateAgeInYears();
			bean.calculateStatusHistoryCount();
		}
		return super.preExecute(actionName, bean, parentBean, webContext);
	}

	@Override
	public void preRerender(String source, StaffExtension bean, WebContext webContext) throws Exception {
		if (Staff.dateOfBirthPropertyName.equals(source)) {
			bean.calculateAgeInYears();
		} else if (Staff.statusPropertyName.equals(source)) {
			if (bean.originalValues().keySet().contains(Staff.statusPropertyName)) {
				Object o = bean.originalValues().get(Staff.statusPropertyName);
				if (o != null && !o.equals(bean.getStatus()))
				{
				Util.LOGGER.info("The original value of status was " + (bean.originalValues().get(Staff.statusPropertyName)).toString());
				StaffStatusHistory newHistory = StaffStatusHistory.newInstance();
				newHistory.setStatus(bean.getStatus());
				newHistory.setStatusTimeStamp(new Timestamp());
				bean.addStatusHistoryElement(0, newHistory);
				bean.calculateStatusHistoryCount();
				}
			}
		}
		super.preRerender(source, bean, webContext);
	}

	@Override
	public List<DomainValue> getDynamicDomainValues(String attributeName, StaffExtension bean) throws Exception {
		// Generate current activity that are valid
		if (Staff.currentActivityPropertyName.equals(attributeName)) {
			if (bean.getStatus() != null) {
				List<DomainValue> result = new ArrayList<>();
				switch (bean.getStatus()) {
				case havingLunch:
					result.add(CurrentActivity.eating.toDomainValue());
					break;
				case in:
					result.add(CurrentActivity.working.toDomainValue());
					result.add(CurrentActivity.meeting.toDomainValue());
					break;
				case out:
					result.add(CurrentActivity.sleeping.toDomainValue());
					result.add(CurrentActivity.parking.toDomainValue());
					break;
				default:
					break;
				}
				return result;
			}
		}
		return super.getDynamicDomainValues(attributeName, bean);
	}

}
