# Appunti langchain4j

Fonte: documentazione ufficiale (Tutorials). Scopo: capire quali concetti modellare in agentdsl e come farlo in stile funzionale.

## Tabella concetti

| Concetto              | Come lo fa langchain                                                                   | Come si potrebbe rendere funzionale                                      |
|-----------------------|----------------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| Messaggi              | Interfaccia `ChatMessage` con 5 classi (User, Ai, ToolExecutionResult, System, Custom) | `enum Message`: System, User, Ai, ToolResult                             |
| Risposta del modello  | `AiMessage` con testo e/o richieste di tool                                            | Testo oppure lista di richieste di tool                                  |
| Conversazione         | Lista di messaggi rimandata a ogni chiamata (modello senza stato)                      | Lista immutabile di `Message`, ogni passo ne produce una nuova           |
| Richiesta/risposta    | `ChatRequest` con builder, `ChatResponse` con metadati                                 | Case class con valori di default, niente builder                         |
| Parametri del modello | Builder o factory, proprietà Quarkus/Spring                                            | Case class `ModelConfig` con default; chiave API da variabile d'ambiente |
| Memoria               | `ChatMemory` con politiche di eliminazione e store                                     | Funzione pura `List[Message] => List[Message]` (opzionale)               |
| Streaming             | Handler con callback mutabili                                                          | Stream di eventi (`enum`), estensione opzionale                          |
| Ciclo dell'agente     | Nascosto nel proxy di `AiServices` (reflection, annotazioni)                           | Funzione/DSL esplicita sui messaggi                                      |
| Specifica del tool    | `ToolSpecification` (nome, descrizione, parametri) o `@Tool`/`@P`                      | Case class degli argomenti + type class derivata                         |
| Richiesta di tool     | `ToolExecutionRequest` (id, nome, argomenti JSON)                                      | Case class con nome e argomenti                                          |
| Risultato del tool    | `ToolExecutionResultMessage`                                                           | Caso `ToolResult` di `Message`                                           |
| Errori sui tool       | Eccezioni o gestori configurabili                                                      | `AgentError` dentro `Either`                                             |
| Test                  | Mock dell'interfaccia                                                                  | Interprete mock deterministico                                           |

## Osservazioni utili

- Il modello non esegue i tool: esprime l'intenzione, li esegue il codice.
- Una risposta può contenere più richieste di tool (anche parallele).
- Alcuni provider (Google, Ollama) omettono l'id della richiesta.
- Il modello può inventare tool inesistenti o mandare argomenti malformati: va gestito.
- Decisione da prendere: un errore sugli argomenti va rimandato al modello o interrompe il ciclo?
- Messaggi di eccezione grezzi non vanno inviati al modello (rischio di dati interni).

## Decisioni

- La risposta del modello è o testo o richiesta di tool, mai tutte e due. Così il compilatore mi obbliga a gestire entrambi i casi.
- I tool li dichiaro una volta nell'agente, non li passo a ogni richiesta.
- La chiave API sta in una variabile d'ambiente, mai nel repo.
- Il ciclo ha un limite di giri, altrimenti rischia di non fermarsi mai.
- Se il modello chiede un tool che non esiste, il ciclo si ferma con un errore. Rimandare l'errore al modello lo faccio solo se avanza tempo.
- Se gli argomenti del tool sono sbagliati, errore esplicito e non crash.
- Se il modello manda testo insieme alla richiesta di tool, lo conservo in un campo facoltativo. Serve al logging per ricostruire cosa è successo; il ciclo non lo usa.
- Per i test uso ScalaTest con stile FlatSpec e Matchers (scelta predefinita consigliata dalla documentazione)