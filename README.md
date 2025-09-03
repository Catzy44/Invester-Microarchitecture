PROJECT REFACTOR IN PROGRESS, PLEASE DON'T MIND THE STRUCTURE.

# Invester

**Invester** is a distributed microservices system for collecting, analyzing, and presenting information that may affect the currency market (specifically the EUR/USD pair). It focuses on the impact of news and public figures' activity. Designed for performance, modularity, and easy scalability.

---

## Architecture

| Microservice        | Main tasks | Requirements |
|---------------------|------------|--------------|
| **Scraper** | • Collects articles from RSS and websites (FXStreet, Investing.com) <br>• Uses modified Selenium to fetch full content | Connected display |
| **Processor** | • Content analysis using AI (DeepSeep R1 32B) <br>• Links news with people, events, and market impact | Powerful GPU (24GB VRAM+) |
| **Master** | • Stores data (Hibernate + MySQL) <br>• Exposes REST API and dashboard | Public IP |

Microservices communicate via ⚡**Apache Kafka**⚡.

---

## Features

- Automatic downloading and parsing of economic news  
- Sentiment analysis and content classification via AI  
- Identification and monitoring of influential people and topics  
- Central dashboard with REST API  
- Scalability-ready thanks to microservice architecture  

---

## Monitored entities (WIP)

| Politicians / Leaders | Institutions / Companies | Innovators |
|------------------------|--------------------------|------------|
| Donald Trump           | FED / SEC                | Sam Altman (OpenAI) |
| Joe Biden              | BlackRock / Vanguard     | Elon Musk (Tesla, SpaceX) |
| Xi Jinping             | Apple / Nvidia           | Christine Lagarde (ECB) |
| Vladimir Putin         | —                        | Jerome Powell (FED) |

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
