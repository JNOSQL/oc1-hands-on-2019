# Modern Data Streaming and Processing with Apache Kafka and NoSQL - BYOL [HOL4394]

Modern microservice-based architecture needs to exchange data in a fast and reliable way. The use of distributed streaming platforms such as Apache Kafka makes the job easier, but that's not enough. Applications also use NoSQL databases to store information that comes from one or more sources. So it makes perfect sense to use Kafka and NoSQL to create a distributed streaming and processing pipeline between applications. This hands-on lab explores such architecture, deploying applications with Cloud technologies such as Redis NoSQL Database and sending events to a second application with Kafka and Jakarta EE technologies such as JNoSQL to connect to the database and receive Kafka messages as CDI events.


## Slides

TBD

## Requirements

Please have your laptop ready with these tools, so your experience is smooth while working the lab!

* [Git](https://git-scm.com/book/en/v1/Getting-Started-Installing-Git)
* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3 or higher Configured](https://maven.apache.org/download.cgi)
* [Docker 17.12.1-ce or higher](https://docs.docker.com/install/#next-release)
* [Docker Compose 1.21.2 or higher](https://docs.docker.com/v17.09/compose/install/)
* Any IDE that supports maven and Java 8
  * [Eclipse](https://www.eclipse.org/downloads/)
  * [NetBeans](https://netbeans.org/)
  * [IntelliJ](https://www.jetbrains.com/idea/download/)


## Hands on Lab

This workshop is divided in four steps.

1. [Connecting to Kafka](kafka/README.md): This step connects your Java code to a Kafka instance, using Kafka's native library

2. [Connecting to NoSQL](nosql/README.md): This step connects your Java code to a NoSQL database instance, using EE4J's JNoSQL project

3. [Streaming Kafka Events as CDI](events/README.md): This step updates your Java code and now events are received as CDI events

4. [Running in the Cloud](cloud/README.md): This last step brings all the previous together to create a data processing pipeline

### Use case

For this lab, we'll use a standard Industrial IoT use case for process temperature control - we will receive temperature readings from multiple devices and need to check for alarming conditions. We also want to have a dashboard with the current temperature for each device.

The Temperature Readings have the following fields: 
* Device ID (String)
* Timestamp (long)
* Temperature (in Celsius - double)

A Device has the follwing fields:
* Device ID (String)
* Last Temperature Reading (Temperature Reading)
* Device Name (String)

The alarms we want to generate are:
* No messages for 5 minutes (or the last message was sent more than 5 minutes ago)
* Temperature is more than X Celsius degrees

The dashboard will be served using a REST API so that multiple clients can consume the data.

So, the demo environment has:
* 2 device simulators that will send Kafka messages with Temperature Readings;
* A server to receive this readings and check for alarming conditions and store data for the dashboard
* The same server to provide dashboard data over REST

We'll provide the device simulators and a dashboard client, and your job is to develop the server that receives the readings first as Kafka events, later as CDI events, storing them in a Key-Value (Redis) database so that when a dashboard client asks for data, it'll be returned by your service.

The four steps in the lab will enable you to develop such server, and we hope that this can serve as starting point for many other different use cases. There are different ways to do this processing (for example, using Kafka Streams) that can be explored later!

### Twitter

* [otaviojava](https://twitter.com/otaviojava)
* [leomrlima](https://twitter.com/leomrlima)
* [gamussa](https://twitter.com/gamussa)
