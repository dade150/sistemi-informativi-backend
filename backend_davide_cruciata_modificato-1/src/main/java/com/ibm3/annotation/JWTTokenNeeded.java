package com.ibm3.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.ws.rs.NameBinding;
//va su Corso controller perche deve essere accessibile dall admin
@NameBinding 
//specifica che secured deve essere convertita in fase di runtime
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface JWTTokenNeeded {

}
