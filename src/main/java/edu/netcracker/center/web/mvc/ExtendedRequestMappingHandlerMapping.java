package edu.netcracker.center.web.mvc;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

public class ExtendedRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        RoleMapping typeAnnotation = AnnotationUtils.findAnnotation(handlerType, RoleMapping.class);
        return createCondition(typeAnnotation);
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        RoleMapping methodAnnotation = AnnotationUtils.findAnnotation(method, RoleMapping.class);
        return createCondition(methodAnnotation);
    }

    private RequestCondition<?> createCondition(RoleMapping accessMapping) {
        return (accessMapping != null) ? new RolesRequestCondition((accessMapping.value())) : null;
    }
}
