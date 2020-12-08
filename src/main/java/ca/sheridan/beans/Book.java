package ca.sheridan.beans;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * 
 * @author gursimar
 * This class will act as a book object.
 *
 */
@Data

public class Book {
	
//id not visible to the user, needed to implement an effective database.
private long id;
//Title of the book
@NotEmpty(message="Please Enter Title Of The Book")
@NotNull(message="Please Enter Title Of The Book")
private String title;
//Author of the book
@NotEmpty(message="Please Enter The Name Of The Author")
@NotNull(message="Please Enter The Name Of The Author")
private String author;
}
