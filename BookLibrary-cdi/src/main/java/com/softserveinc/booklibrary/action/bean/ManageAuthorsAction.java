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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.action.util.AuthorUIWrapper;
import com.softserveinc.booklibrary.action.util.DataTableHelper;
import com.softserveinc.booklibrary.exception.AuthorManagerException;
import com.softserveinc.booklibrary.exception.BookCatalogException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.session.manager.AuthorManagerLocal;
import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeLocal;
import com.softserveinc.booklibrary.session.util.holders.AuthorFieldHolder;
import com.softserveinc.booklibrary.session.util.holders.BookFieldHolder;
import com.softserveinc.booklibrary.session.util.holders.EntityFieldHolder;

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

	@EJB
	private AuthorManagerLocal authorManager;

	@EJB
	private AuthorFacadeLocal authorFacade;

	public ManageAuthorsAction() {
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

	public void submitAddAuthor() {
		System.out.println("submitAddAuthor" + author);
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

	public void reset() {
		author = new Author();
	}

	public String submitAddAndEditAuthor() {
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

	@Override
	public void deleteEntity() {
		try {
			authorManager.deleteAuthorWithNoBooks(getIdEntityToDelete());
			load();
		} catch (AuthorManagerException e) {
			System.out.println(e.getMessage());

		}

	}

	@Override
	public void deleteListEntities() {
		System.out.println("deleteListEntities");
		List<Author> list = new ArrayList<Author>();
		for (AuthorUIWrapper w : getListEntitiesToDelete()) {
			list.add(w.getAuthor());
		}
		try {
			authorManager.bulkDeleteAuthorsWithNoBooks(list);
		} catch (AuthorManagerException e) {
			System.out.println(e.getMessage());
		}
		refreshPage();

	}

	@Override
	public void getEntitiesForCurrentPage() {
		System.out.println("Method starts. getEntitiesForCurrentPage");
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
		System.out.println("Method done. getEntitiesForCurrentPage");

	}

	@Override
	public int getCountEntitiesForCurrentRequirements() {
		System.out.println("Method start getCountEntitiesForCurrentRequirements");
		int count = authorFacade.findCountAuthorsForDataTable(getCurrentRequirementsForDataTable());
		System.out.println(count);
		System.out.println(count);

		System.out.println("Method done getCountEntitiesForCurrentRequirements");
		return count;
	}

	@Override
	public EntityFieldHolder getFieldHolderForColumn(String column) {
		return AuthorFieldHolder.valueOf(column);
	}

	@Override
	public EntityFieldHolder[] getEntityConstantInstances() {
		return AuthorFieldHolder.values();
	}
	
	public boolean checkIfAuthorsToDeleteHaveBooks() {
		if (getListEntitiesToDelete() != null) {
		for (AuthorUIWrapper a: getListEntitiesToDelete()) {
			if (a.getCountBooks() > 0) {
				return true;
			}
		}
		}
		return false;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	@Override
	public void deleteEmptyFilterValues() {
		for (Iterator<Map.Entry<EntityFieldHolder, String>> it = getFilterValues().entrySet().iterator(); it
				.hasNext();) {
			Map.Entry<EntityFieldHolder, String> entry = it.next();
			if (entry.getKey().equals(AuthorFieldHolder.AVE_RATING) && entry.getValue().equals("0")) {
				it.remove();
			}
			if (entry.getValue().equals("") || entry.getValue().startsWith(" ")) {
				it.remove();
			}
		}

	}

}
