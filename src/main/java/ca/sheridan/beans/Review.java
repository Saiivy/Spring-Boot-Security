package ca.sheridan.beans;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author gursimar
 * This class will act act as a review object for book objects.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Review {
//id of the book
private long bookId;
//id not visible to the user, needed to implement an effective database.
private long id;
//Reviews given the the users to a particular book.
@NotNull(message="Field must not be Empty!")
@NotEmpty(message="Field must not be Empty!")
private String text;
}
