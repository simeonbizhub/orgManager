package modules.orgManagement.Staff;

import java.time.LocalDate;
import java.time.Period;

import modules.orgManagement.domain.Office;
import modules.orgManagement.domain.Staff;

public class StaffExtension extends Staff {

	private static final long serialVersionUID = 1L;

	/**
	 * Sets age in years
	 */
	public void calculateAgeInYears() {
		// Obtain DOB
		if (this.getDateOfBirth() != null) {
			LocalDate dob = this.getDateOfBirth().toLocalDate();
			// Calculate difference
			Period age = Period.between(dob, LocalDate.now());
			// Convert to years
			this.setAgeInYears(age.getYears());
		}
	}

	/**
	 * This method puts the location of this staff to the center of their base office.
	 */
	public void home() {
		Office myOffice = this.getBaseOffice();
		if (myOffice != null && myOffice.getBoundary() != null) {
			this.setLocation(myOffice.getBoundary().getCentroid());
		}
	}
	
	public boolean showSendHomeButton() {
		Office myOffice = this.getBaseOffice();
		return (myOffice != null && myOffice.getBoundary() != null);
	}

	public void calculateStatusHistoryCount() {
		this.setStatusHistoryCount(this.getStatusHistory().size());
	}
}
