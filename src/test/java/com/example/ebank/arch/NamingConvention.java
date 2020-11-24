package com.example.ebank.arch;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;
import static com.tngtech.archunit.library.freeze.FreezingArchRule.freeze;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@AnalyzeClasses(packages = "com.example.ebank")

public class NamingConvention {
    
    @ArchTest
    private final ArchRule no_generic_exceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
    
    @ArchTest
    private final ArchRule no_java_util_logging = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;
    
    @ArchTest
    private final ArchRule no_access_to_standard_streams = freeze(noClasses().should(ACCESS_STANDARD_STREAMS));
    
    @ArchTest
    static ArchRule classes_named_controller_should_be_in_a_controller_package = classes()
            .that()
            .areAnnotatedWith(RestController.class)
            .should()
            .resideInAPackage("..controllers..");
    
    @ArchTest
    static ArchRule controller_should_be_suffixed = classes()
            .that()
            .resideInAPackage("..controllers..")
            .and()
            .areAnnotatedWith(RestController.class)
            .should()
            .haveSimpleNameEndingWith("Controller")
            .orShould()
            .haveSimpleNameEndingWith("ControllerImpl");
    
    @ArchTest
    static ArchRule service_should_be_suffixed = classes()
            .that()
            .resideInAPackage("..services..")
            .and()
            .areAnnotatedWith(Service.class)
            .should()
            .haveSimpleNameEndingWith("Service")
            .orShould()
            .haveSimpleNameEndingWith("ServiceImpl");
    
}
