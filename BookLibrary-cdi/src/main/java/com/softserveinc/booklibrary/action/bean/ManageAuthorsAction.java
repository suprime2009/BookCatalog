package com.softserveinc.booklibrary.action.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.action.helper.AuthorUIWrapper;
import com.softserveinc.booklibrary.action.helper.DataTableHelper;
import com.softserveinc.booklibrary.exception.AuthorManagerException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.session.manager.AuthorManagerLocal;
import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeLocal;
import com.softserveinc.booklibrary.session.util.holders.AuthorFieldHolder;
import com.softserveinc.booklibrary.session.util.holders.EntityFieldHolder;

/**
 * This class is a action bean for UI page manageAuthors. Class extended from
 * DataTableHelper, which provides operations with dataTable: pagination,
 * filtering, sorting. Class used for loading Authors for dataTable in according
 * to current UI requirements, removing and bulk removing Authors. Also class is
 * a action for create author operation.
 *
 */
@ManagedBean
@SessionScoped
public class ManageAuthorsAction extends DataTableHelper<AuthorUIWrapper> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1899235454492705243L;

	private static Logger log = LoggerFactory.getLogger(ManageAuthorsAction.class);

	// author to create
	private Author author;

	private Author authorToEdit;

	@EJB
	private AuthorManagerLocal authorManager;

	@EJB
	private AuthorFacadeLocal authorFacade;

	public ManageAuthorsAction() {
		author = new Author();
	}

	/**
	 * Method runs on add action. Method pass {@link Author} instance to
	 * {@link AuthorManager} for creation a new Author instance. After method
	 * done on manager and new instance is created, method shown message on UI
	 * about it.
	 * 
	 */
	public void addAuthor() {
		try {
			authorManager.createAuthor(author);
			reset();
			showGlobalMessageOnPage("Author has been successfully created.");
			load();
			log.debug("Author instance has been created.");
		} catch (AuthorManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
			log.error(e.getMessage());
		}

	}

	/**
	 * Method runs on add and edit action. Method pass {@link Author} instance
	 * to {@link AuthorManager} for creation a new Author instance. After method
	 * done on manager and new instance is created, method shown message on UI
	 * about it.
	 * 
	 * @return If Author instance has been created return URL for authorDetail
	 *         page for editing this book.
	 */
	public String addAndEditAuthor() {
		try {
			authorManager.createAuthor(author);
			String idAuthor = author.getIdAuthor();
			reset();
			showGlobalMessageOnPage("Author has been successfully created.");
			load();
			log.debug("Author instance has been created.");
			return "authorDetail.xhtml?faces-redirect=true&amp;id=" + idAuthor;
		} catch (AuthorManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Method runs on action clean. Method cleans all inputed values in form.
	 */
	public void reset() {
		author = new Author();
	}

	/**
	 * The method shows the global messages on UI. The method gets the text of
	 * message through parameter.
	 * 
	 * @param message
	 *            String
	 */
	private void showGlobalMessageOnPage(String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage(message);
		context.addMessage(null, facesMessage);
	}

	@Override
	public void deleteEntity() {
		try {
			authorManager.deleteAuthorWithNoBooks(getIdEntityToDelete());
			load();
			log.debug("The method done. Author has been removed.");
		} catch (AuthorManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
			log.error(e.getMessage());
		}
	}

	@Override
	public void deleteListEntities() {
		List<Author> list = new ArrayList<Author>();
		for (AuthorUIWrapper w : getListEntitiesToDelete()) {
			list.add(w.getAuthor());
		}
		try {
			authorManager.bulkDeleteAuthorsWithNoBooks(list);
			refreshPage();
			log.debug("The method done. Authors have been removed.");
		} catch (AuthorManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
			log.error(e.getMessage());
		}
	}

	@Override
	public void getEntitiesForCurrentPage() {
		List<Object[]> list = authorFacade.findAuthorsForDataTable(getCurrentRequirementsForDataTable());
		cleanListEntities();
		for (Object[] o : list) {
			Author author = (Author) o[0];
			int countBooks = (int) (long) o[1];
			int countReviews = (int) (long) o[2];
			Double aveRating = (Double) o[3];
			if (aveRating == null) {
				aveRating = 0.0;
			}
			AuthorUIWrapper wrapp = new AuthorUIWrapper(author, countBooks, countReviews, aveRating);
			getEntities().add(wrapp);
		}
		log.debug("The method done.");
	}

	@Override
	public int getCountEntitiesForCurrentRequirements() {
		int count = authorFacade.findCountAuthorsForDataTable(getCurrentRequirementsForDataTable());
		log.debug("The method done. Has been found {} authors", count);
		return count;
	}

	@Override
	public void deleteEmptyFilterValues() {
		for (Iterator<Map.Entry<EntityFieldHolder, String>> it = getFilterValues().entrySet().iterator(); it
				.hasNext();) {
			Map.Entry<EntityFieldHolder, String> entry = it.next();
			if (entry.getKey().equals(AuthorFieldHolder.AVE_RATING) && entry.getValue().equals("0")) {
				it.remove();
			}
			if (StringUtils.isBlank(entry.getValue())) {
				it.remove();
			}
		}

	}

	@Override
	public EntityFieldHolder getFieldHolderForColumn(String column) {
		return AuthorFieldHolder.valueOf(column);
	}

	@Override
	public EntityFieldHolder[] getEntityConstantInstances() {
		return AuthorFieldHolder.values();
	}

	public void loadAuthorToEdit(String idAuthor) {
		authorToEdit = authorFacade.findById(idAuthor);
	}

	public void submitEditAuthor() {
		try {
			authorManager.updateAuthor(authorToEdit);
			load();
			log.debug("The method done. Author has been updated.");
		} catch (AuthorManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
			log.error(e.getMessage());
		}
	}

	/**
	 * This method used for disable status submit button for delete selected
	 * action. If one or more authors from selected have book, method will
	 * return true, and button will be disable.
	 * 
	 * @return boolean
	 */
	public boolean checkIfAuthorsToDeleteHaveBooks() {
		if (getListEntitiesToDelete() != null) {
			for (AuthorUIWrapper a : getListEntitiesToDelete()) {
				if (a.getCountBooks() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	// getters and setters
	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Author getAuthorToEdit() {
		return authorToEdit;
	}

	public void setAuthorToEdit(Author authorToEdit) {
		this.authorToEdit = authorToEdit;
	}
	
	

}
