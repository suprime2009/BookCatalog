package com.softserveinc.booklibrary.test.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.DataProvider;

import com.google.common.collect.Lists;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;

public class ManagerTestUlil {

	private static final String CHRIS = "Chris";
	private static final String DEVIS = "Devis";
	
	private static final String BOOK_NAME_CORRECT = "Some book";
	private static final String ISBN_CORRECT = "ISBN-13: 978-1-236-04519-4";
	private static final String PUBLISHER_CORRECT = "Some publisher";
	private static final Integer YEAR_CORRECT = 2015;
	public static final String LESS_TWO_CHARACTERS = "M";
	public static final String MORE_EIGHTY_CHARACTERS = appendCharacters(80);
	private static final String NOT_PRESENT_ID = "SomeNotPresentID";

	@DataProvider(name = "negativeCreateBookDataProvider")
	public static Object[][] negativeCreateBookDataProvider() {
		List<Object[]> list = Lists.newArrayList();
		list.addAll(Arrays.asList(negativeBookDataProvider()));

		/* Book with present id number */
		Book book = new Book(BOOK_NAME_CORRECT, ISBN_CORRECT, PUBLISHER_CORRECT, YEAR_CORRECT, new HashSet<Author>());
		book.setIdBook("someID");
		list.add(new Object[] { book });
		/* Book with already present ISBN id DB */
//		 book = new Book(BOOK_NAME_CORRECT, "ISBN-13: 978-1-236-54515-7",
//		 PUBLISHER_CORRECT, YEAR_CORRECT,
//		 new HashSet<Author>());
//		 list.add(new Object[] { book });

		return list.toArray(new Object[list.size()][]);
	}

	@DataProvider(name = "negativeUpdateBookDataProvider")
	public static Book[][] negativeUpdateBookDataProvider() {
		List<Book[]> list = Lists.newArrayList();
		list.addAll(Arrays.asList(negativeBookDataProvider()));

		/* Book with present id number */
		// Book book = new Book(BOOK_NAME_CORRECT, ISBN_CORRECT,
		// PUBLISHER_CORRECT, YEAR_CORRECT, new HashSet<Author>());
		// book.setIdBook("someID");
		// list.add(new Object[] { book });
		/* Book with already present ISBN id DB */
		// book = new Book(BOOK_NAME_CORRECT, "ISBN-10: 1-478-42336-5",
		// PUBLISHER_CORRECT, YEAR_CORRECT,
		// new HashSet<Author>());
		// list.add(new Object[] { book });

		return list.toArray(new Book[list.size()][]);
	}

	@DataProvider(name = "negativeDeleteBookDataProvider")
	public static Object[][] negativeDeleteBookDataProvider() {
		return new Object[][] { { null }, { NOT_PRESENT_ID } };
	}
	
	@DataProvider(name = "negativeDeleteAuthorDataProvider")
	public static Object[][] negativeDeleteAuthorDataProvider() {
		return new Object[][] { { null }, { NOT_PRESENT_ID } };
	}

	public static Book[][] negativeBookDataProvider() {
		Book[][] retObjArr = {
				{ new Book(null, ISBN_CORRECT, PUBLISHER_CORRECT, YEAR_CORRECT, new HashSet<Author>()) },
				{ new Book(null, ISBN_CORRECT, PUBLISHER_CORRECT, YEAR_CORRECT, new HashSet<Author>()) },
				{ new Book(BOOK_NAME_CORRECT, null, PUBLISHER_CORRECT, YEAR_CORRECT, new HashSet<Author>()) },
				{ new Book(BOOK_NAME_CORRECT, ISBN_CORRECT, null, null, new HashSet<Author>()) },
				{ new Book(BOOK_NAME_CORRECT, ISBN_CORRECT, PUBLISHER_CORRECT, 136, new HashSet<Author>()) },
				{ new Book(BOOK_NAME_CORRECT, "ISBN-13: 147-5-856-85695-7", PUBLISHER_CORRECT, null,
						new HashSet<Author>()) },
				{ null },
				{ new Book(LESS_TWO_CHARACTERS, ISBN_CORRECT, PUBLISHER_CORRECT, YEAR_CORRECT, new HashSet<Author>()) },
				{ new Book(BOOK_NAME_CORRECT, ISBN_CORRECT, LESS_TWO_CHARACTERS, YEAR_CORRECT, new HashSet<Author>()) },
				{ new Book(MORE_EIGHTY_CHARACTERS, ISBN_CORRECT, PUBLISHER_CORRECT, YEAR_CORRECT,
						new HashSet<Author>()) },
				{ new Book(BOOK_NAME_CORRECT, ISBN_CORRECT, MORE_EIGHTY_CHARACTERS, YEAR_CORRECT,
						new HashSet<Author>()) },
				{ new Book(BOOK_NAME_CORRECT, ISBN_CORRECT, MORE_EIGHTY_CHARACTERS, YEAR_CORRECT,
						new HashSet<Author>()) },
				{ new Book(BOOK_NAME_CORRECT, ISBN_CORRECT, PUBLISHER_CORRECT, YEAR_CORRECT,
						getSetAuthorsNotPresentInDB()) } };
		return retObjArr;

	}

	public static Author[][] negativeAuthorDataProvider() {
		Author[][] retObjArr = { { new Author(LESS_TWO_CHARACTERS, DEVIS) },
				{ new Author(MORE_EIGHTY_CHARACTERS, DEVIS) },
				{ new Author(CHRIS, LESS_TWO_CHARACTERS) },
				{ new Author(CHRIS, MORE_EIGHTY_CHARACTERS) },
				{ new Author("chris", DEVIS) }, { new Author("hello world", DEVIS) },
				{ new Author(CHRIS, "chris") }, { new Author(CHRIS, "hello world") },
				{ new Author(null, DEVIS) }, { new Author(CHRIS, null) } };
		return (retObjArr);
	}

	@DataProvider(name = "negativeCreateAuthorDataProvider")
	public static Author[][] negativeCreateAuthorDataProvider() {
		List<Author[]> list = Lists.newArrayList();
		list.addAll(Arrays.asList(negativeAuthorDataProvider()));
		Author author = new Author(CHRIS, DEVIS);
		author.setIdAuthor("someId");
		list.add(new Author[] { author });
		
		return list.toArray(new Author[list.size()][]);
	}
	
	@DataProvider(name = "negativeUpdateAuthorDataProvider")
	public static Author[][] negativeUpdateAuthorDataProvider() {
		List<Author[]> list = Lists.newArrayList();
		list.addAll(Arrays.asList(negativeAuthorDataProvider()));
		for (int i=0; i< list.size(); i++) {
			Author author = list.get(i)[0];
			author.setIdAuthor("someID" + i);
		}
		Author author = new Author(CHRIS, DEVIS);
		author.setIdAuthor(null);
		list.add(new Author[] { author });
		
		return list.toArray(new Author[list.size()][]);
	}
	


	private static Set<Author> getSetAuthorsNotPresentInDB() {
		Set<Author> authors = new HashSet<Author>();
		authors.add(new Author("Mister", "Bin"));
		authors.add(new Author("Some", "Author"));
		return authors;
	}

	private static String appendCharacters(int count) {
		StringBuilder sb = new StringBuilder();
		sb.append("M");
		for (int i = 0; i < count - 1; i++) {
			sb.append("m");
		}
		return sb.toString();
	}

}
