# Invester

**Invester** is a distributed microservices system for collecting, analyzing, and presenting information that may affect the currency market (specifically the EUR/USD pair). It focuses on the impact of news and public figures' activity. Designed for performance, modularity, and easy scalability.

---

## Actions
<img width="335" height="314" alt="image" src="https://github.com/user-attachments/assets/f5cae196-a909-450a-8858-71b337db98cb" />
Project deploys automically if commit message contains "#deploy" string.

## Features

- Automatic downloading and parsing of economic news  
- Sentiment analysis and content classification via AI  
- Identification and monitoring of influential people and topics  
- Central dashboard with REST API  
- Scalability-ready thanks to microservice architecture  

---

## Architecture

| Microservice        | Main tasks | Requirements |
|---------------------|------------|--------------|
| **Scraper** | • Collects articles from RSS and websites (FXStreet, Investing.com) <br>• Uses modified Selenium to fetch full content | Connected display |
| **Processor** | • Content analysis using AI (DeepSeep R1 32B) <br>• Links news with people, events, and market impact | Powerful GPU (24GB VRAM+) |
| **Master** | • Stores data (Hibernate + MySQL) <br>• Exposes REST API and dashboard | Public IP |

Microservices communicate via ⚡**Apache Kafka**⚡.

---


## Technologies

- Java 17 + Spring Boot  
- Apache Kafka  
- Selenium WebDriver (modified to avoid detection)  
- MySQL (ORM: Hibernate)  
- Caffeine Cache  
- React (dashboard – under development)  

---

## Diagram

<img width="701" height="533" alt="Untitled Diagram(1) drawio" src="https://github.com/user-attachments/assets/e3f8b45c-fa37-4a38-b763-a75629fb8bfc" />
