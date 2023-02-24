package ru.logging_app.asm.visitors;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import ru.logging_app.asm.VisitMethodParams;
import ru.logging_app.asm.visitors.AdvancedClassVisitor;

import java.util.Collection;

public class RenameMethods extends AdvancedClassVisitor {
    private final Collection<VisitMethodParams> withLogAnnotation;
    private final String nameEnding;

    public RenameMethods(int api, ClassVisitor classVisitor,
                         Collection<VisitMethodParams> withLogAnnotation,
                         String nameEnding) {
        super(api, classVisitor);
        this.withLogAnnotation = withLogAnnotation;
        this.nameEnding = nameEnding;
    }

    @Override
    public MethodVisitor visitMethod(VisitMethodParams vmp) {
        String name = withLogAnnotation.contains(vmp) ? vmp.name() + nameEnding : vmp.name();
        return cv.visitMethod(vmp.access(), name, vmp.desc(), vmp.signature(), vmp.exceptions());
    }

}
