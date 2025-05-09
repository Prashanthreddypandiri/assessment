package com.prodapt.assesment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prodapt.assesment.dto.ReserveBookRequest;
import com.prodapt.assesment.dto.UpdateReservationDTO;
import com.prodapt.assesment.service.ReservationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<Object> reserveBook(@Valid @RequestBody ReserveBookRequest request, HttpServletRequest requestPath) {
        return reservationService.reserveBook(request, requestPath);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateReservation(@Valid @RequestBody UpdateReservationDTO request,HttpServletRequest requestPath) {
        return reservationService.updateReservation(request,requestPath);
    }

    @GetMapping("/calculate-fine")
    public ResponseEntity<Double> calculateFine(@RequestParam String reservationId) {
        double fine = reservationService.calculateFine(reservationId);
        return ResponseEntity.ok(fine);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteReservation(@RequestParam String reservationId) {
        return reservationService.markReservationAsDeleted(reservationId);
    }

    @GetMapping(value = "/history", produces = "application/xml")
    public ResponseEntity<String> getReservationHistory(@RequestParam(required = false) String isbn) {
        String response = reservationService.getReservationHistory(isbn);
        return ResponseEntity.ok(response);
    }
}