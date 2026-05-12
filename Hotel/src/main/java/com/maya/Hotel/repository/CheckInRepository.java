package com.maya.Hotel.repository;

import com.maya.Hotel.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
    boolean existsByReserva_Id(Integer idReserva);
}
