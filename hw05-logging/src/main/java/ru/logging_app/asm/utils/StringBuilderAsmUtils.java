package ru.logging_app.asm.utils;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class StringBuilderAsmUtils {
    private static final String classPath="java/lang/StringBuilder";
    private StringBuilderAsmUtils(){}
    public static void newStringBuilder(MethodVisitor mv){
        mv.visitTypeInsn(Opcodes.NEW, classPath);
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, classPath, "<init>", "()V", false);
    }
    public static void append(MethodVisitor mv, Type parameterType) {
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(" + parameterType.getDescriptor() + ")L" + classPath + ";", false);
    }

    public static void append(MethodVisitor mv, String value) {
        mv.visitLdcInsn(value);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, classPath, "append",
                "(Ljava/lang/String;)L" + classPath + ";", false);
    }
}
