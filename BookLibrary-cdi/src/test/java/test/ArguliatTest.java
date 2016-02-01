package test;


import static org.junit.Assert.*;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.softserveinc.booklibrary.session.persist.home.BookHomeLocal;
import com.softserveinc.booklibrary.session.persist.home.impl.BookHome;



@RunWith(Arquillian.class)
public class ArguliatTest  {
	
	   @Deployment
	    public static Archive<?> createTestArchive() {
	        return ShrinkWrap.create(WebArchive.class, "test.war")
	                .addClasses(BookHomeLocal.class)
	                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
	                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
	                // Deploy our test datasource
	                .addAsWebInfResource("test-ds.xml");
	    }

    @Inject
    private BookHomeLocal bookHome;
    
    @Test
    public void test() {
    	String hello = "hello";
    	assertEquals(hello, "hello");
    }
	
	

}
