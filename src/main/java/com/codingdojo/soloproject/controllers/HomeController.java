package com.codingdojo.soloproject.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codingdojo.soloproject.models.LoginUser;
import com.codingdojo.soloproject.models.Book;
import com.codingdojo.soloproject.models.User;
import com.codingdojo.soloproject.services.BookService;
import com.codingdojo.soloproject.services.UserService;


    
@Controller
public class HomeController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BookService bookService;
    
   
    @GetMapping("/")
    public String index( @ModelAttribute("newUser") User newUser, 
    		@ModelAttribute("newLogin") LoginUser newLogin) {
   
              return "index.jsp";
    }
    
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser,
            BindingResult result, Model model, HttpSession session) {

        User registeredUser = userService.register(newUser, result);

        if (result.hasErrors()) {
            model.addAttribute("newLogin", new LoginUser());
            return "index.jsp";
        }

        // No errors!
        session.setAttribute("userId", registeredUser.getId());

        return "redirect:/dashboard";
    }
    
    
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin,
                        BindingResult result, Model model, HttpSession session) {
    	
         User user = userService.login(newLogin, result);

        if (result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "index.jsp";
        }

        // No errors!
        session.setAttribute("userId", user.getId());


        return "redirect:/dashboard";
    }
    
    
    
    @GetMapping("/dashboard")
    public String welcome(HttpSession session, Model model, @ModelAttribute("newBook") Book newBook) {
    	
        // Check if the user is logged in
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // User is not logged in, redirect to the login page
            return "redirect:/";
        }

        // User is logged in
        User user = userService.findUser(userId);
        if (user == null) {
            // User not found, redirect to the login page
            return "redirect:/";
        }
        
        List<Book> books= bookService.allBooks();
        
        // User is authenticated, pass the user and books objects to the view for display
        model.addAttribute("user", user);
        model.addAttribute("books", books);


        return "welcome.jsp";
    }
    

    @PostMapping("/addFavorite/{bookId}")
    public String addFavorite(@PathVariable("bookId") Long bookId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        User user = userService.findUser(userId); 
        Book book = bookService.findBook(bookId);

        if (user != null && book != null) {
            if (!user.getFavorite_books().contains(book)) {
                user.getFavorite_books().add(book);
                userService.updateUser(user); 
            }
        }

        return "redirect:/dashboard"; 
    }
    
    
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // Clear the session
        session.invalidate();
        return "redirect:/";
    }
    
    
    
    @PostMapping("/books/new")
    public String bookShow( @Valid @ModelAttribute("newBook") Book newBook, BindingResult result, Model model, HttpSession session) {
    	
    	Book savedBook =bookService.createBook(newBook, result);
        List<Book> books= bookService.allBooks();
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUser(userId);

    	if (result.hasErrors()) {
    		model.addAttribute("user", user);
    		model.addAttribute("books", books);
            return "welcome.jsp";
        } 
    	
    
        // Add the saved book to the user's favorite list
        if (user != null) {
            user.getFavorite_books().add(savedBook);
            userService.updateUser(user); 
        }
    	
		return "redirect:/dashboard";

    }
    
    @GetMapping("/books/{id}")
    public String viewBook( @PathVariable("id") Long bookId, Model model,  HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        User user = userService.findUser(userId);
        if (user == null) {
            return "redirect:/";
        }

        Book book = bookService.findBook(bookId);
        

        model.addAttribute("user", user);
        model.addAttribute("book" , book);
        return "viewBook.jsp";
    }
    

    
    @RequestMapping(value="/books/edit/{id}", method=RequestMethod.PUT)
    public String update(@Valid @ModelAttribute("book") Book updatedBook, BindingResult result, Model model, HttpSession session) {
        
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUser(userId);
        // Fetch the existing book from the database
        Book existingBook = bookService.findBook(updatedBook.getId());
        if (existingBook == null) {
            return "redirect:/dashboard";
        }
        
        // If there are validation errors, return the view with error messages and necessary attributes
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "viewBook.jsp";
        }
        
        
        
        Book updatedBookResult = bookService.updateBook(updatedBook, result);

        if (updatedBookResult == null) {
            model.addAttribute("user", user);
            return "viewBook.jsp";
        }
        
        return "redirect:/dashboard";
    }
    
    @RequestMapping(value="/removeFavorite/{bookId}", method=RequestMethod.DELETE)
    public String removeFavorite(@PathVariable("bookId") Long bookId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        User user = userService.findUser(userId);
        Book book = bookService.findBook(bookId);

        if (user != null && book != null) {
            user.getFavorite_books().remove(book);
            userService.updateUser(user);
        }

        return "redirect:/books/" + bookId;
    }
    
    @RequestMapping(value="/books/delete/{id}", method=RequestMethod.DELETE)
    public String deleteBook(@PathVariable("id") Long id) {
        Book book = bookService.findBook(id);
        
        // Remove the book from users' favorite list
        for(User user : book.getFavoritedByUsers()) {
            user.getFavorite_books().remove(book);
            userService.updateUser(user);
        }
        
        // Delete the book
        bookService.deleteBook(id);
        
        return "redirect:/dashboard";
    }
    
    
    

}
    

