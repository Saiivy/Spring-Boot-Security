package ca.sheridan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.sheridan.beans.Book;
import ca.sheridan.database.DatabaseAccess;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDatabase {
	@Autowired
	public DatabaseAccess da;

	@Test
	/**
	 * Tests if database is adding book object
	 */
	public void testDatabaseAddBook() {
		// Creating a book object
		Book book = new Book();
		book.setAuthor("Gursimar");
		book.setTitle("Java Spring Boot");

		// Original db size
		int orginalDatabaseSize = da.getBooks().size();

		// adding a book to db
		da.addBook(book);

		// After adding object, db size
		int addedDatabaseSize = da.getBooks().size();

		// checking if both are equal
		assertThat(addedDatabaseSize).isEqualTo(orginalDatabaseSize + 1);

	}
	
	@Test
	/**
	 * Checks if methods are querying correct data from database
	 */
	public void testDatabaseQuery() {
		Book book = new Book();
		book.setAuthor("Stephen R. Covey");
		book.setTitle("The 7 Habits of Highly Effective People");
		book.setId(1);
		Book daBook = da.getBooks().get(0);
		assertEquals(book,daBook);
		
	}

}
