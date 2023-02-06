package ru.est0y.testing_app.example_test_classes;

import ru.est0y.testing_app.annotations.After;
import ru.est0y.testing_app.annotations.Before;
import ru.est0y.testing_app.annotations.Test;

public class BeforeTest {
    @Before
    void failedBeforeMethod(){
        throw new RuntimeException();
    }
    @Before
    void before2(){
    }
    @Test
    void test(){
    }
}
