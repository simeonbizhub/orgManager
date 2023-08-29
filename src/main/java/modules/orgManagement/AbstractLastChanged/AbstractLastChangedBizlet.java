package modules.orgManagement.AbstractLastChanged;

import org.skyve.domain.types.DateTime;
import org.skyve.metadata.model.document.Bizlet;

import modules.orgManagement.domain.AbstractLastChanged;

public abstract class AbstractLastChangedBizlet<T extends AbstractLastChanged> extends Bizlet<T> {

	@Override
	public void preSave(T bean) throws Exception {
		bean.setLastChangedDateTime(new DateTime());
		super.preSave(bean);
	}
	
}
