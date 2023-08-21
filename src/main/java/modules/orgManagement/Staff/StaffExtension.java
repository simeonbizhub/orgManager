package modules.orgManagement.Staff;

import java.time.LocalDate;
import java.time.Period;

import modules.orgManagement.domain.Staff;

public class StaffExtension extends Staff {

	private static final long serialVersionUID = 1L;

	/**
	 *  Sets age in years
	 */
	public void calculateAgeInYears() {
		// Obtain DOB
		if (this.getDateOfBirth()!= null)
		{
			LocalDate dob = this.getDateOfBirth().toLocalDate();
			// Calculate difference
			Period age = Period.between(dob, LocalDate.now());
			// Convert to years
			this.setAgeInYears(age.getYears());
		}
	}
	
	public void calculateStatusHistoryCount() {
		this.setStatusHistoryCount(this.getStatusHistory().size());
	}
}
