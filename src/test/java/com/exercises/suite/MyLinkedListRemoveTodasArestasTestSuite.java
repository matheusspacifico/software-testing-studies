package com.exercises.suite;

import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.exercises.mylinkedlist")
@IncludeTags("MyLinkedListRemoveTodasArestas")
@DisplayName("All critério de todas arestas tests from remove method of My Linked List")
public class MyLinkedListRemoveTodasArestasTestSuite {
}
