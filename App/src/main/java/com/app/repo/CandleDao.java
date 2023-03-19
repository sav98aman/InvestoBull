package com.app.repo;



import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Candle;


@Repository
public interface CandleDao extends JpaRepository<Candle, LocalDateTime>{

	


}
