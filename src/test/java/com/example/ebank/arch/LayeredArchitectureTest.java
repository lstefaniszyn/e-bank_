package com.example.ebank.arch;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import org.junit.Test;

@AnalyzeClasses(packages = "com.example.ebank", importOptions = { ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class, ImportOption.DoNotIncludeArchives.class })
public class LayeredArchitectureTest {
    
    // @Test
    // public void Services_should_only_be_accessed_by_Controllers() {
    // // JavaClasses importedClasses = new ClassFileImporter().importPackages("com.mycompany.myapp");
    // JavaClasses importedClasses = new ClassFileImporter()
    // .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
    // .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
    // .importClasspath();
    
    // ArchRule myRule = classes()
    // .that()
    // .resideInAPackage("..service..")
    // .should()
    // .onlyBeAccessed()
    // .byAnyPackage("..controller..", "..service..");
    
    // myRule.check(importedClasses);
    // }
    
    @ArchTest
    static final ArchRule layer_dependencies_are_respected = layeredArchitecture()
            .layer("Controllers")
            .definedBy("com.example.ebank.controllers..")
            .layer("Services")
            .definedBy("com.example.ebank.services..")
            .layer("Repositories")
            .definedBy("com.example.ebank.repositories..")
            .whereLayer("Controllers")
            .mayNotBeAccessedByAnyLayer()
            .whereLayer("Services")
            .mayOnlyBeAccessedByLayers("Controllers")
            .whereLayer("Repositories")
            .mayOnlyBeAccessedByLayers("Services");
    
    // .ignoreDependency(RestBase.class, AccountService.class);
    // .ignoreDependency(origin, AppStatusController.class)
    // AsyncTransactionService
    // CustomerController
    // CustomerService
    // TransactionService
    
    // @ArchTest
    // static final ArchRule layer_dependencies_are_respected_with_exception = layeredArchitecture()
    
    // .layer("Controllers").definedBy("com.tngtech.archunit.example.layers.controller..")
    // .layer("Services").definedBy("com.tngtech.archunit.example.layers.service..")
    // .layer("Persistence").definedBy("com.tngtech.archunit.example.layers.persistence..")
    
    // .whereLayer("Controllers").mayNotBeAccessedByAnyLayer()
    // .whereLayer("Services").mayOnlyBeAccessedByLayers("Controllers")
    // .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Services")
    
    // .ignoreDependency(SomeMediator.class, ServiceViolatingLayerRules.class);
}
