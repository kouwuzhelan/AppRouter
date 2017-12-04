package com.sudi.route.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;
import com.sudi.route.annotation.Interceptor;
import com.sudi.route.compiler.utils.Constans;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by sudi on 2017/11/17.
 * Email：sudi@yiche.com
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.sudi.route.annotation.Interceptor"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class InterceptorProcess extends AbstractProcessor {
    private String mModuleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mModuleName = processingEnv.getOptions().get(Constans.OPTION_MODULE_NAME);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Interceptor.class);
        if (elements == null || elements.isEmpty()) {
            return true;
        }
        // 合法的TypeElement集合
        Set<TypeElement> typeElements = new HashSet<>();
        for (Element element : elements) {
            if (validateElement(element)) {
                typeElements.add((TypeElement) element);
            } else {
//                mLogger.error(element, String.format("The annotated element is not a implementation class of %s",
//                        Constans.INTERCEPTOR_INTERFACE));
            }
        }

        if (mModuleName != null) {
            String validModuleName = mModuleName.replace(".", "_").replace("-", "_");
            generateInterceptors(validModuleName, typeElements);
        } else {
            throw new RuntimeException(String.format("No option `%s` passed to Interceptor annotation processor.", Constans.OPTION_MODULE_NAME));
        }
        return true;
    }

    private boolean validateElement(Element element) {
        return element.getKind().isClass() &&
                processingEnv.getTypeUtils().isAssignable(
                        element.asType(),
                        processingEnv.getElementUtils().getTypeElement(Constans.INTERCEPTOR_INTERFACE).asType());
    }

    private void generateInterceptors(String moduleName, Set<TypeElement> elements) {
        /*
         * params
         */
        TypeElement interceptorType = processingEnv.getElementUtils().getTypeElement(Constans.INTERCEPTOR_INTERFACE);
        // Map<String, Class<? extends RouteInterceptor>> map
        ParameterizedTypeName mapTypeName = ParameterizedTypeName.get(ClassName.get(Map.class),
                ClassName.get(String.class), ParameterizedTypeName.get(ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(ClassName.get(interceptorType))));
        ParameterSpec mapParameterSpec = ParameterSpec.builder(mapTypeName, "map").build();
        /*
         * method
         */
        MethodSpec.Builder handleInterceptors = MethodSpec.methodBuilder(Constans.HANDLE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(mapParameterSpec);
        for (TypeElement element : elements) {
//            mLogger.info(String.format("Found interceptor: %s", element.getQualifiedName()));
            Interceptor interceptor = element.getAnnotation(Interceptor.class);
            String name = interceptor.value();
            handleInterceptors.addStatement("map.put($S, $T.class)", name, ClassName.get(element));
        }

        /*
         * class
         */
        TypeSpec type = TypeSpec.classBuilder(capitalize(moduleName) + Constans.INTERCEPTOR_TABLE)
                .addSuperinterface(ClassName.get(Constans.PACKAGE_NAME, Constans.INTERCEPTOR_TABLE))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(handleInterceptors.build())
                .addJavadoc(Constans.CLASS_JAVA_DOC)
                .build();

        try {
            JavaFile.builder(Constans.PACKAGE_NAME, type).build().writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String capitalize(CharSequence self) {
        return self.length() == 0 ? "" :
                "" + Character.toUpperCase(self.charAt(0)) + self.subSequence(1, self.length());
    }
}
