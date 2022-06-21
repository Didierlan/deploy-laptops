package com.example.obrestexercise.controller;

import com.example.obrestexercise.Repository.LaptopRepository;
import com.example.obrestexercise.entitis.Laptop;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    //este objeto nos va permitir utilizar los metodos http
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;


    //aca inllectamos el puerto generado a la zar
    @LocalServerPort
    private int port;


    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://Localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);

    }

    @Test
    void findAll() {


        ResponseEntity<Laptop[]> response = testRestTemplate.getForEntity("/api/laptos", Laptop[].class);
        assertEquals(HttpStatus.OK,response.getStatusCode());

        //convertir un array a arrayList
        List<Object> books =  Arrays.asList(response.getBody());
        System.out.println(books.size());
    }

    @Test
    void findOneById() {
        ResponseEntity<Laptop> response = testRestTemplate.getForEntity("/api/laptos/1", Laptop.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());


    }

    @Test
    void create() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String json = """
                 {
                    "mark": "Hp",
                    "peso": 23.4,
                    "processor": "intel-core-i5"
                  }
                """;

        String json2 = """
                 {
                    "mark": "Hp-12",
                    "peso": 13.4,
                    "processor": "intel-core-i9"
                  }
                """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);
        HttpEntity<String> request2 = new HttpEntity<>(json2,headers);

        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptos",HttpMethod.POST,request,Laptop.class);

        ResponseEntity<Laptop> response2 = testRestTemplate.exchange("/api/laptos",HttpMethod.POST,request2,Laptop.class);
        Laptop laptop  = response.getBody();

        assertEquals(1L,laptop.getId());

    }


    @Test
    void  update(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String json = """
                 {
                    "id": 1,
                    "mark": "Hp_9045D",
                    "peso": 23.4,
                    "processor": "intel-core-i7"
                  }
                """;


        HttpEntity<String> request = new HttpEntity<>(json,headers);

        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptos",HttpMethod.PUT,request,Laptop.class);

        Laptop laptop  = response.getBody();
        assertEquals(1L,laptop.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());


    }


    @Test
    void deleteById(){
        testRestTemplate.delete("/api/laptos/2",Laptop.class);
        ResponseEntity<Laptop> response = testRestTemplate.getForEntity("/api/laptos/2", Laptop.class);
        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());


    }



//    @Test
//    void delete(){
//         testRestTemplate.delete("/api/laptos",Laptop.class);
//        ResponseEntity<Laptop> response = testRestTemplate.getForEntity("/api/laptos", Laptop.class);
//        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
//
//    }


}