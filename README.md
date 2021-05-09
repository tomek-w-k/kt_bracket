# KtBracket

![Custom badge](https://img.shields.io/static/v1.svg?label=Java&message=11&color=cadetblue)
![Custom badge](https://img.shields.io/static/v1.svg?label=Spring+Boot&message=2.4.2&color=apple)
![Custom badge](https://img.shields.io/static/v1.svg?label=JavaFX&message=15.0.1&color=darkorange)

## Overview
KtBracket allows users to bulid brackets for tournaments played in a knockout system. It is intended in particular for martial arts tournaments which are played on a several mats with a group of categories assigned to each one. A category contains a group of competitors to be placed on a bracket. For each category a single bracket needs to be built.
<p align="center">
  <img src="https://user-images.githubusercontent.com/59183133/114760240-652f8880-9d5f-11eb-8e56-7f04128f6d9b.png">
</p>

## Detailed description
Competitors for a particular category shall to be placed in a `.txt` file, each one in a separate line - like on a picture below:
<p align="center">
  <img src="https://user-images.githubusercontent.com/59183133/114762522-24853e80-9d62-11eb-9079-e0ceb1dcb4e6.png">
</p>

Since there are usually more than one category played on a single mat, competitors for the rest of categories shall to be placed in a `.txt` files in the same way. All  created files need to be placed in the same directory as they will be loaded together by the application. 

To load files select **File -> Import categories...** option and choose a directory containing prepared files. It is also possible to load a single file by choosing **File -> Import category...** option or remove by selecting it on the list and choosing **Category -> Remove selected** option. 

When all desired files are loaded, choose **Build -> Build** option to build all brackets. Meanwhile, it's possible to add new files or remove them, but then it's necessary to re-build brackets since fight numbering will be out of date (numbering is performed for the entire set of brackets). 
<p align="center">
  <img src="https://user-images.githubusercontent.com/59183133/114799919-038c1000-9d99-11eb-9814-593874012c21.png">
</p>

Generated brackets can be exported to `.xls` file with **File -> Export to XLS...** option. Each one will be exported to a separate file and placed in a chosen directory. 
<p align="center">
  <img src="https://user-images.githubusercontent.com/59183133/114800883-d9d3e880-9d9a-11eb-8400-b7179c9b37bb.png">
</p>

## Requirements
- JDK 11
- Maven 3.6

## Compilation & Lanuch (Linux - Debian/Ubuntu)
Assuming that JDK and Maven are already installed in your system:
1. Go to the project directory and run `mvn package` to build the project and create a `.jar` package.
2. Run the application: `java -jar target/spring-boot-javafx-test-0.0.1-SNAPSHOT.jar`
