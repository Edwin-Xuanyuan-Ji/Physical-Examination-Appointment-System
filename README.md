# Physical Examination Appointment Management System

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

## Screenshots
### Front stage appointment system
Login

![image](https://user-images.githubusercontent.com/81521033/180381112-a1a27134-6cc7-43e6-bddd-e80b0368f36c.png)

![image](https://user-images.githubusercontent.com/81521033/180381243-51bf88c9-cbc2-4dc2-a4a1-3bd28ce90a46.png)

Appointment

![image](https://user-images.githubusercontent.com/81521033/180381472-eef31c75-299e-4c6d-bdc0-9d638fca99ae.png)

![image](https://user-images.githubusercontent.com/81521033/180381542-93634956-4e43-4b55-aa0f-5b527f26ee02.png)

![image](https://user-images.githubusercontent.com/81521033/180381644-3359f5eb-3797-4819-8c0b-c8e1067865fc.png)

![image](https://user-images.githubusercontent.com/81521033/180381713-9be51da7-8429-4e1f-8590-38e18586c453.png)

![image](https://user-images.githubusercontent.com/81521033/180382467-0625ad20-0272-41a2-92e9-68a2d024d10a.png)

### Back stage admin management system
Check items management

![image](https://user-images.githubusercontent.com/81521033/180382363-dcc9df8f-c36c-4069-9695-f94747d01d55.png)

Check groups management

![image](https://user-images.githubusercontent.com/81521033/180382842-97742aac-8832-422f-87b1-5e81a77bdf21.png)

Packages Management

![image](https://user-images.githubusercontent.com/81521033/180382975-e5ca301c-dade-4fb3-ac4b-d83ddf8f6d5d.png)

Appointment Settings

![image](https://user-images.githubusercontent.com/81521033/180383327-fe0ae375-22e3-428f-a38c-0aea19c9f19a.png)

Number of members

![image](https://user-images.githubusercontent.com/81521033/180383445-caa95f35-9815-42fa-bda7-250e73057677.png)

Packages statistics

![image](https://user-images.githubusercontent.com/81521033/180383548-e461bf18-3ac2-4803-b3d1-cecbd846a965.png)

Operation data

![image](https://user-images.githubusercontent.com/81521033/180383687-d5e6ee67-5331-4ef7-8b95-d31398ffda18.png)






