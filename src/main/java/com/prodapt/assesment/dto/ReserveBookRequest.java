package com.prodapt.assesment.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class ReserveBookRequest {
    @NotBlank
    private String userId;

    @NotBlank
    private String bookingId;

    @NotBlank
    private String bookId;
    
    @NotNull
    private LocalDate reservationDate;

    @NotNull
    private LocalDate dueDate;

    @Pattern(regexp = "reserved|cancelled|completed")
    private String status;

    @Email
    private String email;

    @Pattern(regexp = "^\\+\\d{10,15}$")
    private String phoneNumber;

    @Pattern(regexp = "email|phone|none")
    private String preferredContactMethod;

    @Size(max = 500)
    private String notes;
    
       

	public ReserveBookRequest(String userId,String bookingId,String bookId,LocalDate reservationDate,
			LocalDate dueDate,String status,String notes) {
		this.userId = userId;
		this.bookingId = bookingId;
		this.bookId = bookId;
		this.reservationDate = reservationDate;
		this.dueDate = dueDate;
		this.status = status;
		this.notes = notes;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public LocalDate getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPreferredContactMethod() {
		return preferredContactMethod;
	}

	public void setPreferredContactMethod(String preferredContactMethod) {
		this.preferredContactMethod = preferredContactMethod;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
   
}