package com.exercises.suite;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectPackages("com.exercises.mylinkedlist")
@IncludeTags("MyLinkedListRemoveTodosNos")
@SuiteDisplayName("All critério de todos nós tests from remove method of My Linked List")
public class MyLinkedListRemoveTodosNosTestSuite {
}
