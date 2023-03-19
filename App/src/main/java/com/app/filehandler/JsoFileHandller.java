package com.app.filehandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.stereotype.Service;
import com.app.model.CandlesDummy;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class JsoFileHandller {
	
	// Collect json Data from  file--
	public List<CandlesDummy> GetListofCandles() throws StreamReadException, DatabindException, IOException{
		
		
		ObjectMapper mapper = new ObjectMapper();
////	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		TypeReference<List<CandlesDummy>> typeReference = new TypeReference<List<CandlesDummy>>(){};// creating list of Candles Type Reference
		InputStream inputStream = TypeReference.class.getResourceAsStream("/json/data.json");// find file through file path
		List<CandlesDummy> listofcandles;
//		try {
			listofcandles = mapper.readValue(inputStream,typeReference);
			System.out.println(listofcandles);
//		} catch (IOException e){
//			System.out.println("Unable to save users: " + e.getMessage());
//		}
		return listofcandles;
	}
}
