package com.example.ebank.arch;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@AnalyzeClasses(packages = "com.example.ebank", importOptions = { ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class, ImportOption.DoNotIncludeArchives.class })

public class NamingConvention {
    
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
    
    @ArchTest
    static ArchRule repository_should_be_suffixed = classes()
            .that()
            .resideInAPackage("..repositories..")
            .should()
            .haveSimpleNameEndingWith("Repository");
    
}
