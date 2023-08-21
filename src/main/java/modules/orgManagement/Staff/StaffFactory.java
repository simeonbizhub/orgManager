package modules.orgManagement.Staff;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.skyve.domain.types.DateOnly;
import org.skyve.util.DataBuilder;
import org.skyve.util.test.DataMap;
import org.skyve.util.test.SkyveFactory;
import org.skyve.util.test.SkyveFixture;
import org.skyve.util.test.SkyveFixture.FixtureType;

import modules.admin.domain.Contact;
import modules.orgManagement.domain.Staff;

@SkyveFactory(value = {
		@DataMap(attributeName = Contact.namePropertyName, fileName = "personName.txt")
		
})

public class StaffFactory {
	@SkyveFixture(types = FixtureType.crud)
	public static Staff crudInstance() throws Exception {
		Staff staff = new DataBuilder().build(Staff.MODULE_NAME, Staff.DOCUMENT_NAME);
		// 18-50 years old generation
		Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        Calendar minDate = (Calendar) calendar.clone();
        minDate.add(Calendar.YEAR, -50);

        Calendar maxDate = (Calendar) calendar.clone();
        maxDate.add(Calendar.YEAR, -18);
        
        Date randomDateOfBirth = generateRandomDate(minDate.getTime(), maxDate.getTime());
        
		// convert to DateOnly
		DateOnly dob = new DateOnly(randomDateOfBirth);
		
		// set DoB
		staff.setDateOfBirth(dob);
		
		return staff;
	}
	
	private static Date generateRandomDate(Date minDate, Date maxDate) {
        long minMillis = minDate.getTime();
        long maxMillis = maxDate.getTime();

        Random random = new Random();
        long randomMillis = minMillis + (long) (random.nextDouble() * (maxMillis - minMillis));

        return new Date(randomMillis);
    }
}
