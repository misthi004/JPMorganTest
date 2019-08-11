package com.interview.jpmorgan.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class SaleLog {

	private static HashMap<String, Product> lineItems = new HashMap();

	private double totalSalesValue=0.0;

	private static ArrayList normalReports = new ArrayList();

	private ArrayList adjustmentReports= new ArrayList();

	// Constructor
	public SaleLog() {

	}

	public Product getProduct(String type) {
		return lineItems.getOrDefault(type, new Product(type));
	}

	public void updateProduct(Product product) {
		lineItems.put(product.getProductType(), product);
	}

	public ArrayList getNormalReports() {
		return normalReports;
	}

	public void setNormalReports(String normalReport) {
		this.normalReports.add(normalReport);
	}

	public ArrayList getAdjustmentReports() {
		return adjustmentReports;
	}

	public void setAdjustmentReports(String adjustmentReport) {
		this.adjustmentReports.add(adjustmentReport);
	}

	public double getTotalSalesValue() {
		return totalSalesValue;
	}

	public void appendTotalSalesValue(double productTotalPrice) {
		totalSalesValue += productTotalPrice;
	}

	public void setTotalSalesValue(double productTotalPrice) {
		totalSalesValue = productTotalPrice;
	}

	/*
	 * Report outputs sales information to system console on every 10th report
	 * iteration using modulo. Displays in a table formatted structure and stops
	 * execution of the application after 50th message iteration.
	 */
	public void report() {

		// Report after 10th iteration and not at the beginning.
		if ((normalReports.size() % 10) == 0 && normalReports.size() != 0) {
			setTotalSalesValue(0.0);
			// System.out.println(normalReports);
			System.out.println("10 sales appended to log");
			System.out.println("*************** Log Report *****************");
			System.out.println("|SalesMessageProcessor.Product           |Quantity   |Value      |");
			lineItems.forEach((k, v) -> formatReports(k, v));
			System.out.println("-------------------------------------------");
			System.out.println(String.format("|%-30s|%-11.2f|", "Total Sales", getTotalSalesValue()));
			System.out.println("-------------------------------------------");
			System.out.println("End\n\n");
			try {
				// Add 2 second pause
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Report and stop execution after 50th message
		if ((normalReports.size() % 50) == 0 && normalReports.size() != 0) {
			System.out.println(
					"Application reached 50 messages and cannot process further. The following are the adjustment records made;\n");

			// Display all the adjustment reports so far recorded.
			getAdjustmentReports().forEach(System.out::println);
			System.exit(1);
		}
	}

	// Format the report with right padding structure. populates product details on
	// each line.
	public void formatReports(String type, Product product) {
		String lineItem = String.format("|%-18s|%-11d|%-11.2f|", product.getProductType(), product.getTotalQuantity(),
				product.getTotalPrice());
		appendTotalSalesValue(product.getTotalPrice());
		System.out.println(lineItem);
	}

}
