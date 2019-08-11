package com.interview.jpmorgan;

import com.interview.jpmorgan.api.ApiResponse;

public class Main {
	public static void main(String[] args) {
		String workingDirectory = "inputFile/testInput.txt";
		SalesMessageAPICaller caller = new SalesMessageProcessor();
		ApiResponse aiMessageAPICaller = caller.processMessage(workingDirectory);
	}

}
