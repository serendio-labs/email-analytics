## **Overview**
The Email-Analytics is a framework for analyzing large email datasets. The project is incubated at [Serendio](www.serendio.com). The objective is to process large email datasets and generate useful information out of it. The framework has capability of performing Sentiment Analysis and Topic Extraction on any email dataset with a help of [DisKoveror, The Text Analysis framework](https://github.com/serendio-labs/diskoveror-ta). The Email-Analytics can be run through Command Line Interface.

The possible use case for Email Analytics

    1.  Analyze the email activities of the employees
    2.  Fraud Detection
    3.  Data-mining for Business Analytics

License: GPL 3.0
<!--
## **Key Functionalities**

> 1. **Ingest mailbox**
> 2. **Make Neo4j Graph Database**
> 3. **Auto Indexing**
> 4. **Topic modeling and Sentiment Extraction**
> 5. **Pre-defined Query Support**
-->

## **System Architecture**

![System Architecture](SystemArchi.jpg)
#####**1. Input Email Dataset:**
The Email Analytics system currently supports three major Email Formats. They are EML, MBOX and PST. The current email data ingestion is done by providing absolute file/folder path of the email dataset. 
#####**2. Email Ingestion & Processing Engine:**
This module reads raw email document and extract details like CC,BCC,SUBJECT,CONTENTs. It also interact with Diskoveror-TA to get Sentiment Result and Topic Modeling out of email document. It finally pushes the data to Neo4j Database.
#####**3. Email Analytics Engine:**
This part of Email Analytcs system takes queries from user, interact with Neo4j and present the query results.
#####**4. Embedded Neo4j:**
The Email Analytics system stores data into Neo4j Graph Database. The Neo4j can be used either in Embedded mode or with REST API. The current architecture uses Embedded Neo4j. In Embedded mode, we dont need to setup or start neo4j but Neo4j Native Java Library does that job. The database is persisted on local filesytem in specified folder. 
#####**5. DiKoveror-TA Server:**
The Email Analytics system uses DisKoveror-TA for processing Sentiment and Topic Modeling out of email document. The DisKoveror-TA is developed at Serendio and available open source. 
#####**6. Command Line:**
The Email Analytics system takes queries and return results via command line.

## **Getting Started**
#### **Step1: Software Requirements**
#####**Install following softwares:**

    JDK (Version 8)
    Python (version 2.7.X)
    Pip (version 7.1.X)
    Maven
    
<br/>
**For Ubuntu/Debian Users:**

Install **python-2.7**:

    https://www.python.org/downloads/    

Install **JDK**, **Pip** and **Maven** from apt package manager:

    sudo apt-get install openjdk-8-jdk
    sudo apt-get install python-pip
    sudo apt-get install maven

    
#### **Step2: Workspace to Download**
Get the Email-Analytics project from serendio-labs project repository.

  > $ git clone https://github.com/serendio-labs/email-analytics.git

Download Neo4j-Server Community version and extract it.
  > [Neo4j-Server](http://neo4j.com/download/)

#### **Step3: Package Source Code**
Email Analytics is maven based project. You need to compile and package java class files using maven.<br/> 

>    $ cd /path/to/email-analytics/script

>    $ sh GenerateJar.sh

If the packaging is done successful than output jar file should be created in target folder.

#### **Step4: Starting Thrift servers for Sentiment and Topics in DisKoveror-ta**

The requirements.txt file specifies the software packages along with their versions to be installed. Execute the
below command to install all python related dependencies for the Sentiment and Topics.

>     /diskoveror-ta/src/main/python$ sudo pip install -r requirements.txt

Start the thrift servers for Topics and Sentiments 

>     /diskoveror-ta/src/main/python$ python server.py

#### **Step5: Copy the jar file into Email Analytics libs folder**

Add the following jars to your build path

1. Add all jars present in the **_neo4j/lib_** folder of [neo4j](http://neo4j.com/download/) database package to **_email-analytics/libs/toRun/external_**

#### **Step6: Run the sample Application**
The application runner is bundled in email-analytics jar. Use the following script to run email-analytics jar. 

>    $ cd /path/to/email-analytics/script

>    $ sh RunExample.sh

#### **Step7: Add all jars from Step3 & Step5 to your build path and use them in your java program**
You can use email-analytics jar in your application.
The sample code is provided below.
        
        /*Email Ingestion*/
        AppConfigurations conf = new AppConfigurations();
		conf.setNeo4jDbPath("/home/nishant/Desktop/test.db");
		conf.setInputDatasetPath("/home/nishant/Desktop/input");
		conf.setDatasetType(AppConfigurations.EmailDatasetType.EML);
		DatasetIngestionRunner runner = new DatasetIngestionRunner();
		runner.run(conf);

        /*Email Analytics*/
		Neo4jTraversalQuery a = new Neo4jTraversalQuery();
		System.out.println(a.TotalEmailProcessed());
		
## **Using Sample Data**

Sample email dataset files in **_"sample-data"_** folder have been provided for your convenience and can be used to test the project:
>     .pst
>     .mbox
>     .eml
