package com.prodapt.assesment.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.prodapt.assesment.dto.ReserveBookRequest;
import com.prodapt.assesment.dto.UpdateReservationDTO;
import com.prodapt.assesment.entity.Book;
import com.prodapt.assesment.entity.Reservation;
import com.prodapt.assesment.entity.User;
import com.prodapt.assesment.repository.ReservationRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ResponseEntity<Object> reserveBook(ReserveBookRequest request, HttpServletRequest requestPath) {
        try {
            if (request.getUserId() == null || request.getUserId().trim().isEmpty()) {
                return buildErrorResponse("User ID must not be empty", HttpStatus.BAD_REQUEST, requestPath.getRequestURI());
            }
            if (request.getBookId() == null || request.getBookId().trim().isEmpty()) {
                return buildErrorResponse("Book ID must not be empty", HttpStatus.BAD_REQUEST, requestPath.getRequestURI());
            }

            Reservation reservation = new Reservation();
            reservation.setUser(new User(request.getUserId()));
            reservation.setBook(new Book(request.getBookId()));
            reservation.setReservationDate(request.getReservationDate());
            reservation.setDueDate(request.getDueDate());
            reservation.setStatus(request.getStatus());
            reservation.setNotes(request.getNotes());

            reservation = reservationRepository.save(reservation);

            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } catch (Exception e) {
        	System.out.println("Error while reserving book: " + e.getMessage());
            return buildErrorResponse("Error while reserving book: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, requestPath.getRequestURI());
        }
    }

    public ResponseEntity<Object> updateReservation(UpdateReservationDTO request,HttpServletRequest requestPath) {
        try {
            Optional<Reservation> reservationOpt = reservationRepository.findById(request.getReservationId());
            if (reservationOpt.isEmpty()) {
                return buildErrorResponse("Reservation not found", HttpStatus.NOT_FOUND, "/api/v1/reservations/update");
            }

            Reservation reservation = reservationOpt.get();
            reservation.setBook(new Book(reservation.getBook().getId()));
            reservation.setUser(new User(reservation.getUser().getUserId()));
            reservation.setReservationDate(request.getNewReservationDate());
            reservation.setDueDate(request.getNewdueDate());
            reservation.setStatus(request.getNewStatus());
            reservation.setNotes(request.getNewNotes());

            reservation = reservationRepository.save(reservation);
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } catch (Exception e) {
        	System.out.println("Error updating reservation: " + e.getMessage());
        	return buildErrorResponse("Error updating reservation: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "/api/v1/reservations/update");
        }
    }


    public double calculateFine(String reservationId) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        if (reservationOpt.isEmpty()) {
            throw new IllegalArgumentException("Reservation not found");
        }

        Reservation reservation = reservationOpt.get();
        LocalDate dueDate = reservation.getDueDate();
        LocalDate returnDate = LocalDate.now();
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);

        return daysLate <= 14 ? 0 : daysLate <= 30 ? (daysLate - 14) * 1.0 : (16 * 1.0) + ((daysLate - 30) * 0.5);
    }

    public ResponseEntity<Object> markReservationAsDeleted(String reservationId) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        if (reservationOpt.isEmpty()) {
            throw new IllegalArgumentException("Reservation not found");
        }

        Reservation reservation = reservationOpt.get();
        reservation.setStatus("deleted");
        reservationRepository.save(reservation);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
	public String getReservationHistory(String isbn) {
		try {
		List<Reservation> reservations;
		if (isbn != null) {
			reservations = reservationRepository.findByBookIsbn(isbn);
		} else {
			reservations = reservationRepository.findAll();
		}

		if (reservations.isEmpty()) {
//			throw new IllegalArgumentException("No reservations found.");
			return "No reservations found.";
		}

		return generateXmlResponse(reservations);
		}catch(Exception e) {
			System.out.println("Error while markReservationAsDeleted: "+e.getMessage());
			return "Unable to generate response";			
		}
		
	}
	
	private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status, String path) {
	    Map<String, Object> errorMap = new HashMap<>();
	    errorMap.put("timestamp", LocalDate.now().toString());
	    errorMap.put("status", status.value());
	    errorMap.put("error", status.getReasonPhrase());
	    errorMap.put("message", message);
	    errorMap.put("path", path);

	    return new ResponseEntity<>(errorMap, status);
	}

    private String generateXmlResponse(List<Reservation> reservations) {
		return "<books>" +
				reservations.stream()
		.map(reservation -> "<book>" +
				"<title>" + reservation.getBook().getTitle() + "</title>" +
				"<author>" + reservation.getBook().getAuthor() + "</author>" +
				"<isbn>" + reservation.getBook().getIsbn() + "</isbn>" +
				"<language>" + reservation.getBook().getLanguage() + "</language>" +
				"<reservations>" +
				"<reservation>" +
				"<reservation_id>" + reservation.getReservationId() + "</reservation_id>" +
				"<user_id>" + reservation.getUser().getUserId() + "</user_id>" +
				"<reservation_date>" + reservation.getReservationDate() + "</reservation_date>" +
				"<due_date>" + reservation.getDueDate() + "</due_date>" +
				"<status>" + reservation.getStatus() + "</status>" +
				"</reservation>" +
				"</reservations>" +
				"</book>")
		.collect(Collectors.joining()) +
		"</books>";
	}


}