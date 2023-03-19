package com.app.service;


import java.util.List;

import com.app.model.Candle;


public interface CandleService {
	/* 	1--method--- saveAllCandles this Method return as list of candle 
			 
		* 	2--method--- getAllCandles  this Method return as list of candle 
			
		*  3--method--- combineCandles this Method return as list of candle but in this Method take one Time Parameter as Time in 
						 and combine the candles According to Time --like 5min ,10min,30min
						 
		* 	4--method--- findBreakOutRange this method return type is a String of DateandTime
	*/
	public List<Candle> saveAllCandles() throws Exception;
	
	public List<Candle> getAllCandles()throws Exception;
	
	public List<Candle> combineCandles(Integer time) throws Exception;
	
	public String  findBreakOutRangeDateandTime(Integer time) throws Exception;
	
}
