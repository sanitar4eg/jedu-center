package edu.netcracker.center.config;

import edu.netcracker.center.web.mvc.ExtendedRequestMappingHandlerMapping;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.inject.Inject;

public class RequestMappingConfiguration extends DelegatingWebMvcConfiguration {

    /**
     * Configuration for custom @RequestMapping condition
     */
    /*@Override
    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new ExtendedRequestMappingHandlerMapping();
    }*/
    //    @Bean
//    public BeanPostProcessor requestMappingHandlerMappingPostProcessor() {
//        return new BeanPostProcessor() {
//            @Override
//            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//                if (bean instanceof RequestMappingHandlerMapping && "requestMappingHandlerMapping".equals(beanName)) {
//                    LoggerFactory.getLogger(RequestMappingConfiguration.class).info("Bean name is {}", beanName);
//                    RequestMappingHandlerMapping customized = new ExtendedRequestMappingHandlerMapping();
//                    customized.setApplicationContext(((RequestMappingHandlerMapping) bean).getApplicationContext());
//                    customized.setOrder(0);
//                    return customized;
//                }
//                return bean;
//            }
//
//            @Override
//            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//                return bean;
//            }
//        };
//    }


}
