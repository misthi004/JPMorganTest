package com.interview.jpmorgan.processor;

public class Sale {

	public SaleLog log;

	private AdjustPrice adjustPrice;

	private Product product;

	public SaleLog getLog() {
		return log;
	}

	public void setLog(SaleLog log) {
		this.log = log;
	}

	public AdjustPrice getAdjustPrice() {
		return adjustPrice;
	}

	public void setAdjustPrice(AdjustPrice adjustPrice) {
		this.adjustPrice = adjustPrice;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Sale() {
		log = new SaleLog();
	}
}
