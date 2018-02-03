package ch.fhnw.edu.eaf.rentalmgmt.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import ch.fhnw.edu.eaf.rentalmgmt.domain.Rental;
import ch.fhnw.edu.eaf.rentalmgmt.web.dto.RentalDTO;

@Aspect
@Component
public class LogMethodsWithArgAspect {

	@Before("execution(* *..rentalmgmt..*Controller.*(..)) && args(id)")
	public void logIdif1(JoinPoint jp, Long id) {
		if (id == 1) {
		System.out.println("Method called jp: " +
				jp.getSignature() + 
				" with Arg: " + id.toString());
		}
		
	}
	
	@AfterReturning(pointcut = "execution(* findOne(..))", returning = "rental")
	public void logFindOne(Rental rental) {
		System.out.println("returning : " +rental.getId());		
	}
	
	@AfterReturning(pointcut = "execution(* findById(..))", returning = "reRental")
	public void logFindById(ResponseEntity<RentalDTO> reRental) {
		System.out.println("returning findbyid: " +reRental.getBody().getId());		
	}
}
