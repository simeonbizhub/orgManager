package modules.orgManagement.Office;

import org.skyve.metadata.model.document.Bizlet;
import org.skyve.util.Util;

import modules.orgManagement.Staff.StaffExtension;
import modules.orgManagement.domain.Office;

public class OfficeBizlet extends Bizlet<Office> {

	@Override
	public void preSave(Office bean) throws Exception {
		// TODO Auto-generated method stub
		for (StaffExtension staff : bean.getOfficeStaff())
		{
			Util.LOGGER.info(staff.getName());
		}
		
		super.preSave(bean);
	}

}
