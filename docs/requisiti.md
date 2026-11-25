# Requisiti

## Obiettivo

Realizzare una libreria in Scala 3 che permetta di definire ed eseguire agenti basati su modelli linguistici (LLM), usando un DSL tipizzato e il paradigma funzionale.

## Requisiti funzionali

- **RF1 – Definizione di un agente:** deve essere possibile definire un agente tramite il DSL della libreria.
- **RF2 – Definizione dei tool:** deve essere possibile definire strumenti che l'agente può utilizzare.
- **RF3 – Associazione dei tool:** deve essere possibile associare uno o più tool a un agente.
- **RF4 – Esecuzione:** deve essere possibile eseguire un agente, gestendo il dialogo con il modello e l'eventuale invocazione dei tool.
- **RF5 – Interpreti:** deve essere possibile eseguire lo stesso agente con interpreti diversi, senza modificarne la definizione.
- **RF6 – Gestione degli errori:** gli errori che si verificano durante l'esecuzione devono essere gestiti in modo esplicito.
- **RF7 – Esempi:** la libreria deve includere alcuni agenti di esempio e un semplice programma da riga di comando per provarli.