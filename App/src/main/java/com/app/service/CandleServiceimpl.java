package com.app.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.exception.CandleException;
import com.app.filehandler.JsoFileHandller;
import com.app.model.Candle;
import com.app.model.CandlesDummy;
import com.app.repo.CandleDao;


@Service
public class CandleServiceimpl implements CandleService{
	
	@Autowired// this import the method all method from jsonFileHandller
	private JsoFileHandller jsonFileHandalller;
	
	@Autowired
	
	// inject the CandleDao Dependency from CandleDao Interface and THis Interface internally  
	//extends one other interface JpaRepository in this interface by defaults Spring Data jpa 
	//Provide Some Methods To Perform Crude operation on database 
    private CandleDao candledao;

	@Override// save all data.json file Into Database
	public List<Candle> saveAllCandles() throws Exception {
		// in CandlesDummy is all field in the From of String need to convert to the Candle class Filed
		
		//get the CandlesDummy data and Convert into Actual Data Types
		List<CandlesDummy> listOfCandles=jsonFileHandalller.GetListofCandles();
		
		// create list new List of candle this List based on Data Type of - Candle
		List<Candle> newCandleData=new ArrayList<>();
		
		// Converting each candlesdummy String data into -- Candle Data Type 
		for (CandlesDummy candlesdummy : listOfCandles) {
			
			//LastTradeTime String  convert in LocalDateTime--
			LocalDateTime LastTradeTime;
				String dateString = candlesdummy.getLastTradeTime();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
				LastTradeTime = LocalDateTime.parse(dateString, formatter);
				
			//convert into this three String value quotationLot,tradedQty,openInterest in  Integer value--
			Integer quotationLot,tradedQty,openInterest;
			
				quotationLot=Integer.parseInt(candlesdummy.getQuotationLot());
				tradedQty=Integer.parseInt(candlesdummy.getTradedQty());
				openInterest=Integer.parseInt(candlesdummy.getOpenInterest());
			
			// convert into this three String value open,low,high in  double value--
			Double open,high,low,close;
				open=Double.parseDouble(candlesdummy.getOpen());
				high=Double.parseDouble(candlesdummy.getHigh());
				low=Double.parseDouble(candlesdummy.getLow());
				close=Double.parseDouble(candlesdummy.getClose());
				
			//create Candle  object
			Candle candle=new Candle(LastTradeTime, quotationLot, tradedQty, openInterest, open, high, low, close);
			
			//add each candle to list because at time save list of candle
			newCandleData.add(candle);
		}
		//save all List of candles into Database
		return candledao.saveAll(newCandleData);
	}


	@Override// get List Of Candle From Database 
	public List<Candle> getAllCandles() throws Exception {
		
		// find the List of candle from database in candledao interface have method to find all  data from database 
		List<Candle> listofCandle=candledao.findAll();
		
		//if data is Not Persnet in data base then throw on Exception 
		if (listofCandle == null) {
			throw new Exception(" Not Candles Is Persent ");
		}
		return listofCandle;// return data from the database 
		
	}


