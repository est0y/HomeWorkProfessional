package ru.logging_app.asm.utils.method;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import ru.logging_app.asm.VisitMethodParams;

public class Params {
    static public void methodParamsLoop(VisitMethodParams vmp, MethodVisitor mv, ActionWithMethodParam action) {
        Type methodType = Type.getMethodType(vmp.desc());
        Type[] params = methodType.getArgumentTypes();
        int length = params.length;
        int methodParamIndex = vmp.isStatic() ? 0 : 1;
        int paramsCounter = 1;
        for (Type type : params) {
            int opcode = type.getOpcode(Opcodes.ILOAD);
            mv.visitVarInsn(opcode, methodParamIndex);
            action.execute(type, length - paramsCounter);
            methodParamIndex++;
            paramsCounter++;
        }
    }
}
