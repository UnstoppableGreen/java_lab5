package ru.rsatu;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.arc.InjectableBean;
import io.quarkus.arc.impl.ArcContainerImpl;
import io.quarkus.vertx.http.runtime.devmode.Json;
import io.quarkus.vertx.http.runtime.devmode.Json.JsonArrayBuilder;
import io.quarkus.vertx.http.runtime.devmode.Json.JsonObjectBuilder;

@ApplicationScoped
@Path("/hello")
public class GreetingResource {


    @Inject @Default
    private IsbnGenerator numberGenerator13_1;
    
    @Inject @ThirteenDigits
    private NumberGenerator numberGenerator13_2;
    
    @Inject @EightDigits
    private NumberGenerator numberGenerator8;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

    	  return "Inject Default: "+ numberGenerator13_1.generateNumber()+" || Inject ThirteenDigits: "+ numberGenerator13_2.generateNumber()
    	  +" || Inject EightDigits: "+ numberGenerator8.generateNumber() + "\n\n BEANS: \n\n\n"
    	  + getBeans("ru.rsatu")+ "\n\n\n"+ getBeans("ru.rsatu.NumberGenerator");
    }
    
    public String getBeans(String beanClass) {
    	JsonArrayBuilder array = Json.array();

        ArcContainerImpl container = ArcContainerImpl.instance();
        List<InjectableBean<?>> beans = container.getBeans();
        beans.addAll(container.getInterceptors());


        String kindParam = null;
       InjectableBean.Kind kind = kindParam != null ? InjectableBean.Kind.from(kindParam.toUpperCase()) : null;

        
        String scopeEndsWith = null;
        String beanClassStartsWith = beanClass;

        for (Iterator<InjectableBean<?>> it = beans.iterator(); it.hasNext();) {
            InjectableBean<?> injectableBean = it.next();
            if (kind != null && !kind.equals(injectableBean.getKind())) {
                it.remove();
            }
            if (scopeEndsWith != null && !injectableBean.getScope().getName().endsWith(scopeEndsWith)) {
                it.remove();
            }
            if (beanClassStartsWith != null
                    && !injectableBean.getBeanClass().getName().startsWith(beanClassStartsWith)) {
                it.remove();
            }
        }

        for (InjectableBean<?> injectableBean : beans) {
            JsonObjectBuilder bean = Json.object();
            bean.put("id", injectableBean.getIdentifier());
            bean.put("kind", injectableBean.getKind().toString());
            bean.put("generatedClass", injectableBean.getClass().getName());
            bean.put("beanClass", injectableBean.getBeanClass().getName());
            JsonArrayBuilder types = Json.array();
            for (Type beanType : injectableBean.getTypes()) {
                types.add(beanType.getTypeName());
            }
            bean.put("types", types);
            JsonArrayBuilder qualifiers = Json.array();
            for (Annotation qualifier : injectableBean.getQualifiers()) {
                if (qualifier.annotationType().equals(Any.class) || qualifier.annotationType().equals(Default.class)) {
                    qualifiers.add("@" + qualifier.annotationType().getSimpleName());
                } else {
                    qualifiers.add(qualifier.toString());
                }
            }
            bean.put("qualifiers", qualifiers);
            bean.put("scope", injectableBean.getScope().getName());

            if (injectableBean.getDeclaringBean() != null) {
                bean.put("declaringBean", injectableBean.getDeclaringBean().getIdentifier());
            }
            if (injectableBean.getName() != null) {
                bean.put("name", injectableBean.getName());
            }
            if (injectableBean.isAlternative()) {
                bean.put("alternativePriority", injectableBean.getAlternativePriority());
            }
            if (injectableBean.isDefaultBean()) {
                bean.put("isDefault", true);
            }
            array.add(bean);
        }
     return array.build();
    }

}  