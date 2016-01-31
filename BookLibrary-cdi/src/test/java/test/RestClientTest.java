package test;


import org.testng.annotations.Test;
import static org.junit.Assert.*;

import com.softserveinc.booklibrary.rest.dto.AuthorDTO;
import com.softserveinc.model.util.BaseTest;
import com.softserveinc.model.util.DataBaseConstants;

public class RestClientTest extends BaseTest implements DataBaseConstants {
	
	@Test
	public void test() {
		AuthorDTO dto = new AuthorDTO(null, "Pavlo", "Kravets");
		boolean result = authorClientRemote.createAuthor(dto);
		assertTrue(result);
	}
	

}
