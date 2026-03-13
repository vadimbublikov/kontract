# AGENTS.md - Kontract Development Guide

This document provides guidelines for AI agents working on the Kontract Kotlin DSL library.

## Project Overview

Kontract is a Kotlin library for describing domains using DSL. It allows declarative definition of entities, attributes, relationships, and business operations, creating a single source of knowledge about a system.

## Build System

### Expected Build Tools
- **Primary**: Gradle with Kotlin DSL (build.gradle.kts)
- **Kotlin Version**: 1.9+ (check gradle.properties or build.gradle.kts)
- **JDK**: 17+

### Common Commands
```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Run a specific test class
./gradlew test --tests "com.example.ClassNameTest"

# Run a specific test method
./gradlew test --tests "com.example.ClassNameTest.methodName"

# Run tests with coverage
./gradlew test jacocoTestReport

# Clean build
./gradlew clean build

# Run lint/formatting checks
./gradlew ktlintCheck
./gradlew detekt

# Apply formatting
./gradlew ktlintFormat

# Publish to local Maven repository
./gradlew publishToMavenLocal

# Generate documentation
./gradlew dokkaHtml
```

## Code Style Guidelines

### Imports
- Use explicit imports (avoid wildcard imports `.*`)
- Group imports: Kotlin stdlib, third-party libraries, project modules
- Sort imports alphabetically within groups
- Remove unused imports

### Formatting
- **Indentation**: 4 spaces (no tabs)
- **Line length**: 120 characters maximum
- **Braces**: Use Kotlin-style (opening brace on same line)
- **Blank lines**: Use to separate logical sections
- **Trailing commas**: Use in multi-line collections/parameter lists

### Naming Conventions
- **Packages**: lowercase, no underscores (`com.example.kontract.domain`)
- **Classes/Interfaces**: PascalCase (`EntityDefinition`, `DomainModel`)
- **Functions/Methods**: camelCase (`defineEntity`, `validateModel`)
- **Variables/Properties**: camelCase (`entityName`, `isValid`)
- **Constants**: UPPER_SNAKE_CASE (`MAX_ENTITIES`, `DEFAULT_CONFIG`)
- **Test classes**: Suffix with `Test` (`EntityDefinitionTest`)
- **Test methods**: Use descriptive names with underscores (`should_validate_entity_when_name_is_empty`)

### Types
- Use explicit types for public API
- Leverage Kotlin's type inference for local variables
- Prefer `val` over `var` for immutability
- Use nullable types (`Type?`) only when necessary
- Use data classes for value objects
- Use sealed classes/interfaces for closed hierarchies

### Error Handling
- Use Kotlin's Result type or custom sealed result classes
- Throw meaningful exceptions with descriptive messages
- Use `require()` and `check()` for argument validation
- Use `assert()` for internal invariants (disabled in production)
- Avoid catching generic `Exception` - be specific

### DSL Design (Specific to Kontract)
- Use extension functions for fluent APIs
- Leverage `@DslMarker` annotations to prevent scope pollution
- Use infix functions judiciously for readability
- Provide sensible defaults for DSL elements
- Ensure DSL is type-safe and provides compile-time checks
- Use receiver parameters for context passing

### Testing
- Use JUnit 5 (`org.junit.jupiter:junit-jupiter`)
- Use KotlinTest or Kotest for property-based testing if needed
- Use MockK for mocking (`io.mockk:mockk`)
- Follow Given-When-Then pattern in test names/comments
- Test both happy paths and edge cases
- Test DSL usability and error messages

### Documentation
- Use KDoc for all public APIs
- Include `@param`, `@return`, `@throws` tags
- Document complex DSL usage with examples
- Keep README.md updated with usage examples
- Use `@sample` tags to link to example code

## Project Structure

Expected module structure:
```
kontract/
├── core/                    # Core DSL definitions
├── processor/              # Annotation processing
├── examples/               # Usage examples
├── docs/                   # Documentation
└── buildSrc/               # Custom Gradle plugins
```

## Common Patterns in Kontract

### DSL Definition Pattern
```kotlin
@DslMarker
annotation class KontractDsl

class DomainBuilder {
    fun entity(name: String, block: EntityBuilder.() -> Unit) { ... }
}

class EntityBuilder {
    var description: String = ""
    fun attribute(name: String, type: String, block: AttributeBuilder.() -> Unit) { ... }
}
```

### Validation Pattern
```kotlin
sealed class ValidationResult {
    data class Valid(val model: DomainModel) : ValidationResult()
    data class Invalid(val errors: List<ValidationError>) : ValidationResult()
}

fun validateDomain(block: DomainBuilder.() -> Unit): ValidationResult { ... }
```

## Development Workflow

1. **Before making changes**:
   - Run existing tests: `./gradlew test`
   - Check formatting: `./gradlew ktlintCheck`

2. **When adding features**:
   - Add tests for new functionality
   - Update documentation (KDoc and README)
   - Add examples if introducing new DSL constructs

3. **When fixing bugs**:
   - Add failing test first
   - Fix the issue
   - Ensure all tests pass

4. **Before committing**:
   - Run full test suite
   - Ensure formatting is correct
   - Verify no warnings in build

## Agent-Specific Notes

- Always check for existing patterns before adding new code
- Maintain backward compatibility for public APIs
- When in doubt, follow Kotlin Coding Conventions (https://kotlinlang.org/docs/coding-conventions.html)
- The DSL should be intuitive for both humans and LLMs to understand
- Prefer compile-time safety over runtime flexibility
- Keep the library lightweight and focused on its core purpose

## Useful Resources

- Kotlin DSL Guide: https://kotlinlang.org/docs/type-safe-builders.html
- Gradle Kotlin DSL: https://docs.gradle.org/current/userguide/kotlin_dsl.html
- Kotlin Coding Conventions: https://kotlinlang.org/docs/coding-conventions.html
- KDoc Syntax: https://kotlinlang.org/docs/kotlin-doc.html