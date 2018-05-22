package com.seabig.processor;

import com.google.auto.service.AutoService;
import com.seabig.annotation.anno.BindView;
import com.squareup.javapoet.TypeName;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

// 编译时注解,用于生成java文件
@SupportedAnnotationTypes ("com.seabig.annotation.anno.BindView")
@SupportedSourceVersion (SourceVersion.RELEASE_7)
@AutoService (Processor.class)
public class BindViewProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv)
    {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        parseRoundEnvironment(roundEnv);
        return true;
    }

    private static void printValue(Object obj)
    {
        System.out.println(obj);
    }

    private void parseRoundEnvironment(RoundEnvironment roundEnv)
    {
        Map<TypeElement, ClassCreatorProxy> map = new LinkedHashMap<>();
        // 通过注解获取所有的该注解中的属性
        for (Element element : roundEnv.getElementsAnnotatedWith(BindView.class))
        {
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            //所在类名
            String className = enclosingElement.getSimpleName().toString();
            System.out.println("className =" + className);
            //所在类全名
            String qualifiedName = enclosingElement.getQualifiedName().toString();
            System.out.println("qualifiedName  =" + qualifiedName);
            //注解的值
            int annotationValue = element.getAnnotation(BindView.class).value();
            ClassCreatorProxy bindClass = map.get(enclosingElement);
            if (bindClass == null)
            {
                bindClass = ClassCreatorProxy.createBindClass(enclosingElement);
                map.put(enclosingElement, bindClass);
            }
            String name = element.getSimpleName().toString();
            TypeName type = TypeName.get(element.asType());
            ViewBinding viewBinding = ViewBinding.createViewBind(name, type, annotationValue);
            bindClass.addAnnotationField(viewBinding);
            //printElement(element);
        }

        for (Map.Entry<TypeElement, ClassCreatorProxy> entry : map.entrySet())
        {
            printValue("==========" + entry.getValue().getBindingClassName());
            try
            {
                entry.getValue().preJavaFile().writeTo(filer);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    //注解所在的类                 【element.getEnclosingElement()】
    //注解上的值, 用于findViewById 【element.getAnnotation(BindView.class).value()】
    //注解字段的类型,用于强转       【element.asType()】

    //1,类模板
    // butterKnife是 T extends SimpleActivity 主要是为了兼容继承的个功能。
    /*
    public class MainActivity_Binding {
        public MainActivity_Binding(MainActivity target,View view) {
            target.text = (TextView)view.findViewById(id);
        }
    }
    */
}
