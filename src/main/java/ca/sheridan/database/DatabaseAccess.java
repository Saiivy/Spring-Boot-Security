package ca.sheridan.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 * 
 * @author gursimar
 * A class which allows us to push all the data of the book and user
 * to the database and retrieve it.
 *
 */

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridan.beans.Book;
import ca.sheridan.beans.Review;

@Repository
public class DatabaseAccess {

	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;
	// Getting jdbc template
	private NamedParameterJdbcTemplate jdbc;

	// Invoking constructor for jdbc
	public DatabaseAccess(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * Using this a user can add Book object to the database
	 * 
	 * @param book
	 * @return database response.
	 */

	public int addBook(Book book) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT into books (title,author) " + "VALUES (:title, :author)";
		namedParameters.addValue("title", book.getTitle()).addValue("author", book.getAuthor());
		return jdbc.update(query, namedParameters);
	}

	/**
	 * Will query the database to get all the books.
	 * 
	 * @param
	 * @return List of Books.
	 */

	public List<Book> viewBooks() {
		String query = "SELECT * FROM books";
		BeanPropertyRowMapper<Book> bookMapper = new BeanPropertyRowMapper<Book>(Book.class);
		List<Book> bookList = jdbc.query(query, bookMapper);
		return bookList;
	}

	/**
	 * Will query the database to get all the reviews.
	 * 
	 * @param
	 * @return List of Books.
	 */

	public List<Review> getReviews(long bookID) {
		String query = "SELECT * FROM reviews WHERE bookID= :bookID";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		BeanPropertyRowMapper<Review> reviewMapper = new BeanPropertyRowMapper<Review>(Review.class);
		namedParameters.addValue("bookID", bookID);
		List<Review> reviewList = jdbc.query(query, namedParameters, reviewMapper);
		return reviewList;

	}
	/**
	 * Will add review to the database
	 * @param review
	 * @return response database
	 */

	public int addreview(Review review) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT into reviews (bookId,text) " + "VALUES (:bookId, :text)";
		namedParameters.addValue("bookId", review.getBookId()).addValue("text", review.getText());
		return jdbc.update(query, namedParameters);
	}

}
