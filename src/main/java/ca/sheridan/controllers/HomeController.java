package ca.sheridan.controllers;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ca.sheridan.beans.Book;
import ca.sheridan.beans.Review;
import ca.sheridan.database.DatabaseAccess;

/**
 * 
 * @author gursimar 
 * Base class that controls all the actions one the frontend,
 * backend for the application
 *
 */
@Controller
public class HomeController {
	private DatabaseAccess database;

	public HomeController(DatabaseAccess database) {
		this.database = database;
	}
//Thread Safe Arraylist for storing book object
//private CopyOnWriteArrayList <Book> bookList = new CopyOnWriteArrayList<>();

	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	JdbcUserDetailsManager jdbcUserDetailsManager;

	private User user;

	/**
	 * Will redirect users to the home-page with book data
	 * 
	 * @return home-page
	 */
	@GetMapping("/")
	public String goIndex(Model model, Book book) {
		System.out.println(database.viewBooks());
		model.addAttribute("bookList", database.viewBooks());
		return "index";
	}

	/**
	 * 
	 * @return login page
	 */
	@GetMapping("/registerForm")
	public String retrunRegistrationForm() {
		return "register";
	}

	/**
	 * validates fields and creates a new user in database
	 * @param name
	 * @param password Registers users and adds them to database
	 * @return registration form
	 */

	@PostMapping("/register")
	public String RegistrationForm(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password, Model model) {

		if (username.equals("") || username == null || password.equals("") || password == null) {
			model.addAttribute("error", "Input both password and username inorder to register!");
			return "register";
		} else {
			String encodedpassword = passwordEncoder.encode(password);
			user = new User(username, encodedpassword, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
			jdbcUserDetailsManager.createUser(user);
			return "redirect:/";
		}
	}

	/**
	 * 
	 * @return login form
	 */
	@GetMapping("/loginForm")
	public String returnLoginForm() {
		return "login";
	}

	/**
	 * Checks if the user is entering right credentials for signing in
	 * 
	 * @param name
	 * @param password
	 * @return /login
	 */
	@RequestMapping("/login")
	public String loginValidation() {
		return "/login";
	}

	/**
	 * Gets reviews from database of a a particular book using its id
	 * 
	 * @param id
	 * @param model
	 * @param bookName
	 * @return view-book page
	 */

	@GetMapping("/user/reviews/{id}")
	public String getReviews(@PathVariable long id, Model model) {
		model.addAttribute("id",id);
		model.addAttribute("reviewList", database.getReviews(id));
		return "view-book";
	}

	/**
	 * Creates new object of reviews added by the user/admin
	 * 
	 * @param model
	 * @return add-review page
	 */
	@GetMapping("/user/post-review/{id}")
	public String addReview(Model model,@PathVariable long id) {
		model.addAttribute("review",new Review());
		model.addAttribute("id",id);
		System.out.println(id);
		return "/user/add-review";
	}

	/**
	 * Adds reviews to database
	 * 
	 * @param review
	 * @return redirect to index page
	 */
	@RequestMapping("/user/process-review/{id}")
	public String processReview(@Valid Review review,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "/user/add-review";
		}
		else {
		database.addreview(review);
		System.out.println(review);
		return "redirect:/";
		}
	}

	/**
	 * Pushes new book object to add-book page
	 * 
	 * @param model
	 * @return add-book page
	 */
	@GetMapping("/admin/add-book")
	public String addBookForm(Model model) {
		model.addAttribute("book", new Book());
		return "admin/add-book";

	}

	/**
	 * Checks if the input fields are correct
	 * Adds book to database from input of admin
	 * 
	 * @param book
	 * @return redirect to index
	 */
	@PostMapping("/admin/addBook")
	public String addBook(@Valid Book book,BindingResult bindingResult,Model model) {
		if(bindingResult.hasErrors()) {
			return "/admin/add-book";
		}
		else {
		database.addBook(book);
		return "redirect:/";
		}
	}

	/**
	 * Logs out user
	 * 
	 * @return /logout
	 */
	@RequestMapping("/logout")
	public String logout() {
		return "/logout";
	}

	/**
	 * Gets forbidden page, if unauthorized user access the pages.
	 * 
	 * @return error page
	 */
	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "error/permission-denied";
	}
}
