package ru.logging_app.asm.method_changers;

import org.objectweb.asm.MethodVisitor;
import ru.logging_app.asm.VisitMethodParams;

public interface MethodChanger {
    void change(MethodVisitor mv, VisitMethodParams vmp);
}
