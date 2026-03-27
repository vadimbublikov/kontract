# Kontract
Kontract is a Kotlin library for describing a domain using DSL. It allows you to declaratively define entities, their attributes, relationships, and business operations, creating a single source of knowledge about your system, without binding to the database. A description in the form of code, instead of diagrams, allows LLM to better understand your project.  

---------------------

Kontract — это Kotlin-библиотека для описания предметной области с использованием DSL. Она позволяет декларативно определять сущности, их атрибуты, связи и бизнес-операции, создавая единый источник знаний о вашей системе, без привязки к БД. Описание в виде кода, вместо диаграмм, позволяет LLM лучше понимать ваш проект.

## Примеры

### Объявление сущности, без ссылок на другие сущности

```kotlin
object Country : MetaModel(name = "Страны", businessPackage = Dictionary) {
    val code = string(code = "code", name = "Числовой код", length = 3).required()
    val codeAlf2 = string(code = "codeAlf2", name = "Буквенный код Альфа-2", length = 2).required()
    val codeAlf3 = string(code = "codeAlf3", name = "Буквенный код Альфа-3", length = 3).required()
    val name = string(code = "name", name = "Наименование", length = 160).required()
}
```

### Сущность с ссылкой на другую сущность

```kotlin
object Currency : MetaModel(name = "Валюты", businessPackage = Dictionary) {
    val code = string(code = "code", name = "Буквенный код", length = 3).required()
    val codeNum = string(code = "codeNum", name = "Числовой код", length = 3).required()
    val name = string(code = "name", name = "Наименование", length = 160).required()
    val country = (long(code = "country", name = "Ссылка на страну") references Country.id)
}
```

### Сущность, которую можно добавлять в различные коллекции

```kotlin
object Address : CollectionMetaModel(name = "Адрес", businessPackage = Dictionary) {
    val city = string(code = "city", name = "Город/населенный пункт", length = 100)
    val country = (long(code = "country", name = "Страна") references Country.id)
    val line1 = string(code = "line1", name = "Строка 1", length = 100)
    val line2 = string(code = "line2", name = "Строка 2", length = 100)
    val note = string(code = "note", name = "Примечания", length = 250)
    val postCode = string(code = "postCode", name = "Индекс", length = 6)
}
```

### Сущность с коллекциями адресов и контактов

```kotlin
object Counterparty : MetaModel(name = "Контрагент", businessPackage = Dictionary) {
    val type = string(code = "type", name = "Тип", length = 100)
    val inn = string(code = "inn", name = "ИНН/TIN", length = 36)
    val kpp = string(code = "kpp", name = "КПП", length = 9)
    val name = string(code = "name", name = "Наименование", length = 160)
    val category = string(code = "category", name = "Группа", length = 100)
    val fullName = string(code = "fullName", name = "Полное наименование", length = 1000)
    val internationalName = string(code = "internationalName", name = "Интернациональное наименование", length = 160)
    val residency = string(code = "residency", name = "Резидентство", length = 100)
    val country = (long(code = "country", name = "Юрисдикции/гражданство") references Country.id)
    val addresses = (long(code = "addresses", name = "Адреса") collection Address.collectionId)
    val code = string(code = "code", name = "Код контрагента", length = 30)
}
```

### Сущность с иерархией экземпляров

```kotlin
object BusinessUnit : TreeMetaModel(name = "Business units", businessPackage = Dictionary) {
    val code = string(code = "code", length = 30).required()
    val name = string(code = "name", length = 160).required()
    val startDt = date(code = "startDt", name = "Start date").required()
    val endDt = date(code = "endDt", name = "End date")
    val reportName = string(code = "reportName", name = "Наименование для отчетов", length = 160)
    val counterparty = (long(code = "counterparty", name = "Контрагент") references Counterparty.id)
}
```


