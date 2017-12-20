//package com.playtika.carshop.service.external.domain.http;
//
//import com.playtika.carshop.service.external.AuthorService;
//import com.playtika.carshop.service.external.domain.AuthorCar;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//@AllArgsConstructor
//public class HttpAuthorServiceImpl implements AuthorService {
//    private RestTemplateBuilder restTemplateBuilder;
//    @Override
//    public AuthorCar getAuthorCarData(String name) {
//        RestTemplate restTemplate = restTemplateBuilder.build();
//
//        return restTemplate.getForObject("http://localhost:8080/api/library/{author}",AuthorCar.class, name);
//    }
//}
