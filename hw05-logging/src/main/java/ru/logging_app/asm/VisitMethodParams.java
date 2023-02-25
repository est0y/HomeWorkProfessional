package ru.logging_app.asm;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.Arrays;
import java.util.Objects;

public record VisitMethodParams(int access, String name, String desc, String signature, String[] exceptions) {
    public boolean isStatic() {
        return (access & Opcodes.ACC_STATIC) != 0;
    }
    public Type getReturnType(){
        return Type.getType(desc).getReturnType();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitMethodParams that = (VisitMethodParams) o;
        return access == that.access && Objects.equals(name, that.name) && Objects.equals(desc, that.desc) && Objects.equals(signature, that.signature) && Arrays.equals(exceptions, that.exceptions);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(access, name, desc, signature);
        result = 31 * result + Arrays.hashCode(exceptions);
        return result;
    }
}
