package com.example.obrestexercise;

import com.example.obrestexercise.Repository.LaptopRepository;
import com.example.obrestexercise.entitis.Laptop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ObRestExerciseApplication {

	public static void main(String[] args) {

		ApplicationContext context =  SpringApplication.run(ObRestExerciseApplication.class, args);

		LaptopRepository repository = (LaptopRepository) context.getBean(LaptopRepository.class);

		Laptop laptop1 = new Laptop(null,"Azer", "RYZEN-5",12.3);
		Laptop laptop2 = new Laptop(null,"alienware", "RYZEN-9",17.3);

		repository.save(laptop1);
		repository.save(laptop2);

	}

}
