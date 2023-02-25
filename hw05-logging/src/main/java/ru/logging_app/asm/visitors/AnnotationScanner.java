package ru.logging_app.asm.visitors;

import org.objectweb.asm.*;
import ru.logging_app.asm.VisitMethodParams;

import java.lang.annotation.Annotation;
import java.util.*;

public class AnnotationScanner extends ClassVisitor {
    private final Map<String, HashSet<VisitMethodParams>> annotatedMethods=new HashMap<>();

    public AnnotationScanner(List<Class<? extends Annotation>> annotations) {
        super(Opcodes.ASM8);
        annotations.forEach(annotation -> annotatedMethods.put(Type.getDescriptor(annotation), new HashSet<>()));
    }
    private void addVisitMethodParams(String desc,VisitMethodParams vmp){
       HashSet<VisitMethodParams> hashSet=annotatedMethods.get(desc);
       if (hashSet!=null){
           hashSet.add(vmp);
       }
    }

    public HashSet<VisitMethodParams> getAnnotatedMethods(Class<? extends Annotation> annotation) {
        return annotatedMethods.get(Type.getDescriptor(annotation));
    }

    class MyMethodVisitor extends MethodVisitor {
        private final VisitMethodParams visitMethodParams;
        MyMethodVisitor(VisitMethodParams visitMethodParams) {
            super(Opcodes.ASM8);
            this.visitMethodParams=visitMethodParams;
        }
        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            addVisitMethodParams(desc,visitMethodParams);
            return null;
        }
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
        return new MyMethodVisitor(new VisitMethodParams(access,name,desc,signature,exceptions));
    }

}