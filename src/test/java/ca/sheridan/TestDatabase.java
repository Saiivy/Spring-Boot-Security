package ca.sheridan;

import static org.assertj.core.api.Assertions.assertThat;

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
	public void testDatabaseAddBook() {
		//Creating a book object
		Book book = new Book();
		book.setAuthor("Gursimar");
		book.setTitle("Java Spring Boot");
		
		//Original db size
		int orginalDatabaseSize = da.viewBooks().size();
		
		//adding a book to db
		da.addBook(book);
		
		//After adding object, db size
		int addedDatabaseSize = da.viewBooks().size();
		
		//checking if both are equal
		assertThat(addedDatabaseSize).isEqualTo(orginalDatabaseSize+1);
		
	}

}
