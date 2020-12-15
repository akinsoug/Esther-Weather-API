package com.tts.WeatherApp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.WeatherApp.model.Request;
import com.tts.WeatherApp.model.Response;
import com.tts.WeatherApp.model.ZipCode;
import com.tts.WeatherApp.repository.ZipCodeRepository;
import com.tts.WeatherApp.service.WeatherService;

@Controller
public class WeatherController {
    @Autowired
    private WeatherService weatherService;
    

	@Autowired
	ZipCodeRepository zipCodeRepository;
    
//    @GetMapping
//    public String getIndex(Model model) {
//        Response response = weatherService.getForecast("43210");
//        model.addAttribute("data", response);
//        return "weatherApi/index";
//    }
    
   
    
  @GetMapping(value = "/")
  public String getIndex(Model model) {
		model.addAttribute("request", new Request());
      return "weatherApi/index";
  }
    
    
    @PostMapping
    public String postIndex(Request request, Model model) {
    	
    	String theZipCode = request.getZipCode();
		weatherService.saveZipCode(theZipCode);
		
    	System.out.println("\n\n\n \t request.getZipCode() : " + request.getZipCode());
    	
        Response data = weatherService.getForecast(request.getZipCode());
    	System.out.println("\n\n\n data \t " + data);
        model.addAttribute("data", data);

    	List<ZipCode> zipCodeList = new ArrayList<>();
					    	zipCodeList = (List<ZipCode>) zipCodeRepository.findAll();
					    	System.out.println("\n\n \t zipCodeList: -------------- \t" + zipCodeList);
    	
		model.addAttribute("zipCodeList", zipCodeList);
        return "weatherApi/index";
    }
    
    /**
     * Puts an empty list in the model that thymeleaf can use to sum up the cart total.
     */
    @ModelAttribute("list")
    public List<Double> list() {
        return new ArrayList<>();
    }

    @GetMapping("/zipCodes")
    public String showZipCode(Model model) {
    	
		System.out.println("\n\n\t zipCodeRepository.findAll()\t"+ zipCodeRepository.findAll());
		
    	List<ZipCode> zipCodeList = new ArrayList<>();
    	zipCodeList = (List<ZipCode>) zipCodeRepository.findAll();
    	System.out.println("\n\n \t zipCodeList: \t" + zipCodeList);
    	
		model.addAttribute("zipCodeList", zipCodeList);
        return "weatherApi/index";
    }

//    @PostMapping("/cart")
//    public String addToCart(@RequestParam long id) {
//        Product p = productService.findById(id);
//		Map<Product, Integer> cart = cart();
//        setQuantity(p, cart().getOrDefault(p, 0) + 1);
//        return "ecommerce/cart";
//    }
   
}