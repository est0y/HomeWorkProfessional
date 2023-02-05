package ru.est0y.testing_app;

import ru.est0y.testing_app.example_test_classes.*;

public class App {
    public static void main(String[] args) throws ClassNotFoundException {
        TestsRunner testsRunner=new TestsRunner();
        /* testsRunner.run(BeforeTest.class.getName());
        testsRunner.run(TestAnnotationsTest.class.getName());
        testsRunner.run(AfterTest.class.getName());*/
        testsRunner.run(AllAnnotations.class.getName());
    }

}
