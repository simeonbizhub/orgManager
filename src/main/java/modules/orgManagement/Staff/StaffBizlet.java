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

import modules.orgManagement.domain.Staff;
import modules.orgManagement.domain.Staff.CurrentActivity;
import modules.orgManagement.domain.StaffStatusHistory;

public class StaffBizlet extends Bizlet<StaffExtension> {

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
				if (!bean.originalValues().get(Staff.statusPropertyName).toString().equals(bean.getStatus().toString()))
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
