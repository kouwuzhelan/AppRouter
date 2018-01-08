package com.sudi.route.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;
import com.sudi.route.annotation.ActivityRouter;
import com.sudi.route.annotation.model.RouteInfo;
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
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/**
 * Created by sudi on 2017/11/17.
 * Email：sudi@yiche.com
 */

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.sudi.route.annotation.ActivityRouter"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RouteProcess extends AbstractProcessor {
    private String mModuleName;

    private final static String SCHEMA_FORMATER = "%s://%s/%s";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mModuleName = processingEnv.getOptions().get(Constans.OPTION_MODULE_NAME);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ActivityRouter.class);
        if (elements == null || elements.isEmpty()) {
            return true;
        }
        // 合法的TypeElement集合
        Set<TypeElement> typeElements = new HashSet<>();
        for (Element element : elements) {
            if (validateElement(element, Constans.FRAGMENT_V4_FULL_NAME) ||
                    validateElement(element, Constans.FRAGMENT_FULL_NAME) ||
                    validateElement(element, Constans.ACTIVITY_FULL_NAME)) {
                typeElements.add((TypeElement) element);
            }
        }


        if (mModuleName != null) {
            String validModuleName = mModuleName.replace(".", "_").replace("-", "_");
//            generateRoutePathTable(validModuleName, typeElements);
            generateRouteInfoTable(validModuleName, typeElements);
            generateTargetInterceptors(validModuleName, typeElements);
        } else {
            throw new RuntimeException(String.format("No option `%s` passed to Route annotation processor.", Constans.OPTION_MODULE_NAME));
        }
        return true;
    }

    private boolean validateElement(Element typeElement, String type) {
        if (!isSubtype(typeElement, type)) {
            return false;
        }
        Set<Modifier> modifiers = typeElement.getModifiers();
        // abstract class.
        if (modifiers.contains(Modifier.ABSTRACT)) {
            return false;
        }
        return true;
    }

    private boolean isSubtype(Element typeElement, String type) {
        return processingEnv.getTypeUtils().isSubtype(typeElement.asType(),
                processingEnv.getElementUtils().getTypeElement(type).asType());
    }

    /**
     * RoutePathTable.
     */
    private void generateRoutePathTable(String moduleName, Set<TypeElement> elements) {
        // Map<String, Class<?>> map
        ParameterizedTypeName mapTypeName = ParameterizedTypeName.get(ClassName.get(Map.class),
                ClassName.get(String.class), ParameterizedTypeName.get(ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(Object.class)));
        ParameterSpec mapParameterSpec = ParameterSpec.builder(mapTypeName, "map").build();

        MethodSpec.Builder methodHandle = MethodSpec.methodBuilder(Constans.HANDLE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(mapParameterSpec);
        for (TypeElement element : elements) {
//            ActivityRouter route = element.getAnnotation(ActivityRouter.class);
            ClassName className = ClassName.get(element);
            methodHandle.addStatement("map.put($S, $T.class)", className, className);
        }
        TypeElement interfaceType = processingEnv.getElementUtils().getTypeElement(Constans.ROUTE_PATH_TABLE_FULL_NAME);
        TypeSpec type = TypeSpec.classBuilder(capitalize(moduleName) + Constans.ROUTE_PATH_TABLE)
                .addSuperinterface(ClassName.get(interfaceType))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodHandle.build())
                .addJavadoc(Constans.CLASS_JAVA_DOC)
                .build();
        try {
            JavaFile.builder(Constans.PACKAGE_NAME, type).build().writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * RouteInfoTable.
     */
    private void generateRouteInfoTable(String moduleName, Set<TypeElement> elements) {
        // Map<String, RouteInfo> map
        ParameterizedTypeName mapTypeName = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ClassName.get(RouteInfo.class));
        ParameterSpec mapParameterSpec = ParameterSpec.builder(mapTypeName, "map").build();

        MethodSpec.Builder methodHandle = MethodSpec.methodBuilder(Constans.HANDLE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(mapParameterSpec);
        for (TypeElement element : elements) {
            ActivityRouter route = element.getAnnotation(ActivityRouter.class);
            // public RouteInfo build(Class cls,String classPath, String schema, String alias)
            methodHandle.addStatement("map.put($S, $T.build($T.class,$S,$S,$S))",
                    route.path(),
                    ClassName.get(RouteInfo.class),
                    ClassName.get(element),
                    ClassName.get(element),
                    String.format(SCHEMA_FORMATER, route.schema(), route.host(), route.path()),
                    route.path());
        }

        TypeElement interfaceType = processingEnv.getElementUtils().getTypeElement(Constans.ROUTE_INFO_TABLE_FULL_NAME);
        TypeSpec type = TypeSpec.classBuilder(capitalize(moduleName) + Constans.ROUTE_INFO_TABLE)
                .addSuperinterface(ClassName.get(interfaceType))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodHandle.build())
                .addJavadoc(Constans.CLASS_JAVA_DOC)
                .build();
        try {
            JavaFile.builder(Constans.PACKAGE_NAME, type).build().writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TargetInterceptors.
     */
    private void generateTargetInterceptors(String moduleName, Set<TypeElement> elements) {
        // Map<Class<?>, String[]> map
        ParameterizedTypeName mapTypeName = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ParameterizedTypeName.get(ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(Object.class)),
                TypeName.get(String[].class));
        ParameterSpec mapParameterSpec = ParameterSpec.builder(mapTypeName, "map").build();
        MethodSpec.Builder methodHandle = MethodSpec.methodBuilder(Constans.HANDLE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(mapParameterSpec);
        boolean hasInterceptor = false; // flag
        for (TypeElement element : elements) {
            ActivityRouter route = element.getAnnotation(ActivityRouter.class);
            String[] interceptors = route.interceptors();
            if (interceptors.length > 1) {
                hasInterceptor = true;
                StringBuilder sb = new StringBuilder();
                for (String interceptor : interceptors) {
                    sb.append("\"").append(interceptor).append("\",");
                }
                methodHandle.addStatement("map.put($T.class, new String[]{$L})",
                        ClassName.get(element), sb.substring(0, sb.lastIndexOf(",")));
            } else if (interceptors.length == 1) {
                hasInterceptor = true;
                methodHandle.addStatement("map.put($T.class, new String[]{$S})",
                        ClassName.get(element), interceptors[0]);
            }
        }
        if (!hasInterceptor) { // if there are no interceptors, ignore.
            return;
        }
        TypeSpec type = TypeSpec.classBuilder(capitalize(moduleName) + Constans.TABLE_INTERCEPTORS)
                .addSuperinterface(ClassName.get(Constans.PACKAGE_NAME, Constans.TABLE_INTERCEPTORS))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodHandle.build())
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
