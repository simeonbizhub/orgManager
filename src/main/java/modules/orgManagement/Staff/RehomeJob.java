package modules.orgManagement.Staff;

import java.util.List;

import org.skyve.CORE;
import org.skyve.domain.types.DateTime;
import org.skyve.job.Job;
import org.skyve.metadata.SortDirection;
import org.skyve.persistence.DocumentQuery;
import org.skyve.util.Binder;

import modules.orgManagement.domain.Office;
import modules.orgManagement.domain.Staff;
import modules.orgManagement.domain.Staff.Status;

public class RehomeJob extends Job {

	private static final int PAGESIZE = 50;

	@Override
	public void execute() throws Exception {
		// Get all staff who are based in the office and are not currently in the office
		List<String> log = getLog();
		log.add("Rehoming job has commenced at " + new DateTime());

		int pageStart = 0;
		boolean finished = false;
		while (!finished) {
			DocumentQuery q = CORE.getPersistence().newDocumentQuery(Staff.MODULE_NAME, Staff.DOCUMENT_NAME);
			q.getFilter().addNotNull(Staff.baseOfficePropertyName);
			q.getFilter().addNullOrNotEquals(Staff.statusPropertyName, Status.in);
			q.getFilter().addNotNull(
					Binder.createCompoundBinding(Staff.baseOfficePropertyName, Office.boundaryPropertyName));
			q.addBoundOrdering(Staff.codePropertyName, SortDirection.ascending);
			q.setFirstResult(pageStart);
			q.setMaxResults(PAGESIZE);
			List<StaffExtension> staffToRehome = q.beanResults();

			int total = staffToRehome.size();
			int successful = 0;
			int unsuccessful = 0;

			for (StaffExtension s : staffToRehome) {
				try {
					s.home();
					s = CORE.getPersistence().save(s);
					log.add(s.getName() + " was rehomed successfully");
					successful++;
				} catch (Exception e) {
					unsuccessful++;
					log.add(s.getName() + " failed rehoming");
				}
				setPercentComplete((successful + unsuccessful), total);
			}
			CORE.getPersistence().commit(false);
			CORE.getPersistence().begin();
			pageStart += PAGESIZE;
			finished = (total < PAGESIZE);
		}
		log.add("Rehoming job has finished successfully at " + new DateTime());
	}
}
