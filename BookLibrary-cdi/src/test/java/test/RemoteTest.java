package test;

import javax.naming.InitialContext;

import org.jboss.ejb3.embedded.EJB3StandaloneBootstrap;
import org.jboss.ejb3.embedded.EJB3StandaloneDeployer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;



import com.softserveinc.booklibrary.session.persist.home.BookHomeLocal;
import com.softserveinc.booklibrary.session.persist.home.BookHomeRemote;

public class RemoteTest {

	// a boolean to test if the container is running or not
	private static boolean containerRunning = false;

	// the EJB3 deployer
	private EJB3StandaloneDeployer deployer;
	
	private BookHomeLocal bookLocal;
	private BookHomeRemote bookremote;

	private void startupEmbeddedContainer() throws Exception {

		if (!containerRunning) {

			EJB3StandaloneBootstrap.boot(null);

			deployer = EJB3StandaloneBootstrap.createDeployer();
			deployer.getArchivesByResource().add("META-INF/persistence.xml");
			deployer.create();
			deployer.start();

			containerRunning = true;
		}
	}
	
	@Test
	public void test() {
		System.out.println("done");
	}
	
	@BeforeClass
	public void init() throws Exception {

	startupEmbeddedContainer();
	InitialContext initialContext = new InitialContext();
	 bookLocal = (BookHomeLocal) initialContext.lookup("BookHome/local");
	 bookremote = (BookHomeRemote) initialContext.lookup("BookHome/remote");
	}


	@AfterClass
	public void terminate() throws Exception {
		// stop container
		deployer.stop();
		deployer.destroy();

		EJB3StandaloneBootstrap.shutdown();

		containerRunning = false;
	}

}
