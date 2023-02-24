package ru.logging_app;


import org.objectweb.asm.*;
import ru.logging_app.asm.*;
import ru.logging_app.asm.method_changers.LogParams;
import ru.logging_app.asm.visitors.AdvancedClassVisitor;
import ru.logging_app.asm.visitors.AnnotationScanner;
import ru.logging_app.asm.visitors.RenameMethods;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.List;


public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) {
                return handle(classfileBuffer, className);
            }
        });

    }

    private static byte[] handle(byte[] originalClass, String className) {
        String proxyNameEnding="Proxied";
        var cr = new ClassReader(originalClass);
        var cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        var annotationScanner = new AnnotationScanner(List.of(Log.class));
        cr.accept(annotationScanner, Opcodes.ASM5);
        HashSet<VisitMethodParams> withLogAnnotation = annotationScanner.getAnnotatedMethods(Log.class);
        if (withLogAnnotation.size() == 0) return originalClass;
        cr.accept(new RenameMethods(Opcodes.ASM5, cw, withLogAnnotation, proxyNameEnding), Opcodes.ASM5);

        new ProxyMethods(
                withLogAnnotation,
                proxyNameEnding,
                new AdvancedClassVisitor(Opcodes.ASM5, cw),
                className
        ).makeProxies(new LogParams());

        return cw.toByteArray();
    }

}
