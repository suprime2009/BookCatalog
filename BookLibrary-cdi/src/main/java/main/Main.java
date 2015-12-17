package main;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;

public class Main {
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("maintest");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Author author = em.find(Author.class, "34485143-5663-4594-8643-414427370b04");
        System.out.println(author);
        Set<Author> set = new HashSet<Author>();
        set.add(author);
        
        System.out.println("AUTHOR DONE");
        
        Book book = new Book("Some", "123q", "12Some", null, set);
        em.persist(book);
        
        System.out.println("BOOK DONE");
        
        book = em.find(Book.class, book.getIdbook());
        System.out.println(book);
        
       
      
        System.out.println("After");
        
        em.getTransaction().commit();

        em.close();
        emf.close();
		
	}

	       


}
