# Modern Data Streaming and Processing with Apache Kafka and NoSQL - BYOL [HOL4394]

Modern microservice-based architecture needs to exchange data in a fast and reliable way. The use of distributed streaming platforms such as Apache Kafka makes the job easier, but that's not enough. Applications also use NoSQL databases to store information that comes from one or more sources. So it makes perfect sense to use Kafka and NoSQL to create a distributed streaming and processing pipeline between applications. This hands-on lab explores such architecture, deploying applications with Cloud technologies such as Redis NoSQL Database and sending events to a second application with Kafka and Jakarta EE technologies such as JNoSQL to connect to the database and receive Kafka messages as CDI events.

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

1. [Connecting to NoSQL](nosql/README.md): This step connects your Java code to a NoSQL database instance, using EE4J's JNoSQL project

2. [Streaming Kafka Events as CDI](events/README.md): This step updates your Java code and now events are received as CDI events

3. [Running in the Cloud](cloud/README.md): This last step brings all the previous together to create a data processing pipeline

### Use case

To this demo, we'll manage users. The user will have the following information:


* Nickname
* Name
* Age
* Settings

The information will be there for 5 minutes then it will expire.

### Twitter

* [otaviojava](https://twitter.com/otaviojava)
* [leomrlima](https://twitter.com/leomrlima)
* [gamussa](https://twitter.com/gamussa)
