package ajbc.learn.springboot.bootDemo.aspects;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import ajbc.learn.springboot.bootDemo.daos.DaoException;



@Aspect
@Component
public class MyAspectSupplier {

	public MyAspectSupplier() {
//		System.out.println("Aspect calling to main");
	}
	
	
	//this is an advice method
	//syntax: (? means optional)
	// "execution(modifier? return-type? method-pattern(arg-type, arg-type, ..))"
	@Before("execution(* ajbc.learn.springboot.bootDemo.daos.SupplierDao.*(..))")
	public void logBeforeCalling(JoinPoint joinPoint) {
		System.out.println("Aspect is writing to logger method name: "+joinPoint.getSignature().getName());
		System.out.println("args are "+Arrays.toString(joinPoint.getArgs()));
	}
	
	
	
	@AfterThrowing(throwing = "ex", pointcut = "execution(* ajbc.learn.springboot.bootDemo.daos.SupplierDao.*(..))")
	public void convertToDaoException(Throwable ex) throws DaoException {
		throw new DaoException(ex);
	}
	
	
	
	
	
}