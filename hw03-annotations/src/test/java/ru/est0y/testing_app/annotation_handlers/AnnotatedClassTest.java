package ru.est0y.testing_app.annotation_handlers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class AnnotatedClassTest {
    public  class WithAnnotations {
        @ru.est0y.testing_app.annotations.Before
        public void before() {
        }

        @ru.est0y.testing_app.annotations.Test
        public void test() {
            throw new RuntimeException();
        }

        @ru.est0y.testing_app.annotations.After
        public void after() {
        }
    }

    @Test
    void newInstance() {
    }

    @Test
    void annotationMethods() {
        AnnotatedClass annotatedClass = new AnnotatedClass(WithAnnotations.class);
        var map=annotatedClass.annotatedMethods(List.of(
                ru.est0y.testing_app.annotations.Before.class,
                ru.est0y.testing_app.annotations.Test.class,
                ru.est0y.testing_app.annotations.After.class
        ));
        Assertions.assertEquals(1,map.get(ru.est0y.testing_app.annotations.Before.class).size());
        Assertions.assertEquals(1,map.get(ru.est0y.testing_app.annotations.Test.class).size());
        Assertions.assertEquals(1,map.get(ru.est0y.testing_app.annotations.After.class).size());
    }
}