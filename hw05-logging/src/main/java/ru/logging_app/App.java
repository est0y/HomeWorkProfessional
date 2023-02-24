
package ru.logging_app;

import ru.logging_app.asm.MyClass;
/*

java -javaagent:hw05.jar -jar hw05.jar

*/
public class App {
    public static void main(String[] args) throws Exception{
        MyClass myClass=new MyClass();
        myClass.test();
        myClass.test2(1);
        myClass.floatMethod(8.6f);
        myClass.objectMethod(new Object());
        myClass.booleanMethod(true);
        myClass.stringInt("string",1);
        myClass.stringInt(7);
        myClass.myClassMethod();
        myClass.doubleMethod(2.2);
        MyClass.staticMethod("STATIC");
    }
}
