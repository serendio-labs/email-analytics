## **Overview**
The Email-Analytics is a framework for analyzing large email datasets. The project is incubated at [Serendio](www.serendio.com). The objective is to process large email datasets and generate useful information out of it. The framework has capability of performing Sentiment Analysis and Topic Extraction on any email dataset with a help of [DisKoveror, The Text Analysis framework](https://github.com/serendio-labs/diskoveror-ta). The Email-Analytics can be run through Command Line Interface.

The possible use case for Email Analytics

    1.  Analyze the email activities of the employees
    2.  Fraud Detection
    3.  Data-mining for Business Analytics

License: GPL 3.0

## **System Architecture**

#####**1. Input Email Dataset:**
The Email Analytics system currently supports three major Email Formats. They are EML, MBOX and PST. The current email data ingestion is done by providing absolute file/folder path of the email dataset. 
#####**2. Email Ingestion & Processing Engine:**
This module reads raw email document and extract details like CC,BCC,SUBJECT,CONTENTs. It also interact with Diskoveror-TA to get Sentiment Result and Topic Modeling out of email document. It finally pushes the data to Neo4j Database.
#####**3. Neo4j Server:**
The Email Analytics system stores data into Neo4j Graph Database. The Neo4j can be used either in Embedded mode or with REST API. The current architecture uses Embedded Neo4j. In Embedded mode, we don't need to setup or start neo4j but Neo4j Native Java Library does that job. The database is persisted on local file system in specified folder. 
#####**4. DiKoveror-TA Server:**
The Email Analytics system uses DisKoveror-TA for processing Sentiment and Topic Modeling out of email document. The DisKoveror-TA is developed at Serendio and available open source. 
#####**5. Command Line:**
The Email Analytics system perform email ingestion using command line. 


## **Getting Started**

#### **Step1: Software Requirements**
#####**Install following softwares:**

    JDK (Version 8)
    Maven
    
<br/>
**For Ubuntu/Debian Users:**

Install **JDK**, **Pip** and **Maven** from apt package manager:

    sudo apt-get install openjdk-8-jdk
    sudo apt-get install maven

    
#### **Step2: Workspace to Download**
Get the Email-Analytics project from serendio-labs project repository.

  > $ git clone https://github.com/serendio-labs/email-analytics.git

Download Neo4j-Server Community version (3.x) and extract it.
  > [Neo4j-Server](http://neo4j.com/download/)

#### **Step3: Package Source Code**
Email Analytics is maven based project. You need to compile and package java class files using maven.<br/> 

>    $ cd /path/to/email-analytics/script

>    $ sh GenerateJar.sh

If the packaging is done successful than output jar file should be created in target folder.

#### **Step4: Setup Diskoveror-TA server for Sentiment Analysis and Topics Extraction**

This is optional part and you can skip this if you don't want to perform Sentiment Analysis and Topic Extraction from email dataset.

Check out [Diskoveror-TA](https://github.com/serendio-labs/diskoveror-ta) project. Follow the instructions to set up this project.

Email-Analytics interact Diskoveror-TA with REST-API calls. Make sure that Diskoveror-TA setup is serving REST-API calls.

#### **Step5: Run the Sample Application**
The application runner is bundled in email-analytics jar. Use the following script to run email-analytics jar. 

>    $ cd /path/to/email-analytics/script

>    $ sh RunExample.sh

#### **Step6: Use Email-Analytics library in your java program**
You can use email-analytics jar in your application.
The sample code is provided below.
Use Neo4j Server Web Portal or other Graph Visualization tool to explore email data. 
        
       /*Email Ingestion*/
       AppConfigurations conf = new AppConfigurations();
		conf.setInputDatasetPath("/home/nishant/Desktop/input");
		conf.setDatasetType(AppConfigurations.EmailDatasetType.EML);
		DatasetIngestionRunner runner = new DatasetIngestionRunner();
		runner.run(conf);

		
## **Using Sample Data**

Sample email dataset files in **_"sample-data"_** folder have been provided for your convenience and can be used to test the project:
>     .pst
>     .mbox
>     .eml
