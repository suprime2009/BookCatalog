package com.softserveinc.action.managebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.action.util.DataTableHelper;
import com.softserveinc.exception.AuthorManagerException;
import com.softserveinc.exception.BookCatalogException;
import com.softserveinc.exception.BookManagerException;
import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.AuthorFieldHolder;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.EntityFieldHolder;
import com.softserveinc.model.persist.facade.AuthorFacadeLocal;
import com.softserveinc.model.session.manager.AuthorManagerLocal;



@ManagedBean
@SessionScoped
public class ManageAuthorsAction extends DataTableHelper<AuthorUIWrapper> implements Serializable {
	
	private static Logger log = LoggerFactory.getLogger(ManageAuthorsAction.class);
	
	private Author author;
	
	
	@EJB
	private AuthorManagerLocal authorManager;
	
	@EJB
	private AuthorFacadeLocal authorFacade;
	
	public ManageAuthorsAction() {
		author = new Author();
	}
	
	public void submitAddAuthor() {
		System.out.println("submitAddAuthor" + author);
		try {
			authorManager.createAuthor(author);
		} catch (AuthorManagerException e) {
			
		} catch (BookCatalogException e) {
			// TODO: handle exception
		}
		reset();
		System.out.println("submitAddAuthor");
	
	}
	
	public void reset() {
		author = new Author();
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
		for (AuthorUIWrapper w: getListEntitiesToDelete()) {
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



	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	
}
