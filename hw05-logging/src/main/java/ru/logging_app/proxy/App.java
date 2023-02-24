package ru.logging_app.proxy;

public class App {
    public static void main(String[] args) {
        AnyInterface any = new Ioc<AnyInterface>(new MyClass()).proxy();
        any.test(2);
        SecondIntr second = (SecondIntr) new Ioc(new MyClass()).proxy();
        second.second();
        new MyClass().test2("TEST");
/*        System.out.println(Arrays.toString(MyClass.class.getDeclaredMethods()));
        Arrays.stream(MyClass.class.getDeclaredMethods()).forEach(m-> System.out.println(m.getName()));*/
     /*   System.out.println(a.getClass());
        a.test();
        a.test(1);*/
    }
}
