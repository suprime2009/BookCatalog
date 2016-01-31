package test;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.common.base.Optional;
import com.softserveinc.booklibrary.session.util.SQLCommandConstants;


public class Main implements SQLCommandConstants{
	
	public static void main(String[] args) throws IOException {
		
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

		
		String q = "SELECT COUNT(b) FROM Book b WHERE b.idBook IN (SELECT b.idBook FROM Review r RIGHT JOIN r.book  b  GROUP BY  b HAVING  FLOOR(AVG( r.rating))  = 3)";
		Query query = em.createQuery(q);
		long a = (long) query.getSingleResult();
		System.out.println(a);

//		String qu = "SELECT a, COUNT(distinct b), COUNT(distinct r), AVG(r.rating) from Author a left join a.books b left join b.reviews r "
//				+ " group by a having COUNT(distinct b) >5";
//		Query query = em.createQuery(qu);
//		List<Object[]> result = query.getResultList();
//		for (Object[] o : result) {
//			System.out.print(o[0]);
//			System.out.print(" ");
//			System.out.print(o[1]);
//			System.out.print(" ");
//			System.out.print(o[2]);
//			System.out.print(" ");
//			System.out.println(o[3]);
//			
//
//		}
//		System.out.println("done");
//		
//		
//		List<Integer> list1 = new ArrayList<Integer>();
//		list1.add(1);
//		list1.add(2);
//		list1.add(3);
//		
//		List<Integer> list2 = new ArrayList<Integer>();
//		list2.add(1);
//		list2.add(2);
//		list2.add(2);
//		list2.add(4);
//		
//		List<Integer> list3 = ListUtils.subtract(list1, list2);
//		
//		System.out.println(list3.size());
//		for (int i : list3) {
//			System.out.println(i);
//		}
//		Book book = em.find(Book.class, "b38");
//		System.out.println(book);
//		ObjectWriter ow = new ObjectMapper().writer().forType(Book.class);
//		String json = ow.writeValueAsString(book);
//		System.out.println(json);
//		
//		
//		ObjectReader or = new ObjectMapper().reader().forType(Book.class);
//	
//		Book book1 = or.readValue(json);
//		System.out.println(book1);
		

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
