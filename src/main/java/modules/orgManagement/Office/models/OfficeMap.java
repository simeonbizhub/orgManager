package modules.orgManagement.Office.models;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Geometry;
import org.skyve.CORE;
import org.skyve.metadata.view.model.map.MapFeature;
import org.skyve.metadata.view.model.map.MapItem;
import org.skyve.metadata.view.model.map.MapModel;
import org.skyve.metadata.view.model.map.MapResult;
import org.skyve.persistence.DocumentQuery;
import org.skyve.util.Util;

import modules.orgManagement.Staff.StaffExtension;
import modules.orgManagement.domain.Office;
import modules.orgManagement.domain.Staff;

public class OfficeMap extends MapModel<Office> {

	/**
	 * This method returns a map showing the office boundary and its staff
	 */
	@Override
	public MapResult getResult(Geometry mapBounds) throws Exception {
		List<MapItem> mapItems = new ArrayList<>();
		Office office = getBean();
		// create query
		DocumentQuery qOfficeStaff = CORE.getPersistence().newDocumentQuery(Staff.MODULE_NAME, Staff.DOCUMENT_NAME);
		qOfficeStaff.getFilter().addEquals(Staff.baseOfficePropertyName, office);
		qOfficeStaff.getFilter().addNotNull(Staff.locationPropertyName);
		
		// list of staff in office
		List<StaffExtension> officeStaff = qOfficeStaff.beanResults();
		if (office.getBoundary() != null) {
			// create map item for office boundary
			MapItem officeBoundaryItem = new MapItem();
			officeBoundaryItem.setBizId(office.getBizId());
			officeBoundaryItem.setDocumentName(Office.DOCUMENT_NAME);
			officeBoundaryItem.setModuleName(Office.MODULE_NAME);
			officeBoundaryItem.setInfoMarkup(office.getBizKey());
			// create map feature
			MapFeature boundaryFeature = new MapFeature();
			boundaryFeature.setGeometry(office.getBoundary());
			boundaryFeature.setFillColour("#FFF000");
			boundaryFeature.setFillOpacity(0.5f);
			boundaryFeature.setStrokeColour("");
			officeBoundaryItem.getFeatures().add(boundaryFeature);
			// add to list
			mapItems.add(officeBoundaryItem);
		}
		
		// add staff locations
		for (StaffExtension staff : officeStaff) {
			// create map item
			MapItem staffItem = new MapItem();
			staffItem.setBizId(staff.getBizId());
			staffItem.setDocumentName(Staff.DOCUMENT_NAME);
			staffItem.setModuleName(Staff.MODULE_NAME);
			// create info markup
			StringBuilder sb = new StringBuilder(64);
			sb.append("<table>");
			sb.append("<tr>");
			sb.append("<td>");
			String imageURL = Util.getContentImageUrl(Staff.MODULE_NAME, Staff.DOCUMENT_NAME, Staff.imagePropertyName, staff.getImage(), 64, 64);
			sb.append(imageURL);
			sb.append("</td>");
			sb.append("<td>");
			sb.append("<strong>Code:</strong> ").append(staff.getCode());
			sb.append("<br/>");
			sb.append("<strong>Name:</strong> ").append(staff.getName());
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("</table>");
			
			staffItem.setInfoMarkup(sb.toString());
			// create feature
			MapFeature staffFeature = new MapFeature();
			staffFeature.setGeometry(staff.getLocation());
			staffItem.getFeatures().add(staffFeature);
			// add to list
			mapItems.add(staffItem);
		}
		return new MapResult(mapItems, null);
	}

}
