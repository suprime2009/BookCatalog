package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.softserveinc.booklibrary.session.persist.home.BookHomeRemote;
import com.softserveinc.booklibrary.session.persist.home.impl.BookHome;

public class EJBTester {

	BufferedReader brConsoleReader = null;
	Properties props;
	static InitialContext ctx;

	public static void main(String[] args) {

		EJBTester ejbTester = new EJBTester();

		  Properties jndiProps = new Properties();
		    jndiProps.put(Context.INITIAL_CONTEXT_FACTORY,         
		    "org.jboss.naming.remote.client.InitialContextFactory");
		    jndiProps.put(Context.PROVIDER_URL,"remote://localhost:4447");
		    jndiProps.put(Context.SECURITY_PRINCIPAL, "testuser");
		    jndiProps.put(Context.SECURITY_CREDENTIALS, "testpassword");
		    jndiProps.put("jboss.naming.client.ejb.context", true);
		  


		Context context = null;
		try {
			context = new InitialContext(jndiProps);
			System.out.println(context);
			System.out.println("done");
			final String appName = "BookLibrary-cdi";
			final String moduleName = "";
			final String distinctName = "";
			final String beanName = BookHome.class.getSimpleName();
			final String viewClassName = BookHomeRemote.class.getName();
			Object r =  context.lookup("ejb:" + appName + "/"  + beanName + "!" + viewClassName);
			BookHomeRemote remote = (BookHomeRemote) r;
			System.out.println(remote);
			System.out.println(remote.findAll());
			System.out.println("done");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}

	}

}
