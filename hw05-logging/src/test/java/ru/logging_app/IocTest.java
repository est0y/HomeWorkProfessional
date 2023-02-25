package ru.logging_app;

import org.junit.jupiter.api.Test;
import ru.logging_app.proxy.AnyInterface;
import ru.logging_app.proxy.Ioc;

class IocTest {
static  public class ClassWithLog implements AnyInterface {
    @Log
    @Override
    public void test() {

    }

    @Log
    @Override
    public void test(int num) {

    }
}
    @Test
    void proxy() {
       AnyInterface classWithLog=(AnyInterface) new Ioc(new ClassWithLog()).proxy();
       classWithLog.test();
}
}