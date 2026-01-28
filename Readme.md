# 🚀 High-Throughput Fan-Out Data Processing Engine

Author: Teja Yavvari  
Role: Software Engineer Intern

---

## 📌 Overview

This project implements a **High-Throughput Fan-Out Data Processing Engine** in Java.  
It reads records from a flat file in a streaming manner and distributes each record concurrently to multiple downstream sinks.

Each sink applies its own transformation logic and rate limiting, simulating real-world systems such as REST APIs, Message Queues, gRPC services, and Databases.

The architecture focuses on:
- ⚡ High throughput
- 🧠 Memory safety (streaming processing)
- 🔄 Concurrency and parallelism
- 📈 Scalability
- 🛡️ Reliability and backpressure handling

---

## 🎯 Problem Statement

Modern data systems often need to ingest a large dataset from a single source and distribute it to multiple independent systems without overwhelming downstream services.

This engine demonstrates:
- Fan-out distribution
- Non-blocking concurrent sinks
- Configurable throttling
- Zero data loss guarantees
- Extensible architecture for future sinks

---

## ✨ Features

- ✅ Streaming file ingestion (no full file loaded into memory)
- ✅ Fan-out architecture using multiple BlockingQueues
- ✅ Independent sink processing pipelines
- ✅ Per-sink rate limiting
- ✅ Concurrent execution using ExecutorService
- ✅ Graceful shutdown using EOF markers
- ✅ Observability using live metrics logging
- ✅ Easily extensible sink architecture
- ✅ Thread-safe counters and queues
- ✅ Config-driven behavior

---

## 🧱 Technology Stack

- Java 22
- Maven
- Gson
- BlockingQueue
- ExecutorService
- Concurrent Collections

---

## 🗂️ Project Structure

fanout-engine-app
├── src
│ └── main
│ └── java
│ └── com.teja.fanout
│ ├── FanoutApplication.java
│ ├── config
│ ├── ingestion
│ ├── metrics
│ ├── queue
│ ├── sink
│ ├── throttle
│ └── transform
├── sample.txt
├── pom.xml
└── README.md


---

## 🏗️ Architecture Overview

            sample.txt
                 |
                 v
          FileIngestor
                 |
    --------------------------------
    |        |        |           |
REST Q   gRPC Q    MQ Q       DB Q
|        |        |           |
REST Sink gRPC Sink MQ Sink DB Sink
| | | |
RateLimiter RateLimiter RateLimiter RateLimiter
| | | |
Console Output (Simulation)


---

## 🔄 Data Flow

1. FileIngestor reads the input file line-by-line.
2. Each record is pushed into multiple BlockingQueues.
3. Each Sink consumes from its own queue independently.
4. Each sink:
    - Transforms the data
    - Applies rate limiting
    - Simulates processing
5. Metrics are updated continuously.
6. EOF markers gracefully stop all consumers.

---

## ⚙️ How to Run

### Prerequisites
- Java 22 installed
- Maven installed
- IntelliJ IDEA recommended

### Steps

1. Clone the repository:

2. Open the project in IntelliJ IDEA.

3. Ensure `sample.txt` exists in the project root.

4. Run:


5. Observe console logs showing:
- Sink processing
- Metrics updates
- Throughput activity

---

## 📊 Observability

Metrics printed every few seconds:
- Total records processed
- Successful records
- Failed records
- Live throughput visibility

Example:

---

## 🚦 Backpressure & Throttling

- Each sink has its own BlockingQueue.
- Queues prevent memory overflow when sinks are slow.
- RateLimiter controls request throughput per sink.
- Producers naturally block when queues are full.

This ensures:
- No OutOfMemory errors
- Stable throughput under load
- Controlled downstream pressure

---

## 🔐 Reliability & Safety

- Thread-safe counters
- Blocking queues for synchronization
- Controlled shutdown using EOF signals
- No record is dropped silently
- Failures are tracked in metrics

---

## 🧩 Extensibility

Adding a new sink requires:
1. Implementing a new Sink class.
2. Adding a transformer if needed.
3. Registering the queue in FanoutApplication.

Core orchestration logic remains unchanged.

---

## 🧪 Testing Strategy

- Manual validation using sample files.
- Load testing by increasing input size.
- Validation of throttling behavior.
- Queue backpressure testing.

---

## 📁 Sample Input Format

{"id":1,"name":"Apple","price":120}
{"id":2,"name":"Banana","price":40}
{"id":3,"name":"Orange","price":60}
.......


---

## 🚀 Future Enhancements

- Retry mechanism with dead-letter queue
- Dynamic configuration loading
- Docker support
- Unit and integration tests
- Metrics export (Prometheus)
- Reactive Streams support

---

## 🤖 AI Prompts Used

Prompts included:
- Architecture design
- Concurrency modeling
- Queue backpressure strategies
- Rate limiting implementation
- Code optimization and debugging

---

## 📎 GitHub Repository

<PASTE YOUR GITHUB LINK HERE>

---

## 🙌 Conclusion

This project demonstrates a scalable, resilient, and extensible fan-out engine suitable for large-scale distributed data pipelines. It showcases strong engineering practices in concurrency, memory management, and system design.
