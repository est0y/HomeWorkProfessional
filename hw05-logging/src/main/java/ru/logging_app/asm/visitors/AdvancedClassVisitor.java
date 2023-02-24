package ru.logging_app.asm.visitors;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import ru.logging_app.asm.VisitMethodParams;

public class AdvancedClassVisitor extends ClassVisitor {

    public AdvancedClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);

    }
    @Override
    public MethodVisitor visitMethod(int access,String name,String desc,String signature,String[] exceptions){
        return visitMethod(new VisitMethodParams(access,name,desc,signature,exceptions));
    }
    public MethodVisitor visitMethod(VisitMethodParams vmp){
      return cv.visitMethod(vmp.access(),vmp.name(),vmp.desc(),vmp.signature(),vmp.exceptions());
    }
}
