# Fan-Out Data Processing Engine

Author: Teja Yavvari

## Description
This project reads records from a flat file and distributes them concurrently to multiple sinks using a fan-out architecture. Each sink processes the data independently with its own transformation and throttling logic.

## Features
- Streaming file ingestion (no full file in memory)
- Multi-queue fan-out design
- JSON transformation using Gson
- REST API Sink simulation
- Message Queue Sink simulation
- Rate limiting per sink
- Concurrent execution using thread pools
- Graceful shutdown using EOF markers

## Technology Stack
- Java 22
- Maven
- Gson
- BlockingQueue
- ExecutorService

## How to Run
1. Open project in IntelliJ IDEA.
2. Ensure `sample.txt` exists in project root.
3. Run `FanoutApplication.java`.
4. Observe console output.

## Architecture Overview

sample.txt  
↓  
FileIngestor  
↓  
Fan-out Queues  
↓  
REST Sink     MQ Sink  
↓              ↓  
Rate Limiter   Rate Limiter  
↓              ↓  
Console Output

## Assumptions
- Input file contains one JSON record per line.
- Network latency is simulated using rate limiting.
- Each sink processes independently.
