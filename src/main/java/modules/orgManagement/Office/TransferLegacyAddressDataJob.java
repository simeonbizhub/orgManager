package modules.orgManagement.Office;

import java.util.List;

import org.skyve.CORE;
import org.skyve.domain.types.DateTime;
import org.skyve.job.Job;
import org.skyve.metadata.SortDirection;
import org.skyve.persistence.DocumentQuery;
import org.springframework.beans.BeanUtils;

import modules.orgManagement.domain.Address;
import modules.orgManagement.domain.Office;

public class TransferLegacyAddressDataJob extends Job {

	private static final int PAGESIZE = 50;

	@Override
	public void execute() throws Exception {
		List<String> log = getLog();
		log.add("Transfer Legacy Address Data job has commenced at " + new DateTime());

		int pageStart = 0;
		boolean finished = false;
		while (!finished) {
			// Load all offices
			DocumentQuery qOfficesQuery = CORE.getPersistence().newDocumentQuery(Office.MODULE_NAME,
					Office.DOCUMENT_NAME);
			qOfficesQuery.addBoundOrdering(Office.buildingNamePropertyName, SortDirection.ascending);
			qOfficesQuery.setFirstResult(pageStart);
			qOfficesQuery.setMaxResults(PAGESIZE);

			List<Office> offices = qOfficesQuery.beanResults();

			int total = offices.size();
			int successful = 0;
			int unsuccessful = 0;

			// Get the address data from all loaded offices
			for (Office o : offices) {
				try {
					// Populate the Address table with the retrieved addresses
					Address address = constructAddress(o);
					
					// Create relevant association to each address bean for legal addresses
					o.setLegalAddress(address);
					
					// Save the office objects to db
					o = CORE.getPersistence().save(o);
					log.add(o.getBizKey() + " was transferred successfully");
					successful++;
				} catch (Exception e) {
					unsuccessful++;
					log.add(o.getBizKey() + " failed to transfer address data");
				}
				setPercentComplete((successful + unsuccessful), total);
			}
			CORE.getPersistence().commit(false);
			CORE.getPersistence().begin();
			pageStart += PAGESIZE;
			finished = (total < PAGESIZE);
		}
		log.add("Transferring Address Data job has finished successfully at " + new DateTime());
	}

	private Address constructAddress(Office o) {
		Address address = null;
		if (o.getBuildingName() != null || o.getLevelUnit() != null || o.getStreetAddress() != null
				|| o.getSuburb() != null || o.getPostCode() != null) {

			address = Address.newInstance();
			address.setBuildingName(o.getBuildingName());
			address.setLevelUnit(o.getLevelUnit());
			address.setStreetAddress(o.getStreetAddress());
			address.setSuburb(o.getSuburb());
			address.setPostCode(o.getPostCode());
		}

		/**
		 * This method below would be used to copy properties over and it doesn't copy
		 * null values
		 */
//		BeanUtils.copyProperties(o, address);
		return address;

	}

}
