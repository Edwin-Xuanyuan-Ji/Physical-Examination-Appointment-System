# Physical Examination Appointment Management System

**Table of Contents**

-[Background](#Background)

-[Introduction](#Introduction)

-[Program Architecture](#Program-Architecture)

-[Technology Architecture](#Technology-Architecture)

-[Function Architecture](#Function-Architecture)

-[Table Structure](#Table-Structure)

-[Screenshots](#Screenshots)

-[Environment](#Environment)

## Background
This appointment management system is a business system developed for medical institutions. This system realizes the visualization of the work content of medical institutions, the specialization of member management, the digitization of health assessment, the workflow of health intervention, and the integration of knowledge base, so as to improve the work efficiency of medical workers, strengthen the interaction with members, and enhance healthcare workers' understanding of the operation of medical institutions.

## Introduction
This project includes the front stage reservation system and the back stage admin management system. It is implemented based on SSM and deployed by dubbo and zookeeper. The front stage appointment system includes: user login, physical examination appointment, SMS verification, automatic registration, package details, appointment display and other modules. The back stage admin management system includes: user login, check item management, check group management, package management, appointment setting, member  statistics display, package proportion statistics, operation data statistics, operation data statistics export as excel, PDF file and other modules.

## Program Architecture

    health_parent
    ├── health_common -- tools and general codes
    ├── health_backend -- back stage admin management system module
    ├── health_mobile -- front stage appointment system module
    ├── health_interface -- service interface module
    ├── health_service_provider -- service provider module
    └── health_jobs -- system jobs module

## Technology Architecture

Front-end: HTML5, Bootstrap, ElementUI, Vue.js, AJAX

Distributed architecture: Zookeeper, Dubbo

Back-end: Spring, SpringMVC, Mybatis, Spring Security

Version control and report forms: Git, Apache POI, Echarts

Database: MyBatis, Redis

Third-party service: Ali Cloud SMS, Qiniu Cloud Storage, Wechat development platform

## Function Architecture

![image](https://user-images.githubusercontent.com/81521033/180389358-dd291025-eaba-4087-98c2-375419e5b2a4.png)

## Table Structure

![image](https://user-images.githubusercontent.com/81521033/180396091-9b3fcffe-1847-487c-890c-3417c7b9af96.png)

## Environment 
| Tool        | Version     | Link     |
| ----------- | ----------- | --------                                                                 |
| JDK         | 1.8         |https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html|
| MySQL       | 5.7         |https://www.mysql.com                                                     |
| Redis       | 3.2         |https://redis.io/download                                                 |
| Zookeeper   | 3.4.6       |https://zookeeper.apache.org/                                             |





