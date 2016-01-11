package test;

import java.text.Format;
import java.util.Formatter;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.common.base.Optional;
import com.softserveinc.action.managebook.ISNBValidatorController;
import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.facade.BookFacade;
import com.softserveinc.model.persist.home.BookHome;
import com.softserveinc.model.session.util.SQLCommandConstants;

public class Main implements SQLCommandConstants{
	
	public static void main(String[] args) {
		
//		ISNBValidatorController ob = new ISNBValidatorController();
//		 ob.validate(null, null, "ISBN-13: 978-9-596-52068-7");
//		 
//		 
//		 String r = "[03]";
//		 Pattern p = Pattern.compile(r);
//		 String str = "2";
//		 Matcher matcher = p.matcher(str);
//		 System.out.println(matcher.matches());
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("maintest");
		EntityManager em = emf.createEntityManager();
		Book book = em.find(Book.class, "b19");
		
		String quer = "SELECT COUNT(DISTINCT r) FROM Review r JOIN r.book b WHERE r.book like :par";
		StringBuilder sbForDataTable = new StringBuilder();
		sbForDataTable.append(SELECT).append(COUNT).append("( DISTINCT " + R + ") ").append(FROM);
		sbForDataTable.append(Book.class.getName()).append(' ').append(B);
		sbForDataTable.append(JOIN).append(R).append('.').append("book ");
		sbForDataTable.append(WHERE).append(book).append(" = ").append("r.book");
		Query query = em.createQuery(quer);
		query.setParameter("par", book);
		long g = (long) query.getSingleResult();
		System.out.println(g);
		
		



		}
		

		
	      
	   
	
	public Integer sum(Optional<Integer> a, Optional<Integer> b) {
	      //Optional.isPresent - checks the value is present or not
	      System.out.println("First parameter is present: " + a.isPresent());

	      System.out.println("Second parameter is present: " + b.isPresent());

	      //Optional.or - returns the value if present otherwise returns
	      //the default value passed.
	      Integer value1 = a.or(new Integer(0));	

	      //Optional.get - gets the value, value should be present
	      Integer value2 = b.get();

	      return value1 + value2;
	   }	

}
