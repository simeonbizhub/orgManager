package modules.orgManagement.Staff.actions;

import modules.orgManagement.Staff.StaffExtension;
import modules.orgManagement.domain.Staff;
import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractActionTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractActionTest} to create your own tests for this action.
 */
public class SendHomeTest extends AbstractActionTest<StaffExtension, SendHome> {

	@Override
	protected SendHome getAction() {
		return new SendHome();
	}

	@Override
	protected StaffExtension getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(Staff.MODULE_NAME, Staff.DOCUMENT_NAME);
	}
}