## Java Software Testing Exercises and Studies

This repository serves as a personal collection of exercises and study materials focused on modern software testing practices in Java.

Used technologies:

- JUnit 5: The primary testing framework for writing and running tests.
- Mockito: The mocking framework used to isolate the code under test by creating mock objects and verifying interactions.
- Assertions: A variety of assertion libraries are used to make test validation more readable and expressive. This includes JUnit's built-in assertions and AssertJ for fluent assertions

## Tests Raising

Before almost every exercise, I do some test raising before coding it.

Usually it is a table containing all tests that I thought, like this example:

| ID     | Input                                  | Expected Output                 | Was the product updated? (Repository Called) |
|--------|----------------------------------------|---------------------------------|----------------------------------------------|
| T3     | Product with all non-null valid fields | Empty invalid entries list      | Yes, every field                             |
| T4     | Product with a null name               | Empty invalid entries list      | Yes, except name                             |
| T5     | Produto com nome vazio                 | Invalid entries list with name  | Yes, except name                             |
| T6     | Produto com preço null                 | Empty invalid entries list      | Yes, except price                            |
| T7     | Produto com preço <= 0                 | Invalid entries list with price | Yes, except price                            |
| etc... | etc...                                 | etc...                          | etc...                                       |

It will probably be in portuguese, but you can find every thought test on the same package where the test class is.