package com.maya.Hotel.repository;

import com.maya.Hotel.entity.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckOutRepository extends JpaRepository<CheckOut, Integer> {
    boolean existsByReserva_Id(Integer idReserva);
}

