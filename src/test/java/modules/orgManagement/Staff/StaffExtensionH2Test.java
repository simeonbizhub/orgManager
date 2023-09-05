package modules.orgManagement.Staff;

import java.util.Date;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.skyve.util.Time;

import modules.orgManagement.domain.Staff;
import util.AbstractH2TestForJUnit5;

public class StaffExtensionH2Test extends AbstractH2TestForJUnit5 {
	@Test
	public void testCalculateAgeInYearsNullDOB() {
		// Given
		StaffExtension staff = Staff.newInstance();
		// When
		staff.calculateAgeInYears();
		// Then
		Assert.assertNull(staff.getAgeInYears());
	}

	@Test
	public void testCalculateAgeInYearsValidDOB() {
		// Given
		StaffExtension staff = Staff.newInstance();
		staff.setDateOfBirth(Time.addYearsToNew(new Date(), -5));
		// When
		staff.calculateAgeInYears();
		// Then
		Assert.assertEquals(Integer.valueOf(5), staff.getAgeInYears());
	}
}
