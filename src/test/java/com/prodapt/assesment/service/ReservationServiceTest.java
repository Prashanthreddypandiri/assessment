package com.prodapt.assesment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.prodapt.assesment.dto.ReserveBookRequest;
import com.prodapt.assesment.dto.UpdateReservationDTO;
import com.prodapt.assesment.entity.Book;
import com.prodapt.assesment.entity.Reservation;
import com.prodapt.assesment.entity.User;
import com.prodapt.assesment.repository.BookRepository;
import com.prodapt.assesment.repository.ReservationRepository;
import com.prodapt.assesment.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;
    
    @Mock
    private BookRepository bookRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void reserveBook_ShouldReturnReservation_WhenValidRequest() {
        ReserveBookRequest request = new ReserveBookRequest("user123", "book456", "book123",LocalDate.now(), LocalDate.now().plusDays(7), "active", "Notes");
        Reservation reservation = new Reservation();
        reservation.setUser(new User(request.getUserId()));
        reservation.setBook(new Book(request.getBookId()));
        reservation.setReservationDate(request.getReservationDate());
        reservation.setDueDate(request.getDueDate());
        reservation.setStatus(request.getStatus());

        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        ResponseEntity<Object> response = reservationService.reserveBook(request, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void reserveBook_ShouldReturnBadRequest_WhenUserIdIsEmpty() {
        ReserveBookRequest request = new ReserveBookRequest("", "book456","book123", LocalDate.now(), LocalDate.now().plusDays(7), "active", "Notes");

        ResponseEntity<Object> response = reservationService.reserveBook(request, mock(HttpServletRequest.class));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((Map<?, ?>) response.getBody()).containsValue("User ID must not be empty"));
    }
    
    @Test
    void updateReservation_ShouldReturnUpdatedReservation_WhenValidRequest() {
        UpdateReservationDTO request = new UpdateReservationDTO("res123", LocalDate.now(), LocalDate.now().plusDays(7), "updated", "Updated Notes");
        Reservation reservation = new Reservation();
        Book book = new Book();
        book.setId("book123");
        User user = new User();
        user.setUserId("user123");
        reservation.setReservationId("res123");
        reservation.setBook(book);
        reservation.setUser(user);

        when(reservationRepository.findById("res123")).thenReturn(Optional.of(reservation));
        when(bookRepository.findById("book123")).thenReturn(Optional.of(book));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        ResponseEntity<Object> response = reservationService.updateReservation(request,any());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void updateReservation_ShouldReturnNotFound_WhenReservationNotFound() {
        UpdateReservationDTO request = new UpdateReservationDTO("invalidResId", LocalDate.now(), LocalDate.now().plusDays(7), "updated", "Updated Notes");

        when(reservationRepository.findById("invalidResId")).thenReturn(Optional.empty());

        ResponseEntity<Object> response = reservationService.updateReservation(request, mock(HttpServletRequest.class));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(((Map<?, ?>) response.getBody()).containsValue("Reservation not found"));
    }

    @Test
    void calculateFine_ShouldReturnZero_WhenReturnedOnTime() {
        Reservation reservation = new Reservation();
        reservation.setDueDate(LocalDate.now());

        when(reservationRepository.findById("res123")).thenReturn(Optional.of(reservation));

        double fine = reservationService.calculateFine("res123");

        assertEquals(0, fine);
    }

    @Test
    void calculateFine_ShouldReturnFine_WhenReturnedLate() {
        Reservation reservation = new Reservation();
        reservation.setDueDate(LocalDate.now().minusDays(20));

        when(reservationRepository.findById("res123")).thenReturn(Optional.of(reservation));

        double fine = reservationService.calculateFine("res123");

        assertEquals(6.0, fine); // (20 - 14) * 1.0
    }

    @Test
    void markReservationAsDeleted_ShouldSetStatusDeleted_WhenReservationExists() {
        Reservation reservation = new Reservation();
        reservation.setReservationId("res123");

        when(reservationRepository.findById("res123")).thenReturn(Optional.of(reservation));

        ResponseEntity<Object> response = reservationService.markReservationAsDeleted("res123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("deleted", reservation.getStatus());
    }

    @Test
    void markReservationAsDeleted_ShouldThrowException_WhenReservationNotFound() {
        when(reservationRepository.findById("invalidResId")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> reservationService.markReservationAsDeleted("invalidResId"));
        assertEquals("Reservation not found", exception.getMessage());
    }
}






















