package ru.logging_app.asm.method_changers;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import ru.logging_app.asm.VisitMethodParams;

import static ru.logging_app.asm.utils.method.Console.io.Output.*;
import static ru.logging_app.asm.utils.StringBuilderAsmUtils.*;
import static ru.logging_app.asm.utils.method.Params.methodParamsLoop;

public class LogParams implements MethodChanger {
    @Override
    public void change(MethodVisitor mv, VisitMethodParams vmp) {
        newStringBuilder(mv);
        append(mv, "executed method: " + vmp.name() + ",Params: ");
        methodParamsLoop(vmp, mv, (paramType, leftParams) -> {
            append(mv, paramType);
            if (leftParams != 0) {
                append(mv, ",");
            }
        });
        println(mv,Type.getType(Object.class));
    }

}
