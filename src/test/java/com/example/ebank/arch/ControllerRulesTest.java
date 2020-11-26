package com.example.ebank.arch;

import static com.tngtech.archunit.core.domain.JavaClass.Functions.GET_PACKAGE_NAME;
import static com.tngtech.archunit.core.domain.JavaMember.Predicates.declaredIn;
import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.annotatedWith;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.base.PackageMatchers;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMember;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

@AnalyzeClasses(packages = "com.example.ebank", importOptions = { ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class, ImportOption.DoNotIncludeArchives.class })
public class ControllerRulesTest {
    
    // @ArchTest
    // static final ArchRule controllers_should_implement_openapi_interface =
    // classes().that().resideInAPackage("..controller..").and().areAnnotatedWith(RestController.class)
    // .should().
    // .dependOnClassesThat().
    // .onlyCallMethodsThat(areDeclaredInController().or(are(annotatedWith(Secured.class))));
    
    // @ArchTest
    // static final ArchRule controllers_should_implement_openapi_interface =
    // classes().that().resideInAPackage("..controllers..").and().areAnnotatedWith(RestController.class).should().
    
    @ArchTest
    static final ArchRule controllers_should_only_call_secured_methods = classes().that()
            .resideInAPackage("..controller..")
            .should()
            .onlyCallMethodsThat(areDeclaredInController().or(are(annotatedWith(Secured.class))));
    
    @ArchTest
    static final ArchRule controllers_should_only_call_secured_constructors = classes()
            .that()
            .resideInAPackage("..controller..")
            .should()
            .onlyCallConstructorsThat(areDeclaredInController().or(are(annotatedWith(Secured.class))));
    
    @ArchTest
    static final ArchRule controllers_should_only_call_secured_code_units = classes()
            .that()
            .resideInAPackage("..controller..")
            .should()
            .onlyCallCodeUnitsThat(areDeclaredInController().or(are(annotatedWith(Secured.class))));
    
    @ArchTest
    static final ArchRule controllers_should_only_access_secured_fields = classes()
            .that()
            .resideInAPackage("..controller..")
            .should()
            .onlyAccessFieldsThat(areDeclaredInController().or(are(annotatedWith(Secured.class))));
    
    @ArchTest
    static final ArchRule controllers_should_only_access_secured_members = classes()
            .that()
            .resideInAPackage("..controller..")
            .should()
            .onlyAccessMembersThat(areDeclaredInController().or(are(annotatedWith(Secured.class))));
    
    private static DescribedPredicate<JavaMember> areDeclaredInController() {
        DescribedPredicate<JavaClass> aPackageController = GET_PACKAGE_NAME.is(PackageMatchers.of("..controller..", "java.."))
                .as("a package '..controller..'");
        return are(declaredIn(aPackageController));
    }
    
}
