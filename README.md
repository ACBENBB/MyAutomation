# MyAutomation Project

Welcome to the MyAutomation project. This document provides an overview of the key components and directories in the project.

## **Project Structure:**

### **Configuration**
- **File:** `./config.txt`.
- **Description:** Contains various configuration settings and variables for the automation tests.
- **Usage:** Edit this file to set the appropriate driver paths, URLs, and other necessary configurations.

### **Test Videos**
- **Directory:** `./videos`.
- **Description:** This directory stores videos of the executed tests.
- **Usage:** Review these videos to analyze test execution and debug any issues.

### **Screenshots**
- **Directory:** `./screenshots`.
- **Description:** This directory contains screenshots of failed tests.
- **Usage:** Use these screenshots to quickly identify and understand the points of failure in your tests.

## **How to Use:**

### **Setting Up Configuration:**
- Open `./config.txt`.
- Edit the variables as needed. For example:
  ```properties
  driverName = chrome driver
  driverPath = ./chromedriver.exe
  websiteName-url = https://www.websiteName.co.il/

### **Run test suit:**
- Open terminal and navigate to the project's root directory `\MyAutomation`.
- Type `mvn test`.

### **Show test report:**
- After the tests have completed, generate and serve the Allure report using `allure serve`.
