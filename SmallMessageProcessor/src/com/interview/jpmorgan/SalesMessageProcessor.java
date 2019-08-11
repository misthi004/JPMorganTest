package com.interview.jpmorgan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.interview.jpmorgan.api.ApiCallException;
import com.interview.jpmorgan.api.ApiResponse;
import com.interview.jpmorgan.processor.AdjustPrice;
import com.interview.jpmorgan.processor.MessageParser;
import com.interview.jpmorgan.processor.Sale;

public class SalesMessageProcessor implements SalesMessageAPICaller {

	@Override
	public ApiResponse processMessage(String workingDirectory) {
		Sale sale = new Sale();
		ApiResponse response = null;

		// Read inputs from test file
		try {
			String line;
			BufferedReader fileReader = new BufferedReader(new FileReader(workingDirectory));
			while ((line = fileReader.readLine()) != null) {
				// process message for each sale notification
				response = processNotification(line);

				// Call the report
				// @note: report only generates after every 10th iteration and stops on 50th
				// iteration and pauses for
				// 2 seconds.
				if (response.equals(ApiResponse.OK)) {
					sale.log.report();
				}
			}
		} catch (ApiCallException exception) {
			exception.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return response;
	}

	public ApiResponse processNotification(String saleNotice) {

		// MessageParser helps to parse the incoming messages and obtain product sale
		// information.
		MessageParser messageParser;

		// Process the given message
		messageParser = new MessageParser(saleNotice);

		// Get the product type e.g 'apple'
		String productType = messageParser.getProductType();

		// Check if product type is empty return false and do nothing.
		if (productType.isEmpty()) {
			return ApiResponse.NOTOK;
		}

		Sale sale = new Sale();

		// Returns an existing product else returns a new Product object
		sale.setProduct(sale.getLog().getProduct(productType));

		// Prepare the product details for adjustment
		sale.setAdjustPrice(new AdjustPrice(sale.getProduct()));

		// Set the product details from the parsed message
		sale.getProduct().setProductQuantity(messageParser.getProductQuantity());
		sale.getProduct().setTotalQuantity(messageParser.getProductQuantity());
		sale.getProduct().setProductPrice(messageParser.getProductPrice());
		sale.getProduct().setAdjustmentOperator(messageParser.getOperatorType());

		// Set the total value of the product.
		setProductTotalPrice(sale);

		// Set the sale log reports
		sale.getLog().setNormalReports(saleNotice);

		// Update the product with the new details
		sale.getLog().updateProduct(sale.getProduct());

		return ApiResponse.OK;
	}

	// Set or Append Total product price based on any adjustment if given.
	// Also appends the log for adjustments made.
	private void setProductTotalPrice(Sale sale) {
		double adjustedPrice;
		double productValue;

		if (!sale.getProduct().getAdjustmentOperator().isEmpty()) {
			adjustedPrice = sale.getAdjustPrice().getAdjustedPrice();
			sale.getLog().setAdjustmentReports(sale.getAdjustPrice().adjustmentReport());
			sale.getProduct().setTotalPrice(adjustedPrice);
		} else {
			productValue = sale.getProduct().calculatePrice(sale.getProduct().getProductQuantity(),
					sale.getProduct().getProductPrice());
			sale.getProduct().appendTotalPrice(productValue);
		}
	}


}
