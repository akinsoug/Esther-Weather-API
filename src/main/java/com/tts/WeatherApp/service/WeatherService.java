package com.tts.WeatherApp.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import lombok.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.tts.WeatherApp.model.Response;
import com.tts.WeatherApp.model.ZipCode;
import com.tts.WeatherApp.repository.ZipCodeRepository;


@Service
public class WeatherService {
	@Autowired
	ZipCodeRepository zipCodeRepository;
	
    @Value("${api_key}")
    private String apiKey;
    
    public Response getForecast(String zipCode) {
    	
//    	saveZipCode(zipCode);	//------------------------
    	
        String url = "http://api.openweathermap.org/data/2.5/weather?zip=" +  zipCode + "&units=imperial&appid=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();

    	System.out.println("\n\n\n response: \t " + 1111111);
//        return restTemplate.getForObject(url, Response.class);
    	
        try {
        	Response response = restTemplate.getForObject(url, Response.class);
//        	System.out.println("\n\n\n response: \t " + response);
            return response;
        }
        catch (HttpClientErrorException ex) {
        	System.out.println("\n\n\n response: \t " + ex.getMessage());
            Response response = new Response();
            response.setName("error");
            return response;
        }
    } // getForecast-------------------------
    
    public void saveZipCode(String zipCode) {
    	if (!(zipCode.isEmpty())) {
    		
        	ZipCode myZipCode = new ZipCode();
        	myZipCode.setZipCode(zipCode);

        	List<ZipCode> zipCodeList = new ArrayList<>();
        	zipCodeList = (List<ZipCode>) zipCodeRepository.findAll();
        	
        	HashSet<ZipCode> myZipCodeSet = new HashSet<>(zipCodeList);
        	
        	if (!myZipCodeSet.contains(myZipCode)) {
            	zipCodeRepository.save(myZipCode);
			}
        	
        	Iterable<ZipCode> myIterable = myZipCodeSet;
        	zipCodeRepository.saveAll(myIterable);
        	
//
    		System.out.println("\n\n\t myZipCodeSet *****\t"+ myZipCodeSet +"\n\n" + zipCodeList);
        	
		}
	}
    
}