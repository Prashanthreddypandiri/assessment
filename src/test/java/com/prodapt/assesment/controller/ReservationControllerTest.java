package com.prodapt.assesment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.prodapt.assesment.dto.ReserveBookRequest;
import com.prodapt.assesment.dto.UpdateReservationDTO;
import com.prodapt.assesment.service.ReservationService;

import jakarta.servlet.http.HttpServletRequest;

class ReservationControllerTest {

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void reserveBook_ShouldReturnOk_WhenValidRequest() {
        ReserveBookRequest request = new ReserveBookRequest("user123", "book456", "book123",LocalDate.now(), LocalDate.now().plusDays(7), "active", "Notes");
        when(reservationService.reserveBook(any(ReserveBookRequest.class), any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Object> response = reservationController.reserveBook(request, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateReservation_ShouldReturnOk_WhenValidRequest() {
        UpdateReservationDTO request = new UpdateReservationDTO("res123", LocalDate.now(), LocalDate.now().plusDays(7), "updated", "Updated Notes");

        when(reservationService.updateReservation(any(UpdateReservationDTO.class), any(HttpServletRequest.class)))
            .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Object> response = reservationController.updateReservation(request, mock(HttpServletRequest.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void calculateFine_ShouldReturnFine_WhenReservationExists() {
        when(reservationService.calculateFine("res123")).thenReturn(10.0);

        ResponseEntity<Double> response = reservationController.calculateFine("res123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10.0, response.getBody());
    }

    @Test
    void deleteReservation_ShouldReturnOk_WhenReservationDeleted() {
        when(reservationService.markReservationAsDeleted("res123")).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Object> response = reservationController.deleteReservation("res123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getReservationHistory_ShouldReturnXml_WhenReservationsExist() {
        when(reservationService.getReservationHistory(null)).thenReturn("<reservations><reservation>data</reservation></reservations>");

        ResponseEntity<String> response = reservationController.getReservationHistory(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("<reservations>"));
    }
}