package com.interview.jpmorgan.processor;

public class MessageParser {

	private String productType;

	private double productPrice;

	private int productQuantity;

	private String operatorType;

	public MessageParser(String message) {
		this.productType = "";
		this.productPrice = 0.0;
		this.productQuantity = 0;
		this.operatorType = "";
		parseMessage(message);
	}

	private boolean parseMessage(String message) {
		if (message == null || message.isEmpty()) {
			return false;
		}
		String[] messageArray = message.trim().split("\\s+");
		String firstWord = messageArray[0];
		if (firstWord.matches("Add|Subtract|Multiply")) {
			return parseMessageType3(messageArray);
		} else if (firstWord.matches("^\\d+")) {
			return parseMessageType2(messageArray);
		} else if (messageArray.length == 3 && messageArray[1].contains("at")) {
			return parseMessageType1(messageArray);
		} else {
			System.out.println("Wrong sales notice");
		}
		return true;
	}

	private boolean parseMessageType1(String[] messageArray) {
		if (messageArray.length > 3 || messageArray.length < 3)
			return false;
		productType = parseType(messageArray[0]);
		productPrice = parsePrice(messageArray[2]);
		productQuantity = 1; // Will always be 1
		return true;
	}

	private boolean parseMessageType2(String[] messageArray) {
		if (messageArray.length > 7 || messageArray.length < 7)
			return false;
		productType = parseType(messageArray[3]);
		productPrice = parsePrice(messageArray[5]);
		productQuantity = Integer.parseInt(messageArray[0]);
		return true;
	}

	private boolean parseMessageType3(String[] messageArray) {
		if (messageArray.length > 3 || messageArray.length < 3)
			return false;
		operatorType = messageArray[0];
		productType = parseType(messageArray[2]);
		productQuantity = 0;
		productPrice = parsePrice(messageArray[1]);
		return true;
	}

	public String parseType(String rawType) {
		String parsedType = "";
		String typeWithoutLastChar = rawType.substring(0, rawType.length() - 1);
		// enum DEPREC
		if (rawType.endsWith("o")) {
			parsedType = String.format("%soes", typeWithoutLastChar);
		} else if (rawType.endsWith("y")) {
			parsedType = String.format("%sies", typeWithoutLastChar);
		} else if (rawType.endsWith("h")) {
			parsedType = String.format("%shes", typeWithoutLastChar);
		} else if (!rawType.endsWith("s")) {
			parsedType = String.format("%ss", rawType);
		} else {
			parsedType = String.format("%s", rawType);
		}
		return parsedType.toLowerCase();
	}

	public double parsePrice(String rawPrice) {
		double price = Double.parseDouble(rawPrice.replaceAll("£|p", ""));
		if (!rawPrice.contains(".")) {
			price = Double.valueOf(Double.valueOf(price) / Double.valueOf("100"));
		}
		return price;
	}

	public String getProductType() {
		return productType;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

}
