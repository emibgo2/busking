package com.example.demo.config;



import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Aop {
/**
 * 필요 할때만 AOP 할것, 로그 너무 많이 차지함
 */
//    @Around("execution(* com.example.demo..*(..))")
//    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
//        long start = System.currentTimeMillis();
//        System.out.println("START: " + joinPoint.toShortString());
//        try {
//            return joinPoint.proceed();
//        }
//        finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("END: " + joinPoint.toShortString()+ "  "+ timeMs+ "ms");
//        }
//    }
}

