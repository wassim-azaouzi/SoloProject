package com.codingdojo.soloproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.soloproject.models.Book;
import com.codingdojo.soloproject.models.User;
import com.codingdojo.soloproject.repositories.BookRepository;

@Service
public class BookService {
	@Autowired
    private BookRepository bookRepository;
	
	 // returns all the books
    public List<Book> allBooks() {
        return (List<Book>) bookRepository.findAll();
    }
    
    
    // creates a book
    public Book createBook(Book book, BindingResult result) {
    	Optional<Book> potentialBook = bookRepository.findByTitle(book.getTitle());
        if (potentialBook.isPresent()) {
            result.rejectValue("title", "Matches", "Title already exists");
        }
        
        // Return null if result has errors
        if (result.hasErrors()) {
            return null;
        }
        return bookRepository.save(book);
    }
    
    
    // retrieves a book
    public Book findBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent()) {
            return optionalBook.get();
        } else {
            return null;
        }
    }
    
    
    //update a book 
    public Book updateBook(Book book, BindingResult result) {
    	Optional<Book> potentialBook = bookRepository.findByTitle(book.getTitle());
    	
    	if (potentialBook.isPresent() && !book.getId().equals(potentialBook.get().getId())) {
    	    result.rejectValue("title", "Matches", "Title already exists");
    	}
        
        // Return null if result has errors
        if (result.hasErrors()) {
            return null;
        }
        return bookRepository.save(book);
    }
    
    
    
    // delete a book
    public void deleteBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent()) {
        	bookRepository.deleteById(id);
        } 
    }
    

}
