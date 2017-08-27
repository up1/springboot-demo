package net.tumit.springbootdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FizzBuzzService {

	public String say() {
		log.debug("say");
		return "fizzbuzz";
	}

}
