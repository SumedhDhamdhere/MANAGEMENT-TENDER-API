package com.fresco.tenderManagement.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static int testCaseCounter = 1; // Counter to keep track of test cases
    private static boolean isTestCaseLogged = false; // Flag to avoid duplicate logging per test

    @Before("execution(* com.fresco.tenderManagement..*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        if (isTestCaseLogged) return;

        String methodSignature = joinPoint.getSignature().toShortString();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String testCaseName = "Test Case " + testCaseCounter;
        String logMessage = testCaseName + " - Before Method Execution: " +
                            className + "." + methodSignature + 
                            " at " + System.currentTimeMillis();

       // logger.info(logMessage);
        isTestCaseLogged = true;
    }

    @After("execution(* com.fresco.tenderManagement..*.*(..))")
    public void logAfterMethod(JoinPoint joinPoint) {
        String methodSignature = joinPoint.getSignature().toShortString();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String testCaseName = "Test Case " + testCaseCounter;
        String logMessage = testCaseName + " - After Method Execution: " +
                            className + "." + methodSignature + 
                            " at " + System.currentTimeMillis();

       // logger.info(logMessage);
        isTestCaseLogged = false;
        testCaseCounter++;
    }
}
