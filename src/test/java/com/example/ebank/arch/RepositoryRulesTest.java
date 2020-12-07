package com.example.ebank.arch;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Repository;

@AnalyzeClasses(packages = "com.example.ebank", importOptions = { ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class, ImportOption.DoNotIncludeArchives.class })
public class RepositoryRulesTest {
    
    @ArchTest
    static final ArchRule repository_should_implement_spring_interface = classes()
            .that()
            .resideInAPackage("..repositories..")
            .should()
            .dependOnClassesThat(resideInAPackage("..org.springframework.data.repository.."))
            .because("Repository has to extend interface from org.springframework.data.repository.* ");
    
    @ArchTest
    static final ArchRule repository_should_be_interface = classes()
            .that()
            .resideInAPackage("..repositories..")
            .should()
            .beInterfaces()
            .because("Repository has to be an interface");
    
    @ArchTest
    static final ArchRule repository_should_have_annotation = classes()
            .that()
            .resideInAPackage("..repositories..")
            .should()
            .beAnnotatedWith(Repository.class);
    
}