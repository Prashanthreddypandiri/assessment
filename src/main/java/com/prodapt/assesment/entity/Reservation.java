package com.prodapt.assesment.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reservationId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    @Column(nullable = false)
    private LocalDate reservationDate;
    
    @Column(nullable = false)
    private LocalDate dueDate;
    
    @Column(nullable = false, length = 15)
    private String status;
    
    @Column(length = 500)
    private String notes;
    
    @Column()
    private LocalDate deletionTimestamp;

    public Reservation() {
    	
    }	
    
	public Reservation(String reservationId, User user, Book book, LocalDate reservationDate, LocalDate dueDate,
			String status, String notes, LocalDate deletionTimestamp) {
		super();
		this.reservationId = reservationId;
		this.user = user;
		this.book = book;
		this.reservationDate = reservationDate;
		this.dueDate = dueDate;
		this.status = status;
		this.notes = notes;
		this.deletionTimestamp = deletionTimestamp;
	}

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LocalDate getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDate getDeletionTimestamp() {
		return deletionTimestamp;
	}

	public void setDeletionTimestamp(LocalDate deletionTimestamp) {
		this.deletionTimestamp = deletionTimestamp;
	}

    
}