package test;

import java.text.Format;
import java.util.Formatter;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.common.base.Optional;
import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;

public class Main {
	
	public static void main(String[] args) {
//		EntityManagerFactory emf =Persistence.createEntityManagerFactory("maintest");
//		EntityManager em = emf.createEntityManager();
//		
////		Query query = em.createQuery(
////				"SELECT SUM (t.a) FROM t where t = "
////				+ "( SELECT COUNT(DISTINCT b) as a  FROM Review r RIGHT JOIN  r.book b group by b having floor(AVG(r.rating)) =4 )  t ");
//		
//		Query query = em.createQuery("SELECT  b,  AVG( r.rating) AS rat  FROM com.softserveinc.model.persist.entity.Review r  RIGHT JOIN  r.book  b  LEFT JOIN  b.authors a"
//				+ " WHERE  ( a.secondName  LIKE 'sa%'  OR  a.firstName  LIKE 'sa%')  GROUP BY  b ORDER BY  b.publisher DESC ,  b.createdDate  DESC ");
//		
//		List<Object[]> result = query.getResultList();
//		for (Object[] o : result) {
//			Book book = (Book) o[0];
//			System.out.println(book.getBookName());
//			System.out.println();
//			
//			for (Author a: book.getAuthors()) {
//				System.out.println(a.getSecondName());
//			}
		
		StringBuilder sb = new StringBuilder();
		
		String [] a ={"a", "b"};
		
		

		Formatter fm = new Formatter();
		String s  = String.format("%s. is  years  old, er, young", "as");
		System.out.println(s);
			
		String g = "gogo %s is %s good";
		g = g.format("Ivan", "Rob");
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
