package com.codingdojo.soloproject.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="books")
public class Book {
	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	
	    @NotEmpty(message = "Title is required!")
	    @Size(min = 3, max = 100, message = "Title must be at least 3 characters")
	    private String title;
	
	
	    @NotEmpty(message = "Description is required!")
	    @Size(min = 5, max = 500, message = "Description must be at least 5 characters")
	    private String description;
    
		@Column(updatable=false)
	    private Date createdAt;
	    private Date updatedAt;
	    
	    @PrePersist
	    protected void onCreate(){
	        this.createdAt = new Date();
	    }
	    @PreUpdate
	    protected void onUpdate(){
	        this.updatedAt = new Date();
	    } 
	    
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="user_id")
	    private User leadUser;
	    
	   


		@ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(
	        name = "user_favorite_books", 
	        joinColumns = @JoinColumn(name = "book_id"), 
	        inverseJoinColumns = @JoinColumn(name = "user_id")
	    )
	    private List<User> favoritedByUsers;
	  
	    
	    
	    public Book() {
	        
	    }
	    
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Date getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}
		public Date getUpdatedAt() {
			return updatedAt;
		}
		public void setUpdatedAt(Date updatedAt) {
			this.updatedAt = updatedAt;
		}
			
			
		public User getLeadUser() {
			return leadUser;
		}
		public void setLeadUser(User leadUser) {
			this.leadUser = leadUser;
		}
		public List<User> getFavoritedByUsers() {
			return favoritedByUsers;
		}
		public void setFavoritedByUsers(List<User> favoritedByUsers) {
			this.favoritedByUsers = favoritedByUsers;
		}
		
		
		
		
	    

		
	    
}
