package ch.fhnw.edu.eaf.rentalmgmt.aspects;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogEntryAndExitAspect {

	@Before("execution(* ch.fhnw.edu.eaf..*.*(..))")
	public void logBeforeAllMethods(JoinPoint jp) {
		System.out.println(System.currentTimeMillis() + " | Before execution, called by " + jp.getSignature());
	}

	@After("execution(* ch.fhnw.edu.eaf..*.*(..))")
	public void logAfterAllMethods(JoinPoint jp) {
		System.out.println(System.currentTimeMillis() + " | After execution, called by " + jp.getSignature());
	}

	@Before("within(ch.fhnw.edu.eaf..*)")
	public void logBeforeAllMethodsWithin(JoinPoint jp) {
		System.out.println(System.currentTimeMillis() + " | Before within, called by " + jp.getSignature());
	}

	@After("within(ch.fhnw.edu.eaf..*)")
	public void logAfterAllMethodsWithin(JoinPoint jp) {
		System.out.println(System.currentTimeMillis() + " | After within, called by " + jp.getSignature());
	}
	
	
	@Around("execution(* ch.fhnw.edu.eaf..*.*(..))")
	public Object logAllMethodsAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println(System.currentTimeMillis() + " | Before Around, called by " + pjp.getSignature());
		Object retVal = pjp.proceed();
		System.out.println(System.currentTimeMillis() + " | After Around, called by " + pjp.getSignature());
		return retVal;
	}
}
