package com.softserveinc.booklibrary.session.manager;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ajax4jsf.model.SequenceRange;
import org.richfaces.component.SortOrder;
import org.richfaces.model.ArrangeableState;

import com.softserveinc.booklibrary.action.helper.DataTableSearchHolder;

/**
 * BookManagerLocal an interface extended from IBookManager interface and describes business operations
 * for Book entity. This interface is local and used in application as main.
 *
 */
@Local
public interface BookManagerLocal extends IManagerBook{
	
}
