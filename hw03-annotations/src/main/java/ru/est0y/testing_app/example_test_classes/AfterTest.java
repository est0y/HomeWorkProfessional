package ru.est0y.testing_app.example_test_classes;

import ru.est0y.testing_app.annotations.After;
import ru.est0y.testing_app.annotations.Before;
import ru.est0y.testing_app.annotations.Test;

public class AfterTest {
    @Before
    void before() {

    }

    @Test
    void test() {
    }

    @After
    void afterFailed() {
        throw new RuntimeException();
    }
    @After
    void after() {
    }
}
