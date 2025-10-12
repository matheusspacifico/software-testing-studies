package com.exercises.mylinkedlist;

import org.junit.jupiter.api.*;

@Tag("UnitTest")
public class MyLinkedListRemoveTest {

    private MyLinkedList<String> sut;

    @BeforeEach
    void setup() {
        sut = new MyLinkedList<>();
    }

    @Nested
    @Tag("MyLinkedListRemoveTodosNos")
    class CriterioTodosNos {

        @Test
        void ShouldRemoveNonNullElement() {
            sut.add("A");
            sut.add("B");

            Assertions.assertTrue(sut.remove("B"));
            Assertions.assertEquals(1, sut.size());
        }

        @Test
        void shouldRemoveNullElement() {
            sut.add("A");
            sut.add(null);

            Assertions.assertTrue(sut.remove(null));
            Assertions.assertEquals(1, sut.size());
        }

        @Test
        void shouldNotRemoveWhenElementDoesntExist() {
            sut.add("A");
            sut.add("B");

            Assertions.assertFalse(sut.remove("non-existent-element"));
            Assertions.assertEquals(2, sut.size());
        }
    }

    @Nested
    @Tag("MyLinkedListRemoveTodasArestas")
    class CriterioTodasArestas {

        @Test
        void ShouldRemoveNonNullElement() {
            sut.add("A");
            sut.add("B");

            Assertions.assertTrue(sut.remove("B"));
            Assertions.assertEquals(1, sut.size());
        }

        @Test
        void shouldRemoveNullElement() {
            sut.add("A");
            sut.add(null);

            Assertions.assertTrue(sut.remove(null));
            Assertions.assertEquals(1, sut.size());
        }

        @Test
        void shouldNotRemoveWhenElementDoesntExist() {
            sut.add("A");
            sut.add("B");

            Assertions.assertFalse(sut.remove("non-existent-element"));
            Assertions.assertEquals(2, sut.size());
        }

        @Test
        void shouldReturnFalseWhenRemovingNullFromListWithoutNulls() {
            sut.add("A");
            sut.add("B");

            boolean result = sut.remove(null);

            Assertions.assertFalse(result);
            Assertions.assertEquals(2, sut.size());
        }
    }
}
