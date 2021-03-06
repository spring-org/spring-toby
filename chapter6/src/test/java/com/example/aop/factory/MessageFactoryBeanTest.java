package com.example.aop.factory;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        MessageApplicationConfig.class
})
class MessageFactoryBeanTest {

    @Autowired
    private ApplicationContext context;

    @DisplayName("빈 객체 조회 테스트")
    @Test
    void testCase1() {
        Object message = context.getBean("message");

        assertThat(message).isInstanceOf(Message.class);
        assertThat(((Message) message).getText()).isEqualTo("Factory Bean");
    }

    // TODO 사용 불가 수정해야함
    @Disabled
    @DisplayName("팩토리 빈 조회 테스트")
    @Test
    void testCase2() {
        Object factory = context.getBean("$message");
        assertThat(factory).isInstanceOf(MessageFactoryBean.class);
    }
}