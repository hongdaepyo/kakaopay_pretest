package com.project.sprinkle.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class PerfAspect {
	
	@Around("execution(* com.project.sprinkle..*.*Service.*(..))")
	public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
		String methodName = pjp.getSignature().getName();
		
		log.info(methodName + " started.");
		
		Object retVal = pjp.proceed();
		
		log.info(methodName + " ended.");
		
		return retVal;
	}
}
