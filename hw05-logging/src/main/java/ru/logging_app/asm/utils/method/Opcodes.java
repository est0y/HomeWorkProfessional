package ru.logging_app.asm.utils.method;

import org.objectweb.asm.Type;

public class Opcodes {
    public static int getReturnOpcode(Type returnType) {
        switch (returnType.getSort()) {
            case Type.BOOLEAN:
            case Type.CHAR:
            case Type.BYTE:
            case Type.SHORT:
            case Type.INT:
                return org.objectweb.asm.Opcodes.IRETURN;
            case Type.FLOAT:
                return org.objectweb.asm.Opcodes.FRETURN;
            case Type.LONG:
                return org.objectweb.asm.Opcodes.LRETURN;
            case Type.DOUBLE:
                return org.objectweb.asm.Opcodes.DRETURN;
            case Type.ARRAY:
            case Type.OBJECT:
                return org.objectweb.asm.Opcodes.ARETURN;
            case Type.VOID:
                return org.objectweb.asm.Opcodes.RETURN;
            default:
                throw new AssertionError("Unknown type sort: " + returnType.getClassName());
        }
    }
}
