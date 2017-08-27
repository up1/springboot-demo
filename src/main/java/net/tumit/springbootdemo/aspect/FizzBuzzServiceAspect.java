package net.tumit.springbootdemo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class FizzBuzzServiceAspect {

	@Around("execution(* net.tumit.springbootdemo.service.FizzBuzzService.say())")
	public Object aroundSampleCreation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		StopWatch watch = new StopWatch();
		try {
			watch.start();
			return proceedingJoinPoint.proceed();
		}finally {
			watch.stop();
			System.out.println("time={}" + watch.getLastTaskTimeMillis());
		}
	}

}