	@Override// combine the candle data according to Time (min.) and return list of Candles
	public List<Candle> combineCandles(Integer time) throws Exception {
		/*Some Condition of Combine the Data
		 	* open -- is First candle of Each combination block
		 	* close-- is last candle of each combination block
		 	* high -- high is find the max high with each combination block
		 	* low  -- find lowest of each combination block
		 	* treadqyt-- sum of all candle is present with each combination block
		 
		 	* Take as Argument as Time -- this Time Should be a multiple of 5min because in each data represent with 5min of Data
		 */
		
		// check valid Time or not--
		if(time%5!=0) {
			throw new Exception( "Please valid TIME :- Enter multipe of 5 min. ");
		}
		
		//get list of candle call the getAllCandle() Method this Method Return List of candle or[ you can call the candledao.findAll() Method ]
		List<Candle> listOfAllCandle=getAllCandles();//list of candles
		
		
		Integer index=listOfAllCandle.size();// find The Total candles
		Integer totalTimeinMin=index*5;// find The Total Time in minutes each candle represent as 5minutes of time  
		
		
		int outerLoopLimit=totalTimeinMin/time;//this Represent the total generate candles according to our Time  
		int innerLoopLimit=time/5;// this is Represent the Combination of Candles and make One single candle
		
		
		List<Candle> listOfTimeCandle =new ArrayList<>();// create a new List and Add each Candle in this list after combine this list
			
			
			// i represent the start index from 0 to last index of list
			// j index represent as combine the starting candle  to goes the how many candle combine according the time
		
			for(int i=0;i<index;i=i+innerLoopLimit) {//this is outer loop goes the last of index this loop is increment innerLoopLimit 
													//because when first index of outer loop is calling then inner loop is combine the Candle 
													//according to innner loop the otter loop again start from end of Inner loop
				
				Integer tradedQty=0;
				Double open;
				Double close;
				Double high=Double.MIN_VALUE;
				Double low=Double.MAX_VALUE;
				LocalDateTime localdatetime;
				
				int j=i;
					
					localdatetime=listOfAllCandle.get(i).getLastTradeTime();//getting Starting Candle Trade TIme
					open=listOfAllCandle.get(i).getOpen();// this is the open of Starting Candle
					
				for(j=i;j<i+innerLoopLimit&& j<index;j++) {
					tradedQty=tradedQty+(listOfAllCandle.get(j).getTradedQty());
					
					//low-- finding low value of each candle for  combine the candle
					if(low>listOfAllCandle.get(j).getLow()) {
						low=listOfAllCandle.get(j).getLow();
					}
					//high-- finding high value of each candle for combine the candle
					if(high<listOfAllCandle.get(j).getHigh()) {
						high=listOfAllCandle.get(j).getHigh();
					}
				}
				// find the last close for Each combine the data
				close=listOfAllCandle.get(j-1).getClose();//find the last candle close index is j-1 -- 
														//for the last index j give array out of bound 
														//because j increment by one then need to decrease j by 1 
				
				Candle candle=new Candle(localdatetime, 0, tradedQty, 1, open, high, low, close);// create new Object of Candle
				listOfTimeCandle.add(candle);
			}
		// return this List of combine candles
		return listOfTimeCandle;
	}
	


	
	@Override// find the Candle BreakOut Range
	public String findBreakOutRangeDateandTime(Integer time) throws Exception {
		
		// time must be a multiple of 5 minutes
		if(time%5!=0) {
			throw new Exception( " Please Pass correct Time or Pass Time as multiple of 5" );
		}
		
		List<Candle> listofCandles=candledao.findAll();// find the List Candle from database 
		
		int limitRangeTime=time/5;// limit represent the Range of Time ---- that means the last index of Time
		int endTimeRange=listofCandles.size();// find last time Index
		 
		
		Double low=Double.MAX_VALUE;
		Double high=Double.MIN_VALUE;
		
		//Find Out the low and High value In Given time Range of Limit
		for(int starttimeindex=0;starttimeindex<limitRangeTime;starttimeindex++) {
			//find low - high in Time Range
			if(low>listofCandles.get(starttimeindex).getLow()) {
				low=listofCandles.get(starttimeindex).getLow();
			}
			//find the high
			if(high<listofCandles.get(starttimeindex).getHigh()) {
				high=listofCandles.get(starttimeindex).getHigh();
			}	
		}
		
		// find Break out Range--- after the limit time
		for(int i=limitRangeTime;i<endTimeRange;i++) {
			
			Double close=listofCandles.get(i).getClose();
			if(close>high || close<low) {
				return "ORB Candle Generated at "+ listofCandles.get(i).getLastTradeTime().toString();
			}
		}
		//if Not Found other wise throw an Exception
		throw new CandleException(" no ANy Braekout is Found it");
		
	}


	
	

	

}
