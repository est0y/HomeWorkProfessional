
package ru.logging_app.asm;

import ru.logging_app.Log;


public class MyClass {
    @Log
    public  void test(){
        System.out.println("real test method");
    }
    @Log
    public void test2(int i){

    }
    @Log
    public void floatMethod(float f){
        System.out.println("real float method");
    }
    @Log
    public void objectMethod(Object o){
        System.out.println("real object method");
    }
    @Log
    public String stringInt(String str,int i){
        privateMethod();
        return str;
    }
    @Log
    public String stringInt(int i){
        return String.valueOf(i);
    }
    @Log
    private void privateMethod(){

    }
    @Log
    private static void privateStaticMethod(){}
    @Log
    public static String staticMethod(String str) throws Exception{
        privateStaticMethod();
        return str;
    }
    @Log
    public MyClass myClassMethod(){
        return this;
    }
    @Log
    public double doubleMethod(double d){
        return d;
    }

    //@Log
    public void booleanMethod(boolean o){
        System.out.println("boolean method");
    }

}
