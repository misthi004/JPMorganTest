package com.interview.jpmorgan;

import com.interview.jpmorgan.api.ApiResponse;

public interface SalesMessageAPICaller{
	
	ApiResponse processMessage(String workingDirectory);

}
