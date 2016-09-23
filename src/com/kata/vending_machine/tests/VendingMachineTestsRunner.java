package com.kata.vending_machine.tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class VendingMachineTestsRunner {

	public static void main(String[] args) {
		JUnitCore core = new JUnitCore();
		core.addListener(new ConsoleOutputTestListener());
		System.out.println("Begining all tests...");
		Result results = core.run(VendingMachineBasicTests.class, VendingMachineProcessTests.class);
		System.out.printf("%d tests run in %s ms\n", results.getRunCount(), results.getRunTime());
		System.out.printf("Failed: %d\n", results.getFailureCount());

		for (Failure failure : results.getFailures()) {
			System.out.printf("Fail: %s\n", failure.toString());
		}
		System.out.printf("Test success: %s\n", results.wasSuccessful());
	}

}
