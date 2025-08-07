# Invester

**Invester** to rozproszony system mikroserwisowy do pobierania, analizy i prezentacji informacji mogących mieć wpływ na rynek walutowy (konkretnie para EUR/USD). Skupia się na wpływie newsów oraz aktywności osób publicznych. Zaprojektowany z myślą o wydajności, modularności i łatwej skalowalności.

---

## Architektura

| Mikroserwis        | Główne zadania | Wymagania |
|--------------------|----------------|------------|
| **Scraper** | • Pobiera artykuły z RSS i stron (FXStreet, Investing.com) <br>• Używa zmodyfikowanego Selenium do zaciągania pełnej treści | Podłączony ekran |
| **Processor** | • Analiza treści przy użyciu AI (DeepSeep R1 32B) <br>• Łączenie newsów z osobami, zdarzeniami i wpływem na rynek | Mocne GPU (24gb VRAM+) |
| **Master** | • Przechowuje dane (Hibernate + MySQL) <br>• Udostępnia REST API i panel | Publiczne IP |

Mikroserwisy komunikują się poprzez ⚡**Apache Kafka**⚡.

---

## Funkcje

- Automatyczne pobieranie i parsowanie newsów gospodarczych  
- Analiza sentymentu i klasyfikacja treści przez AI  
- Identyfikacja i monitoring wpływowych osób oraz tematów  
- Centralny dashboard z REST API  
- Gotowość do skalowania dzięki architekturze mikroserwisowej  

---

## Monitorowane podmioty (WIP)

| Politycy / Liderzy | Instytucje / Firmy | Innowatorzy |
|--------------------|--------------------|-------------|
| Donald Trump       | FED / SEC          | Sam Altman (OpenAI) |
| Joe Biden          | BlackRock / Vanguard| Elon Musk (Tesla, SpaceX) |
| Xi Jinping         | Apple / Nvidia     | Christine Lagarde (ECB) |
| Vladimir Putin     | —                  | Jerome Powell (FED) |

---

## Technologie

- Java 17 + Spring Boot  
- Apache Kafka  
- Selenium WebDriver (przerobiony w celu uniknięcia wykrycia) 
- MySQL (ORM: Hibernate)  
- Caffeine Cache 
- React (dashboard – w trakcie budowy)  

---

## Diagram

![Invester Architecture](https://github.com/user-attachments/assets/16054f71-d5b3-4f09-9c4c-03226c5e79a4)

---
