package com.example.obrestexercise.controller;


import com.example.obrestexercise.Repository.LaptopRepository;
import com.example.obrestexercise.entitis.Laptop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {

    private final Logger log = LoggerFactory.getLogger(LaptopController.class);

    LaptopRepository laptopRepository;

    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }


    //muestra todas las laptops de la database
    @GetMapping("/api/laptos")
    public ResponseEntity<List<Laptop>> findAll(){
        if(laptopRepository.findAll().size() <= 0){

            log.warn("no se encuintran laptos en la batabase");
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(laptopRepository.findAll());

    }

    //Busca una laptop port su id
    @GetMapping("/api/laptos/{id}")
    public ResponseEntity<Laptop> findOneById(@PathVariable Long id){

        Optional<Laptop> laptoOpt =  laptopRepository.findById(id);

        if(laptopRepository.findAll().size() <= 0){

            log.warn("no se encuintran laptos en la batabase");
            return ResponseEntity.noContent().build();

        }
        if(!laptoOpt.isPresent()){
            log.warn("no se encuintra el id de la lapto en la batabase");
            return ResponseEntity.noContent().build();

        }

        Laptop laptop = laptoOpt.get();
        return ResponseEntity.ok(laptop);


    }






    //Agregar laptop a database
    @PostMapping("/api/laptos")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop){

        if(laptop.getId() != null){
            log.warn("tratando de agregar una laptop con un id");
            return  ResponseEntity.badRequest().build();

        }

        return ResponseEntity.ok(laptopRepository.save(laptop));


    }



    //Actualizar una laptop existente en la database
    @PutMapping("/api/laptos")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop){

        if(laptop.getId() == null){
            log.warn("trying to update a non exist Laptop");
            return  ResponseEntity.badRequest().build();

        }

        if(!laptopRepository.existsById(laptop.getId())){
            log.warn("trying to update a non exist Laptop");
            return  ResponseEntity.badRequest().build();

        }


        laptopRepository.save(laptop);
        return ResponseEntity.ok(laptop);



    }


    //Eliminar todas las laptops
    @DeleteMapping("/api/laptos")
    public ResponseEntity<Laptop> delete(){
        if(laptopRepository.findAll().size() <= 0){
            log.warn("no se encuintran laptos en la batabase");
            return ResponseEntity.notFound().build();

        }

        laptopRepository.deleteAll();
        return  ResponseEntity.noContent().build();

    }




    //Eliminar una Laptop por su id
    @DeleteMapping("/api/laptos/{id}")
    public ResponseEntity<Laptop> deleteById(@PathVariable Long id){

        Optional<Laptop> laptoOpt = laptopRepository.findById(id);

        if(id == null){
            log.warn("trying to delete a non exist id");
            return ResponseEntity.notFound().build();

        }

        if(!laptoOpt.isPresent()){
            log.warn("trying to delete a non exist id in database");
            return ResponseEntity.notFound().build();

        }

        laptopRepository.delete(laptoOpt.get());
        return ResponseEntity.noContent().build();

    }


}
