package test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.common.base.Optional;

public class Main {
	
	public static void main(String[] args) {
		EntityManagerFactory emf =Persistence.createEntityManagerFactory("maintest");
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery(
				"SELECT SUM (t.a) FROM t where t = "
				+ "( SELECT COUNT(DISTINCT b) as a  FROM Review r RIGHT JOIN  r.book b group by b having floor(AVG(r.rating)) =4 )  t ");
		
		List<Object[]> list = (List<Object[]>) query.getResultList();
		for (Object o : list) {
			System.out.println(o);
		}
		

		
	      
	     
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
