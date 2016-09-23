package com.kata.vending_machine.tests;

import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;

public class ConsoleOutputTestListener extends RunListener {
	@Override
	public void testFinished(Description description) throws Exception {

		System.out.printf("Test finished: %s\n", description);
	}
}
