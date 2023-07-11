# AmazonReload

## Introduction
The Amazon Reload AutoTester is a Java application designed to automate the process of reloading Amazon Gift Card Balance. 
The main purpose of the program is to help test the Amazon Gift Card reloading functionality by making purchases as many times as required.

## Prerequisites
1. Java Development Kit (JDK) 8 or later
2. Maven 4.0 or later
3. Git
4. Intellij Idea
5. Chrome WebDriver compatible with the installed Chrome version
6. Amazon account with saved payment methods

## Setup
1. Clone the repository
2. In the configuration file change parameters, as you need
```
email=email@gmail.com - It's the Amazon login email
cardNumber=6666 - Last 4 digits of the card, which you want to use as a payment variant
purchaseAmount=0.50 - The amount of money you want to transfer on  the gift card
purchaseQuantity=2 - how many purchases you want to make 
```
4. Run the test

## Contributing
If you wish to contribute to this project, please fork the repository, make your changes, and submit a pull request.

## Disclaimer
This project is designed for testing purposes only. 
Unauthorized or abusive use of this tool may violate Amazon's Terms of Service and can result in account suspension or legal action. 
Always ensure that you have the necessary permissions before using this tool.
