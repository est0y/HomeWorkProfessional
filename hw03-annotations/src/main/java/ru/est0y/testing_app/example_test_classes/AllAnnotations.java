package ru.est0y.testing_app.example_test_classes;

import ru.est0y.testing_app.annotations.After;
import ru.est0y.testing_app.annotations.Before;
import ru.est0y.testing_app.annotations.Test;

public class AllAnnotations {
    @Before
    void before() {
    }

    @Test
    void test1() {
    }
    @Test
    void failedTest() {
        throw new RuntimeException();
    }
    @Test
    void test2() {
    }
    @After
    void after() {
    }
}
