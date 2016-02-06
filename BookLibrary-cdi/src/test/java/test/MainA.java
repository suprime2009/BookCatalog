package test;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.session.persist.home.BookHomeRemote;
import com.softserveinc.booklibrary.session.persist.home.impl.BookHome;

public class MainA {
	
	public BookHomeRemote method() throws NamingException {

		final Properties env = new Properties();
	
//		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
//		env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
//		env.put("jboss.naming.client.ejb.context", true);

		
		try {
			System.out.println("before");
			env.load(this.getClass().getClassLoader().getResourceAsStream("properties/context.properties"));
			System.out.println("done");
	
		} catch (IOException e) {
		
		}
		
		InitialContext remoteContext = new InitialContext(env);
		
		
        
		 



		BookHomeRemote ref = (BookHomeRemote) remoteContext.lookup(
				"java:global/BookLibrary-cdi/BookHome!com.softserveinc.booklibrary.session.persist.home.BookHomeRemote");
		List<Book> list = ref.findAll();
		for (Book b: list) {
			System.out.println(b);
		}
		System.out.println("DDDD");
		return ref;
	}
	
	public static void main(String[] args) {
		MainA t = new MainA();
		BookHomeRemote ref = null;
		
		try {
			ref = t.method();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(BookHomeRemote.class.getName());
		System.out.println(BookHomeRemote.class.getSimpleName());
		System.out.println(BookHomeRemote.class.getTypeName());
		
		
		BookHome bh = new BookHome();
		System.out.println();
		for (Class<?> f: BookHome.class.getInterfaces()) {
			System.out.println(f.getName());
		}


	}

}
