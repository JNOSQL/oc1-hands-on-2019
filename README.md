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

This workshop is divided in three steps.

1. Connecting to Kafka: This step connects your Java code to a Kafka instance, using Kafka's native library

2. Connecting to NoSQL: This step connects your Java code to a NoSQL database instance, using EE4J's JNoSQL project

3. Streaming Kafka Events as CDI: This step updates your Java code and now events are received as CDI events

### Use case

For this lab, we'll use a standard Industrial IoT use case of process temperature control - we will receive temperature readings from multiple devices and need to check for alarming conditions. We also want to have a dashboard with the current temperature for each device. There are multiple ways of doing this - using Kafka Streams, for example - but we'll focus on implenting it a Jakarta EE application.

The Temperature Readings have the following fields: 
* Device ID (String)
* Timestamp (long)
* Temperature (in Celsius - double)

The alarms we want to generate are:
* No messages for 5 minutes (or the last message was sent more than 5 minutes ago)
* Temperature is more than X Celsius degrees

## Step 0: Setup

We'll be assuming a Unix like (Linux, MacOS) environment for the lab. To get started, open a terminal window and checkout this repository to your local environment:

```
git clone https://github.com/JNOSQL/oc1-hands-on-2019
```

At the repository root, there's a docker-compose.yml file that starts up the services you'll need for this service: Kafka and Redis, a Key-value database. Change to the cloned directory and start your docker-compose.]

```
cd oc1-hands-on-2019
docker-compose up
```

We will be using a local Kafka instance, that docker names as *kafka*. For code outside Docker, we need to make this name resolvable too. For that, edit your */etc/hosts* file and include "kafka" in the "localhost" line.

## Step 1: Connecting to Kakfa

The source code inside the *kafka* directory contains Java code that connects to Kafka using the Kafka Clients libraries (*package org.jnosql.artemis.demo.kafka*), the Temperature Reading domain object and a Device Simulator (*package org.jnosql.artemis.demo.kafka.device*) that generates Temperature Readings and sends them to Kafka, and a Device Event Receiver that connects to Kafka and prints the information on the screen (*package org.jnosql.artemis.demo.kafka.client*).

The Device Simulator is a Producer of Temperature Readings, and the Device Event Receiver is a Consumer. These are core concepts when using Kafka and you'll find that reflected in the code. Take some time to explore the code and then run the Producer and Consumer. 

```
cd kafka
mvn clean package
```

The "kafka-0.0.1.jar" package will contain everything you need to run the producer or the client.

### Running the producer

For the producer, you'll run:
```
java -cp target/kafka-0.0.1.jar org.jnosql.artemis.demo.kafka.device.DeviceSimulator YOUR_NAME YOUR_DEVICE_ID config.oc1
```

You'll get the following lines as log:
```
java -cp target/kafka-0.0.1.jar org.jnosql.artemis.demo.kafka.device.DeviceSimulator leomrlima device1 config.oc1
...
10:32:40.495 [pool-1-thread-1] DEBUG o.j.a.d.kafka.device.DeviceSimulator - Sending TemperatureReading [deviceId=device1, timestamp=1568914360495, temperature=52.44989] as reading
10:32:40.495 [pool-1-thread-1] INFO  o.j.a.demo.kafka.KafkaConnection - Fire TemperatureReading: TemperatureReading [deviceId=device1, timestamp=1568914360495, temperature=52.44989]
10:32:40.832 [kafka-producer-network-thread | leomrlima-client] DEBUG o.j.a.demo.kafka.KafkaConnection - Event sent with metadata temperatureReadings-0@0
```

This means that you're connected and sending messages!

### Running the consumer
For the producer, you'll run:
```
java -cp target/kafka-0.0.1.jar org.jnosql.artemis.demo.kafka.client.DeviceEventReceiver YOUR_NAME_1 config.oc1
```

Note that the name of Producer and Receiver *can not be the same* - it'll cause Kafka to think that the same client is connect twice and that's not good!

You'll get the following lines as log:
```
java -cp target/kafka-0.0.1.jar org.jnosql.artemis.demo.kafka.client.DeviceEventReceiver leomrlima1 config.oc1
...
10:44:21.307 [main] INFO  o.j.a.demo.kafka.KafkaConnection - Received: org.apache.kafka.clients.consumer.ConsumerRecords@37654521
10:44:21.307 [main] INFO  o.j.a.d.k.client.DeviceEventReceiver - New record: TemperatureReading [deviceId=device1, timestamp=1568915060894, temperature=142.64621]
```

## Step 2: Connecting to Redis using JNoSQL

In the "nosql" folder, you'll find code that connects to a Redis database using the JNoSQL project.

This is a CDI project, so it has the required META-INF/beans.xml file with the default values. The *Device*, *TemperatureReadings* and *TemperatureStatus* are domain entities that will hold the values read for the devices. *DeviceService* and *StatusService* are the Service beans that the application will use to access the database instances. Note that StatusService defines a duration of 5 minutes (in *DEFAULT_DURATION*), so that its values expire after that time and entites saved there won't be available after 5 minutes.

### Running in the command line

Go back to the repository root and then...

```
cd nosql
mvn clean package
java -cp target/nosql-0.0.1.jar:target/lib/* jakarta.nosql.demo.App YOUR_DEVICE_ID
```

When you first run with a given Device ID (for example, *dvc42*), it will show that the device was not found and will create the entities for you. When you run it again, it'll be able to find the Device and Status entities. If you run again after 5 minutes, only the Device entity will be found, because of the expiration mentioned before.

Running dvc42 for the first time:
```
13:20:47.899 [main] DEBUG jakarta.nosql.demo.App - Device ID found: false
13:20:47.899 [main] DEBUG jakarta.nosql.demo.App - No Device with given ID, creating one with 0C as value
13:20:48.116 [main] DEBUG jakarta.nosql.demo.App - Device & Status created
```

Running dvc42 for the second time:
```
13:21:35.042 [main] DEBUG jakarta.nosql.demo.App - Device ID found: true
13:21:35.042 [main] INFO jakarta.nosql.demo.App - Device found: Device{id='dvc42', name='name', lastStatus=TemperatureStatus{time=2019-09-19T13:20:47.953, temperature=0.0}}
13:21:35.046 [main] INFO jakarta.nosql.demo.App - Status found: true
13:21:35.047 [main] INFO jakarta.nosql.demo.App - Device Status: TemperatureReadings{id='dvc42', status=TemperatureStatus{time=2019-09-19T13:20:47.953, temperature=0.0}}
```

Running dvc42 for a third time, 5 minutes or more after the first run:
 
