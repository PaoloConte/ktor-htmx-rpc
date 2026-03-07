# Ktor HTMX RPC

A Kotlin library for building HTMX-powered applications using a **Server-Side RPC** pattern with **Out-of-Band (OOB) Swaps**.

## The Core Concept

In traditional HTMX, the client often specifies *where* the response should be placed using `hx-target` and `hx-swap`.

In the **RPC Pattern**, the client simply triggers an action:

```kotlin
button("btn", "btn-primary") {
    rpc = MyPage::performAction
    hxVals = """{"id": "42"}"""
    +"Execute"
}
```

The server decides exactly which parts of the page need to be updated by returning **Out-of-Band (OOB) fragments**. This allows a single action to update multiple unrelated elements (e.g., updating a table row, a sidebar counter, and showing a notification) without the client needing to know the layout structure.

## Features

- **Type-Safe RPC**: Bind buttons and inputs directly to Kotlin functions using reflection-based routing.
- **OOB Swaps by Default**: Easily update any element in the DOM by its ID from any server response.
- **Lightweight HTML DSL**: A custom, minimal, and fast DSL for building HTML and HTMX responses in Kotlin.
- **Header Helpers**: Built-in support for HTMX response headers (`HX-Trigger`, `HX-Redirect`, `HX-Push-Url`, etc.).
- **Ktor Integration**: Seamlessly integrates into Ktor's routing.

## Architecture

```text
Client (HTML + HTMX)           Server (Ktor + Library)
─────────────────────           ─────────────────────
                                
button.click ──────────────►  POST /rpc/MyPage.saveData
  hx-swap="none"                    │
  hx-vals={id: 42}                  ▼
                               MyPage.saveData()
                                    │
                                    ├─ Business Logic
                                    │
                                    ├─ respondHtmx {
                                    │    replace { rowComponent(updated) }
                                    │    update("#count") { +"Total: 5" }
                                    │    triggerEvent("data-updated")
                                    │  }
                                    │
                                    ▼
◄──────────────────────────  HTML fragments with hx-swap-oob
  HTMX processes each
  OOB fragment and updates
  the DOM automatically.
```

## Key Components

### 1. RPC Routing

Register an entire object's methods as RPC endpoints:

```kotlin
routing {
    rpc {
        registerAll(MyPage) // Registers all matching suspend functions
    }
}
```

An RPC function signature looks like this:

```kotlin
suspend fun performAction(params: Parameters): RpcResponse {
    val id = params["id"] ?: error("Missing ID")
    // ... logic ...
    return {
        replace { itemRow(updatedItem) }
        triggerEvent("action-completed")
    }
}
```

### 2. The HTMX DSL

The `respondHtmx` builder allows you to perform complex DOM updates in a single response:

```kotlin
call.respondHtmx {
    // Replace an element (default strategy: outerHTML)
    replace { itemRow(item) } 
    
    // Update specific parts of the DOM using custom strategies
    tbody("item-list", BeforeEnd) { newItemRow(item) }
    
    // Shorthand to update any element by ID (default strategy: innerHTML)
    update("#counter") { +"10 Items" }

    // Remove an element
    removeElement("#item-3")
    
    // Set HTMX headers
    triggerEvent("items-updated")
    redirect("/dashboard")
    pushUrl("/items/42")
}
```

### 3. Lightweight HTML DSL

The library includes a simple, type-safe HTML DSL that is optimized for HTMX:

```kotlin
fun Tag.itemRow(item: Item) = tr {
    id = "item-${item.id}"
    td { +item.name }
    td {
        button("btn-danger") {
            rpc = MyPage::deleteItem
            hxVals = """{"id": "${item.id}"}"""
            +"Delete"
        }
    }
}
```

## Why this approach?

### Advantages
- **Single Source of Truth**: The server controls the UI state and layout entirely.
- **Decoupled Client**: The HTML doesn't need to know where responses go, making layout changes easier.
- **Multiple Updates**: Easily update many disparate parts of the page in response to a single user action.
- **Type Safety**: Use Kotlin's type system for both your business logic and your UI components.

### Considerations
- **DOM IDs**: The server must be aware of DOM element IDs to perform OOB swaps.
- **Coupling**: There is tighter coupling between the server logic and the specific UI layout compared to a pure REST/HTMX approach.

## Getting Started

### Installation

Add the dependency to your `build.gradle.kts`:

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.paoloconte:ktor-htmx-rpc:1.0")
}
```

### Run the Example

The project includes a complete "Contact Manager" example. To run it:

```bash
./gradlew :example:run
```

Visit `http://localhost:8080` to see it in action.

## License

MIT License - see [LICENSE](LICENSE) for details.
