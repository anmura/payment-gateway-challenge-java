# Key Design Considerations

## 1. **Validation**

All incoming request data is validated before any processing to ensure that all incoming requests are complete, correctly formatted, and safe to process.

## 2. **Logging and Observability**

Logging is kept clear and easy to follow so issues can be found quickly. Only safe, non‑sensitive information is logged, so private data is never exposed. This helps track what happened during a payment without risking security.

## 3. **Use of DTOs**

More DTOs were added to keep the data easy to understand and well‑organized. They separate what the system uses internally from what is sent or received from outside.


### 4. **Testing**

The project uses simple unit and integration‑like tests to check how different parts of the system work together. Even though the application does not use a real database, the tests still simulate this kind of flow to make sure the main payment logic works as a whole.