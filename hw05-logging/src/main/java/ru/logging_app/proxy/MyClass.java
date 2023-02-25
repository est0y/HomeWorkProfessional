
package ru.logging_app.proxy;

import ru.logging_app.Log;


public class MyClass implements AnyInterface,SecondIntr  {
    @Log
    public void test(){}
    @Log
    public void test(int num){}
    @Log
    public void test2(String str){
        System.out.println("Invoked test2");
    }
    public void  s(){}
    @Log
    public void second(){}
}
