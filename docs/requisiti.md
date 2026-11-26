# Requisiti

## Obiettivo

Realizzare una libreria in Scala 3 che permetta di definire ed eseguire agenti basati su modelli linguistici (LLM), usando un DSL tipizzato e il paradigma funzionale.

## Modello di dominio

- **Message**: un messaggio della conversazione. Ce ne sono quattro tipi: istruzioni di sistema, messaggio dell'utente, risposta del modello, risultato di un tool.
- **ModelReply**: la risposta del modello. È o testo (l'esecuzione finisce) o una richiesta di uno o più tool (l'esecuzione continua).
- **ToolRequest**: la richiesta di un tool fatta dal modello, con il nome del tool e i suoi argomenti.
- **Tool**: una funzione con un nome, una descrizione per il modello e degli argomenti. Esegue una sola azione concreta.
- **Agent**: l'insieme dei tool disponibili e del limite di giri. Descrive il ciclo, ma non sa come si parla col modello.
- **Interpreter**: il modo in cui l'agente viene eseguito davvero. Ce ne sono tre: reale, mock, logging.
- **AgentError**: gli errori previsti (tool inesistente, argomenti non validi, tool fallito, modello non raggiungibile, limite di giri superato, chiave mancante).

## Requisiti funzionali

- **RF1 – Definizione di un agente:** deve essere possibile definire un agente tramite il DSL della libreria.
- **RF2 – Definizione dei tool:** deve essere possibile definire strumenti che l'agente può utilizzare.
- **RF3 – Associazione dei tool:** deve essere possibile associare uno o più tool a un agente.
- **RF4 – Esecuzione:** l'esecuzione di un agente riceve la domanda dell'utente (testo) e restituisce o la risposta finale (testo) o un errore.
  - Il sistema manda al modello la conversazione e la lista dei tool dell'agente.
  - Se il modello risponde con testo, l'esecuzione termina e restituisce quel testo.
  - Se il modello chiede uno o più tool, il sistema li esegue, aggiunge richieste e risultati alla conversazione e richiama il modello.
  - Per evitare cicli infiniti, l'esecuzione si ferma dopo un numero massimo di giri e restituisce l'errore di limite superato. Il valore predefinito è 10 e può essere cambiato nella definizione dell'agente.
- **RF5 – Interpreti:** lo stesso agente può essere eseguito con interpreti diversi, senza modificarne la definizione. Gli interpreti sono tre:
  - reale: dialoga con un modello linguistico vero;
  - mock: risponde con risposte preparate in anticipo, in modo deterministico, senza rete;
  - logging: registra i passi dell'esecuzione (messaggi, tool chiamati, risultati).
- **RF6 – Gestione degli errori:** gli errori sono restituiti come valori, senza eccezioni, e fermano l'esecuzione. Gli errori previsti sono:
  - tool inesistente: il modello chiede un tool che l'agente non ha;
  - argomenti non validi: gli argomenti del tool non corrispondono a quanto atteso;
  - tool fallito: l'esecuzione del tool non riesce;
  - modello non raggiungibile: il modello non risponde;
  - limite di giri superato;
  - chiave API mancante: manca la variabile d'ambiente necessaria all'interprete reale.
- **RF7 – Esempi:** la libreria deve includere alcuni agenti di esempio e un semplice programma da riga di comando per provarli.