package com.prodapt.assesment.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.prodapt.assesment.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByBookIsbn(String isbn);
    List<Reservation> findByStatus(String status);
}