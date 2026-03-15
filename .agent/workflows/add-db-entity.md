---
description: add a new SQLDelight database entity following the existing Clean Architecture pattern
---

# Add New DB Entity

Follow these steps to add a new database entity. Replace `<EntityName>` with your actual entity name (e.g. `Trail`, `Badge`, `Checkpoint`).

## Step 1 — Add the Kotlin Entity Data Class

Add a new `@Serializable` data class to:
`shared/src/commonMain/kotlin/com.ohanyan/xhike/data/db/HikeEntity.kt`

Follow the existing pattern:
```kotlin
@Serializable
data class <EntityName>Entity(
    val id: Long? = null,
    val name: String = "",
    // add your fields here
)
```

If the new entity has a custom type (like an enum or a nested list), add a `ColumnAdapter` in `Database.kt` (see `hikeDiffAdapter` and `pointsAdapter` as examples).

## Step 2 — Define the SQL Table in TaskDatabase.sq

Open:
`shared/src/commonMain/sqldelight/com/ohanyan/xhike/TaskDatabase.sq`

Add a `CREATE TABLE` block and the required SQL queries at the bottom of the file. Example:
```sql
import com.ohanyan.xhike.data.db.<EntityName>Entity; -- only if using a custom Kotlin type

CREATE TABLE <entityName>Table (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL
    -- add your columns here
);

getAll<EntityName>s:
SELECT * FROM <entityName>Table;

insert<EntityName>:
INSERT INTO <entityName>Table(id, name) VALUES (?, ?);

get<EntityName>ById:
SELECT * FROM <entityName>Table WHERE id = ?;

delete<EntityName>:
DELETE FROM <entityName>Table WHERE id = ?;
```

## Step 3 — Register the Table in Database.kt

Open:
`shared/src/commonMain/kotlin/com.ohanyan/xhike/data/db/Database.kt`

1. If your entity uses a custom type with a `ColumnAdapter`, pass it in the `TaskDatabase(...)` constructor, following how `hikeTableAdapter` and `currentHikeAdapter` are registered.
2. Add internal functions that call `dbQuery` for each SQL query you defined (insert, getAll, getById, delete, etc.), following the pattern of `insertHike`, `getAllHikes`, `getHikeById`, and `deleteHike`.

Example:
```kotlin
internal fun getAll<EntityName>s(): List<<EntityName>Entity> {
    return dbQuery.getAll<EntityName>s(::map<EntityName>Entity).executeAsList()
}

internal fun insert<EntityName>(entity: <EntityName>Entity) {
    dbQuery.insert<EntityName>(entity.id, entity.name)
}

internal fun delete<EntityName>(id: Long) {
    dbQuery.delete<EntityName>(id)
}

private fun map<EntityName>Entity(id: Long, name: String): <EntityName>Entity {
    return <EntityName>Entity(id, name)
}
```

## Step 4 — Add Methods to the Domain Repository Interface

Open:
`shared/src/commonMain/kotlin/com.ohanyan/xhike/domain/repository/DBRepository.kt`

Add the new operation signatures, for example:
```kotlin
fun getAll<EntityName>s(): List<<EntityName>Entity>
fun insert<EntityName>(entity: <EntityName>Entity)
fun delete<EntityName>(id: Long)
```

## Step 5 — Implement in DBRepositoryImpl

Open:
`shared/src/commonMain/kotlin/com.ohanyan/xhike/data/repository/DBRepository.kt`

Implement the new interface methods by delegating to `database`, following the pattern of `insertHike`, `getAllHikes`, etc.:
```kotlin
override fun getAll<EntityName>s(): List<<EntityName>Entity> = database.getAll<EntityName>s()
override fun insert<EntityName>(entity: <EntityName>Entity) = database.insert<EntityName>(entity)
override fun delete<EntityName>(id: Long) = database.delete<EntityName>(id)
```

## Step 6 — Create Use Cases

Add new use case files under:
`shared/src/commonMain/kotlin/com.ohanyan/xhike/domain/usecases/`

Follow the existing use case pattern (see `GetHikesUseCase.kt`, `InsertHikeInDbUseCase.kt`, `DeleteHikeUseCase.kt`):
```kotlin
class Get<EntityName>sUseCase(private val dbRepository: DBRepository) {
    operator fun invoke(): List<<EntityName>Entity> = dbRepository.getAll<EntityName>s()
}
```

Create one use case per operation (get all, get by id, insert, delete, update as needed).

## Step 7 — Register in DI (Koin)

Open:
`shared/src/commonMain/kotlin/com.ohanyan/xhike/di/CommonModule.kt`

Register your new use cases following the existing pattern:
```kotlin
factory { Get<EntityName>sUseCase(get()) }
factory { Insert<EntityName>UseCase(get()) }
factory { Delete<EntityName>UseCase(get()) }
```

## Step 8 — Build & Verify

// turbo
Run `./gradlew :shared:build` from the project root to verify SQLDelight code generation and compilation succeed.
