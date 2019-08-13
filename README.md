# Modern Data Streaming and Processing with Apache Kafka and NoSQL - BYOL [HOL4394]

Modern microservice-based architecture needs to exchange data in a fast and reliable way. The use of distributed streaming platforms such as Apache Kafka makes the job easier, but that's not enough. Applications also use NoSQL databases to store information that comes from one or more sources. So it makes perfect sense to use Kafka and NoSQL to create a distributed streaming and processing pipeline between applications. This hands-on lab explores such architecture, deploying applications with Oracle Cloud technologies such as Oracle NoSQL Database and sending events to a second application with Kafka and Jakarta EE technologies such as JNoSQL to connect to the database and receive Kafka messages as CDI events.


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


We'll be using Oracle Cloud NoSQL and Oracle Java Cloud Service to host our services, and the steps are the same if you use a different cloud provider.
