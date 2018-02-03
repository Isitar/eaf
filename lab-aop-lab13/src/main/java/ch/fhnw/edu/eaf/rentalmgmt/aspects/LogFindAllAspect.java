package ch.fhnw.edu.eaf.rentalmgmt.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogFindAllAspect {

	@Before("execution(* findAll(..))")
	public void logFindAll(JoinPoint jp) {
		System.out.println("Findall called sig: " + jp.getSignature());
		
	}
}
