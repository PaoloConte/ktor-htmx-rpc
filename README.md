# HTMX + Ktor — Pattern RPC con OOB Swaps

## L'idea

Il client non decide **dove** mettere le risposte. Fa solo:

```html
<button hx-post="/rpc/contactPage.startEdit"
        hx-vals='{"contactId": 42}'
        hx-swap="none">
    Modifica
</button>
```

`hx-swap="none"` → il client non fa nulla con la risposta.
Il server decide tutto via **OOB (Out-of-Band) swaps**.

## Architettura

```
Client (HTML + HTMX)           Server (Kotlin/Ktor)
─────────────────────           ─────────────────────
                                
button.click ──────────────►  /rpc/contactPage.saveEdit
  hx-swap="none"                    │
  hx-vals={contactId: 42}          ▼
                                ContactPage.saveEdit()
                                    │
                                    ├─ business logic
                                    │
                                    ├─ respondHtmx {
                                    │    replaceElement("#contact-42") { ... }
                                    │    updateContent("#contact-count") { ... }
                                    │    toast("Salvato!", "success")
                                    │  }
                                    │
                                    ▼
◄──────────────────────────  HTML con hx-swap-oob su ogni div
  HTMX processa ogni
  fragment OOB e aggiorna
  il DOM automaticamente
```

## Struttura

```
src/main/kotlin/
├── core/
│   ├── HtmxDsl.kt         # DSL: htmxResponse { oobSwap(...) }, respondHtmx { ... }
│   └── RpcRouter.kt        # Router: rpcRoutes("pageName") { action("name") { ... } }
├── model/
│   └── Contact.kt          # Domain model
├── service/
│   └── ContactService.kt   # Business logic (in-memory per demo)
├── pages/
│   ├── ContactPage.kt      # ⭐ RPC handler — tutte le azioni della pagina contatti
│   └── fragments/
│       └── ContactFragments.kt  # Componenti HTML riutilizzabili (kotlinx.html)
└── app/
    └── Application.kt      # Ktor setup, layout, routing
```

## I file chiave

### `core/HtmxDsl.kt` — Il DSL

```kotlin
call.respondHtmx {
    replaceElement("#contact-42") { contactRow(contact) }  // outerHTML
    updateContent("#stats") { +"Totale: 5" }               // innerHTML
    append("#list") { newItem(item) }                       // beforeend
    removeElement("#item-3")                                // delete
    toast("Fatto!", "success")                              // prepend a #toast-area
    redirect("/login")                                      // HX-Redirect header
    pushUrl("/contacts/42")                                 // aggiorna URL bar
}
```

### `core/RpcRouter.kt` — Il routing

```kotlin
fun Route.contactPageRoutes() = rpcRoutes("contactPage") {
    action("startEdit") { call -> ... }    // POST /rpc/contactPage.startEdit
    action("saveEdit") { call -> ... }     // POST /rpc/contactPage.saveEdit
    action("search") { call -> ... }       // POST /rpc/contactPage.search
}
```

### `pages/ContactPage.kt` — Le azioni

Ogni azione sa esattamente quali parti del DOM aggiornare perché
conosce il contesto della pagina. Il client non specifica target/swap.

## Come funziona il rendering OOB

`HtmxResponse.render()` genera HTML tipo:

```html
<div id="contact-42" hx-swap-oob="outerHTML">
    <tr>...</tr>
</div>
<div id="contact-count" hx-swap-oob="innerHTML">
    Contatti: 5
</div>
<div id="toast-area" hx-swap-oob="afterbegin">
    <div class="toast toast-success">Salvato!</div>
</div>
```

HTMX intercetta ogni elemento con `hx-swap-oob` e lo processa
indipendentemente, aggiornando il DOM nei punti giusti.

## Run

```bash
./gradlew run
# → http://localhost:8080
```

## Trade-off di questo approccio

**Vantaggi:**
- Il server è la single source of truth per il layout
- Cambi il layout? Modifichi solo il server
- Il client HTML è semplicissimo — solo `hx-post` + `hx-swap="none"`
- Nessuna logica lato client su dove mettere le risposte

**Svantaggi:**
- Il server deve conoscere gli ID del DOM
- Leggendo l'HTML non capisci cosa succede al click — devi leggere il server
- Più accoppiamento server↔layout rispetto al pattern REST classico HTMX
- OOB non supporta CSS selectors complessi — solo ID
