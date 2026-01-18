package com.library.libraryprod.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect that logs execution time for public methods in the libraryservice package.
 * Helps identify slow service methods without modifying their implementation.
 */
@Aspect
@Component
public class ExecutionTimeAspect {

    private static final Logger log = LoggerFactory.getLogger(ExecutionTimeAspect.class);

    /**
     * Around advice that measures and logs method execution time.
     *
     * @param proceedingJoinPoint the join point representing the intercepted method
     * @return the method's return value
     * @throws Throwable if the intercepted method throws an exception
     */
    @Around("execution(public * com.library.libraryprod.libraryservice..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return proceedingJoinPoint.proceed();
        } finally {
            long durationMs = System.currentTimeMillis() - start;
            log.info("ExecutionTime method={} durationMs={}",
                    proceedingJoinPoint.getSignature().toShortString(),
                    durationMs);
        }
    }
}