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
	private DatabaseAccess da;
	
	@Test
	public void databaseAddtest() {
		Book book = new Book();
		book.setAuthor("Gursimar");
		book.setTitle("Java");
		
		//value before adding to database
		int beforeAddingDatabaseSize = da.viewBooks().size();
		
		//Let's add a value to database
		da.addBook(book);
		
		//Quering db again
		int afterAddingDatabaseSize = da.viewBooks().size();
		
		//Check if is equal or not
		assertThat(afterAddingDatabaseSize).isEqualTo(beforeAddingDatabaseSize+1);
}
}
