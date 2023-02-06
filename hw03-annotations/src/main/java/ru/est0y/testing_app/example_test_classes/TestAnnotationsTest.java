package ru.est0y.testing_app.example_test_classes;

import ru.est0y.testing_app.annotations.Before;
import ru.est0y.testing_app.annotations.Test;

public class TestAnnotationsTest {

    @Test
    void test(){
    }
    @Test
    void testFailed(){
        throw new RuntimeException();
    }
    @Test
    void test3(){
    }

}
