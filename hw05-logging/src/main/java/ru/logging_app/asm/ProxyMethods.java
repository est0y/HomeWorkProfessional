package ru.logging_app.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import ru.logging_app.asm.method_changers.MethodChanger;
import ru.logging_app.asm.visitors.AdvancedClassVisitor;

import java.util.Collection;

import static ru.logging_app.asm.utils.method.Opcodes.getReturnOpcode;
import static ru.logging_app.asm.utils.method.Params.methodParamsLoop;

public class ProxyMethods {
    private final Collection<VisitMethodParams> collection;
    private final String proxyName;
    private final AdvancedClassVisitor cv;
    private final String className;

    public ProxyMethods(Collection<VisitMethodParams> collection, String proxyName, AdvancedClassVisitor cv, String className) {
        this.collection = collection;
        this.proxyName = proxyName;
        this.cv = cv;
        this.className = className;
    }

    public void makeProxies(MethodChanger methodChanger) {
        collection.forEach(visitMethodParams -> {
            MethodVisitor mv = cv.visitMethod(visitMethodParams);
            methodChanger.change(mv, visitMethodParams);
            if (!visitMethodParams.isStatic()) {
                mv.visitVarInsn(Opcodes.ALOAD, 0);
            }
            methodParamsLoop(visitMethodParams, mv, (paramType, leftParams) -> {
            });
            int returnOpcode = visitMethodParams.isStatic() ? Opcodes.INVOKESTATIC : Opcodes.INVOKEVIRTUAL;
            mv.visitMethodInsn(returnOpcode, className, visitMethodParams.name() + proxyName, visitMethodParams.desc(), false);
            mv.visitInsn(getReturnOpcode(visitMethodParams.getReturnType()));
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        });
    }

}
