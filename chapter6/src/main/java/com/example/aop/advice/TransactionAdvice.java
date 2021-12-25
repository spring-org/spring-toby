package com.example.aop.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice
        // 스프링의 어드바이스 인터페이스를 구현
        implements MethodInterceptor {
    
    private PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    // 타깃을 호출하는 기능을 가진 콜백 오브젝트를 프록시로부터 받는다.
    // 덕분에 어드바이스는 특정 타깃에 의존하지 않고 재사용이 가능하다.
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TransactionStatus transactionStatus = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            // 콜백을 호출해서 타깃의 메서드를 실행.
            // 타깃 메서드 호출 전후로 필요한 부가기능을 넣을 수 있다.
            // 경우에 따라서 타깃이 아예 호출되지 않게 하거나 재시도를 위한 반복적인 호출도 가능.
            Object ret = invocation.proceed();
            this.transactionManager.commit(transactionStatus);
            return ret;
        } catch (RuntimeException e) {
            // JDK 다니나믹 프록시가 제공하는 Method와는 달리
            // 스프링의 MethodInvocation을 통한 타깃 호출은 예외가 포장되지 않고 타깃에서 보낸 그대로 전달.
            this.transactionManager.rollback(transactionStatus);
            throw e;
        }
    }
}
