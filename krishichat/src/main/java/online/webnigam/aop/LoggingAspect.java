package online.webnigam.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import online.webnigam.service.UserService;

@Component
@Aspect
public class LoggingAspect {
	@Autowired
	UserService userService;

	@Before("execution(* online.webnigam.controller.*.*(..)))")
	public void callBefore(JoinPoint joinPoint) {
		System.err.println(
				joinPoint.getSignature().getName() + " called with args : " + Arrays.toString(joinPoint.getArgs()));

	}

//	@AfterThrowing(pointcut = "execution(* online.webnigam.*.*.*(..))", throwing = "ex")
//	public void handleException(Exception ex) throws IOException {
//		System.out.println("my exception ---- " + ex.getMessage() + " called");
//
//	}
}
