package com.app.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Candle {
	
	@Id
//	private Integer id;
	@JsonProperty("LastTradeTime")
	private LocalDateTime  lastTradeTime;
	
	@JsonProperty("QuotationLot")
	private Integer quotationLot;
	
	@JsonProperty("TradedQty")
	private Integer tradedQty;
	
	@JsonProperty("OpenInterest")
	private Integer openInterest;
	
	@JsonProperty("Open")
	private Double open;
	
	@JsonProperty("High")
	private Double high;
	
	@JsonProperty("Low")
	private Double low;
	
	@JsonProperty("Close")
	private Double close;
}
