# JSON Parser & Validator  

This project implements a JSON parser, validator, and tree-based representation in Java without external libraries.  

## Features  

### 1. **JSON Validator (`JsonValidator` in `validator` package)**  
- Validates JSON input character-by-character.  
- Returns status:  
  - **"Status:Empty"** → if no input provided.  
  - **"Status:Incomplete"** → if input is valid but incomplete.  
  - **"Status:Valid"** → if input forms complete, valid JSON.  
  - **"Status:Invalid"** → if input is invalid.  
- Throws `InvalidJsonException` when JSON becomes invalid.  

### 2. **JSON Tree Representation (`jsontree` package)**  
- `JsonNode` → Abstract base class for JSON elements.  
- `JsonString` → Represents a JSON string.  
- `JsonArray` → Implements `IJsonArray`, storing ordered elements.  
- `JsonObject` → Implements `IJsonObject`, storing key-value pairs with key restrictions.  

### 3. **Pretty Printing (`prettyPrint` method in `JsonNode`)**  
- Formats JSON with proper indentation (2 spaces per level).  

### 4. **Data Sameness (`equals` method in `JsonNode`)**  
- JSON objects behave like sets, and arrays behave like sequences.  
- Determines if two JSON hierarchies represent the same data.  

### 5. **Tree-producing Parser (`JsonTreeBuilder` in `parser` package)**  
- Parses JSON character-by-character into a hierarchical tree.  
- Returns the root `JsonNode` upon valid JSON input.  

## Capabilities  
With this in place, the overall code will be capable of the following:  
- **Create a JSON hierarchy**, either programmatically or by parsing a valid JSON string.  
- **Convert a JSON hierarchy to a well-formatted JSON string.** This string, if fed to the parser, will recreate the same hierarchy.  
