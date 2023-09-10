package com.reactive.learning.reactive;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

@SpringBootTest
class ReactiveApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void createFlux_just(){
		Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");

		fruitFlux.subscribe(
				c -> System.out.println("Hello " + c)
		);

		StepVerifier.create(fruitFlux)
				.expectNext("Apple")
				.expectNext("Orange")
				.expectNext("Grape")
				.expectNext("Banana")
				.expectNext("Strawberry")
				.verifyComplete();
	}

	@Test
	public void createFlux_fromArray(){
		String[] fruits = new String[] {
				"Apple", "Orange", "Grape", "Banana", "Strawberry"
		};

		Flux<String> fruitFlux = Flux.fromArray(fruits);

		StepVerifier.create(fruitFlux)
				.expectNext("Apple")
				.expectNext("Orange")
				.expectNext("Grape")
				.expectNext("Banana")
				.expectNext("Strawberry")
				.verifyComplete();
	}

	@Test
	public void createFlux_range(){
		Flux<Integer> intervalFlux =
				Flux.range(1, 5);

		StepVerifier.create(intervalFlux)
				.expectNext(1)
				.expectNext(2)
				.expectNext(3)
				.expectNext(4)
				.expectNext(5)
				.verifyComplete();
	}

	@Test
	public void mergeFluxes(){

		Flux<String> characterFlux = Flux
				.just("Garfield", "Kojak", "Barbossa");

		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples");

		Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);
		mergedFlux.subscribe( c -> System.out.println("hello"+c));
	}

	@Test
	public  void zipFluxes(){
		Flux<String> characterFlux = Flux
				.just("Garfield", "Kojak", "Barbossa");
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples");

		Flux<Tuple2<String,String  >> zippedFluxes = Flux.zip(characterFlux,foodFlux);
		zippedFluxes.subscribe(System.out::println);

	}
	@Test
	public  void fluxSkip(){
		Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry").skip(2);
		fruitFlux.subscribe(System.out::println);

	}
	@Test
	public void fluxTake(){
		Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry").take(4);
		fruitFlux.subscribe(System.out::println);
	}

	@Test
	public  void fluxFilter(){
		Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry").filter(
				ff -> !ff.contains(" ")
		);
		fruitFlux.subscribe(System.out::println);
	}
	@Test
	public void map(){
		Flux<Player>  playersFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
				.map(s -> {
					String [] split = s.split(" ");
					return  new Player(split[0] ,split[1]);
				});
		playersFlux.subscribe(System.out::println);
	}

	@Data
	private static class Player {
		private final String firstName;
		private final String lastName;
	}

}
