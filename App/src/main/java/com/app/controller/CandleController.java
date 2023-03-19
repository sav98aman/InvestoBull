package com.app.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Candle;
import com.app.service.CandleService;


@RestController
public class CandleController {
	
	@Autowired
	private CandleService candleservice;
	
	// just checkout controller is Working or not
	@GetMapping(value = "/demo")
	public String welcome() {
		return "Welceome";
	}
	
	
	/*
	 *
	 * internally call the getAllCandle() method from Candle Service Interface in CandleService Interface have SOme Method
	 * 	1--method--- saveAllCandles this Method return as list of candle 
	 
	 * 	2--method--- getAllCandles  this Method return as list of candle 
	 
	 *  3--method--- combineCandles this Method return as list of candle but in this Method take one Time Parameter as Time in 
					 and combine the candles According to Time --like 5min ,10min,30min
					 
	 * 	4--method--- findBreakOutRange this method return type is a String of DateandTime
	 */
	
	
	// get the all candle 
	@GetMapping(value = "/candles")
	public ResponseEntity<List<Candle>> getAllCandlesHandller() throws Exception {
		
		return new ResponseEntity<List<Candle>>(candleservice.getAllCandles(),HttpStatus.OK);
	}
	
	// combine the According to time
	@GetMapping(value = "/candles/{time}")
	public ResponseEntity<List<Candle>> getAllCandlesHandller(@PathVariable("time") Integer time) throws Exception {
		
		return new ResponseEntity<List<Candle>>(candleservice.combineCandles(time),HttpStatus.OK);
	}
	
	// find the Breakout Time 
	@GetMapping(value = "/openingbreakout/{time}")
	public ResponseEntity<String> findEndTradeTime(@PathVariable("time") Integer time) throws Exception{
		
		return new ResponseEntity<String>(candleservice.findBreakOutRangeDateandTime(time),HttpStatus.ACCEPTED);
	}

	
	// save all the candle in database
	@PostMapping("/candles")
    public ResponseEntity<List<Candle>> saveCandlesHandller() throws Exception {
		List<Candle> list= candleservice.saveAllCandles();
		return new ResponseEntity<List<Candle>>(list, HttpStatus.ACCEPTED);
    }
	
	
	

}
