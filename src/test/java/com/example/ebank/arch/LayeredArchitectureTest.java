package com.example.ebank.arch;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.example.ebank", importOptions = { ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class, ImportOption.DoNotIncludeArchives.class })
public class LayeredArchitectureTest {
    
    
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
    
}
