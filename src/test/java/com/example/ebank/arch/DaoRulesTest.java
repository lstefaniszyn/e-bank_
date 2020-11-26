package com.example.ebank.arch;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods;

import java.sql.SQLException;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.example.ebank", importOptions = { ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class, ImportOption.DoNotIncludeArchives.class })
public class DaoRulesTest {
    
    @ArchTest
    static final ArchRule entities_must_reside_in_a_model_package = classes().that()
            .areAnnotatedWith(Entity.class)
            .should()
            .resideInAPackage("..models..")
            .as("Entities should reside in a package '..models..'");
    
}
