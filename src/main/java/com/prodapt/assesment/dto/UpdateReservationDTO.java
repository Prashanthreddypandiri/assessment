package com.prodapt.assesment.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdateReservationDTO {

	@NotBlank
    private String reservationId;
    
    @NotNull
    private LocalDate newReservationDate;

    @NotNull
    private LocalDate newdueDate;

    @Pattern(regexp = "reserved|cancelled|completed")
    private String newStatus;

    @Pattern(regexp = "email|phone|none")
    private String newPreferredContactMethod;

    @Size(max = 500)
    private String newNotes;

    public UpdateReservationDTO(String reservationId, LocalDate newReservationDate,
			LocalDate newdueDate,String newStatus,String newPreferredContactMethod) {
		this.reservationId = reservationId;
		this.newReservationDate = newReservationDate;
		this.newdueDate = newdueDate;
		this.newStatus = newStatus;
		this.newPreferredContactMethod = newPreferredContactMethod;
	}

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public LocalDate getNewReservationDate() {
		return newReservationDate;
	}

	public void setNewReservationDate(LocalDate newReservationDate) {
		this.newReservationDate = newReservationDate;
	}

	public LocalDate getNewdueDate() {
		return newdueDate;
	}

	public void setNewdueDate(LocalDate newdueDate) {
		this.newdueDate = newdueDate;
	}

	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	public String getNewPreferredContactMethod() {
		return newPreferredContactMethod;
	}

	public void setNewPreferredContactMethod(String newPreferredContactMethod) {
		this.newPreferredContactMethod = newPreferredContactMethod;
	}

	public String getNewNotes() {
		return newNotes;
	}

	public void setNewNotes(String newNotes) {
		this.newNotes = newNotes;
	}

    
}
