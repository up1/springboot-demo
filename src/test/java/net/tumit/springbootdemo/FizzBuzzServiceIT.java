package net.tumit.springbootdemo;

import net.tumit.springbootdemo.service.FizzBuzzService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FizzBuzzServiceIT {

	@Autowired
	private FizzBuzzService fizzBuzzService;

	@Test
	public void say_fizzbuzz() throws Exception {

		// act
		String result = fizzBuzzService.say();

		// assert
		assertThat(result).isEqualTo("fizzbuzz");

	}
}
