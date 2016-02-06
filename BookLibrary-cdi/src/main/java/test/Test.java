package test;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.dbunit.ext.mysql.MySqlConnection;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.session.persist.home.BookHomeRemote;
import com.softserveinc.booklibrary.session.persist.home.impl.BookHome;

public class Test {

	Hashtable jndiProps;

	Test() {
		jndiProps = new Hashtable();
		jndiProps.put("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
		jndiProps.put(InitialContext.PROVIDER_URL, "remote://localhost:4447");
		jndiProps.put("jboss.naming.client.ejb.context", true);

		jndiProps.put(Context.SECURITY_PRINCIPAL, "admin");
		jndiProps.put(Context.SECURITY_CREDENTIALS, "admin");

	}

	public void doIt() {
		System.out.println("Lookup name=");
		Context ctx = null;
		try {
			ctx = new InitialContext(jndiProps);
			System.out.println(ctx);
			Object ref = ctx.lookup(
					"BookLibrary-cdi/BookHome!com.softserveinc.booklibrary.session.persist.home.BookHomeRemote");
			System.out.println("...Successful");
		} catch (NamingException e) {
			System.out.println("...Failed");
			// System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
				}
			}
		}

	}

	public static void main(String[] args) {
		Test t = new Test();
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

	public BookHomeRemote method() throws NamingException {

		final Properties env = new Properties();
	
//		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
//		env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
//		env.put("jboss.naming.client.ejb.context", true);

		
		try {
			System.out.println("before");
			env.load(this.getClass().getClassLoader().getResourceAsStream("scr/test/resources/properties/context.properties"));
			System.out.println("done");
	
		} catch (IOException e) {
		
		}
		
		InitialContext remoteContext = new InitialContext(env);
		
		
        
		 



		BookHomeRemote ref = (BookHomeRemote) remoteContext.lookup(
				"BookLibrary-cdi/BookHome!com.softserveinc.booklibrary.session.persist.home.BookHomeRemote");
		List<Book> list = ref.findAll();
		for (Book b: list) {
			System.out.println(b);
		}
		System.out.println("DDDD");
		return ref;
	}

}
