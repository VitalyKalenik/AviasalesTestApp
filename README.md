<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Содержание](#%D1%81%D0%BE%D0%B4%D0%B5%D1%80%D0%B6%D0%B0%D0%BD%D0%B8%D0%B5)
- [Длина строки](#%D0%B4%D0%BB%D0%B8%D0%BD%D0%B0-%D1%81%D1%82%D1%80%D0%BE%D0%BA%D0%B8)
- [Правила именования](#%D0%BF%D1%80%D0%B0%D0%B2%D0%B8%D0%BB%D0%B0-%D0%B8%D0%BC%D0%B5%D0%BD%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F)
- [<a name='expression_formating'>Форматирование выражений</a>](#a-nameexpression_formating%D1%84%D0%BE%D1%80%D0%BC%D0%B0%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5-%D0%B2%D1%8B%D1%80%D0%B0%D0%B6%D0%B5%D0%BD%D0%B8%D0%B9a)
- [<a name='function'>Функции</a>](#a-namefunction%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%B8a)
  - [<a name='function_expression'>Функции с одним выражением</a>](#a-namefunction_expression%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%B8-%D1%81-%D0%BE%D0%B4%D0%BD%D0%B8%D0%BC-%D0%B2%D1%8B%D1%80%D0%B0%D0%B6%D0%B5%D0%BD%D0%B8%D0%B5%D0%BCa)
  - [<a name='formating_function_calling'>Форматирование вызова функции</a>](#a-nameformating_function_calling%D1%84%D0%BE%D1%80%D0%BC%D0%B0%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5-%D0%B2%D1%8B%D0%B7%D0%BE%D0%B2%D0%B0-%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%B8a)
  - [<a name='calling_function_variable'>Вызов переменной функционального типа</a>](#a-namecalling_function_variable%D0%B2%D1%8B%D0%B7%D0%BE%D0%B2-%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%B5%D0%BD%D0%BD%D0%BE%D0%B9-%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%BE%D0%BD%D0%B0%D0%BB%D1%8C%D0%BD%D0%BE%D0%B3%D0%BE-%D1%82%D0%B8%D0%BF%D0%B0a)
- [<a name='classes'>Классы</a>](#a-nameclasses%D0%BA%D0%BB%D0%B0%D1%81%D1%81%D1%8Ba)
- [<a name='annotation'>Аннотации</a>](#a-nameannotation%D0%B0%D0%BD%D0%BD%D0%BE%D1%82%D0%B0%D1%86%D0%B8%D0%B8a)
- [<a name='class_member_order'>Структура класса</a>](#a-nameclass_member_order%D1%81%D1%82%D1%80%D1%83%D0%BA%D1%82%D1%83%D1%80%D0%B0-%D0%BA%D0%BB%D0%B0%D1%81%D1%81%D0%B0a)
- [<a name='lambda_formating'>Форматирование лямбда-выражений</a>](#a-namelambda_formating%D1%84%D0%BE%D1%80%D0%BC%D0%B0%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5-%D0%BB%D1%8F%D0%BC%D0%B1%D0%B4%D0%B0-%D0%B2%D1%8B%D1%80%D0%B0%D0%B6%D0%B5%D0%BD%D0%B8%D0%B9a)
- [<a name='condition_operator'>Использование условных операторов</a>](#a-namecondition_operator%D0%B8%D1%81%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5-%D1%83%D1%81%D0%BB%D0%BE%D0%B2%D0%BD%D1%8B%D1%85-%D0%BE%D0%BF%D0%B5%D1%80%D0%B0%D1%82%D0%BE%D1%80%D0%BE%D0%B2a)
- [<a name='template_header'>Template header</a>](#a-nametemplate_headertemplate-headera)
- [<a name='files'>Файлы</a>](#a-namefiles%D1%84%D0%B0%D0%B9%D0%BB%D1%8Ba)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

В репозитории приведен набор соглашений по оформлению кода на языке Kotlin. 

Этот список правил расширяет предложенные [Google](https://android.github.io/kotlin-guides/style.html) и [командой разработки Kotlin](https://kotlinlang.org/docs/reference/coding-conventions.html) гайды и пересматривает в них некоторые неоднозначные моменты.

# Содержание
1. [Длина строки](#linelength)
2. [Правила именования](#naming)
3. [Форматирование выражений](#expression_formating)
4. [Функции](#function)
    * 4.1 [Функции с одним выражением](#function_expression)
    * 4.2 [Форматирование вызова функции](#formating_function_calling)
    * 4.3 [Вызов переменной функционального типа](#calling_function_variable)
5. [Классы](#classes)
6. [Аннотации](#annotation)
7. [Структура класса](#class_member_order)
8. [Форматирование лямбда-выражений](#lambda_formating)
9. [Использование условных операторов](#condition_operator)
10. [Template header](#template_header)
11. [Файлы](#files)


# Длина строки
- Рекомендуемая длина строки: 100 символов.
- Максимальная длина строки (точно надо переносить): 120 символов.

# Правила именования
- Пакеты именуются одним словом в стиле lowercase, в крайнем случае называть их в стиле lower_snake_case

# <a name='expression_formating'>Форматирование выражений</a>

При переносе на новую строку цепочки вызова методов символ `.` или оператор `?.` переносятся на следующую строку, property при этом разрешается оставлять на одной строке:
```kotlin
val collectionItem = source.collectionItems
                ?.dropLast(10)
                ?.sortedBy { it.progress }
```
Элвис оператор `?:` при разрыве выражения также переносится на новую строку:
```kotlin
val promoItemDistanceTradeLink: String = promoItem.distanceTradeLinks?.appLink
            ?: String.EMPTY
```
При описании переменной с делегатом, не помещающимися на одной строке, оставлять описание с открывающейся фигурной скобкой на одной строке, перенося остальное выражение на следующую строку:
```kotlin
private val promoItem: MarkPromoItem by lazy {
        extractNotNull(BUNDLE_FEED_UNIT_KEY) as MarkPromoItem
}
```

# <a name='function'>Функции</a>
## <a name='function_expression'>Функции с одним выражением</a>
* Позволительно использовать функцию с одним выражением только в том случае, если она помещается в одну строку.

## <a name='formating_function_calling'>Форматирование вызова функции</a>
* Использование именованного синтаксиса аргументов остается на усмотрение разработчика. Стоит руководствоваться сложностью вызываемого метода: если вызов метода с переданными в него параметрами понятен и очевиден, нет необходимости использовать именованные параметры.
При написании именованных аргументов делать перенос каждого аргумента на новую строку с двойным отступом и переносом закрывающейся круглой скобки на следующую строку:

```kotlin
runOperation(
		method = operation::run,
		consumer = consumer,
		errorHandler = errorHandler,
		tag = tag,
		cache = cache,
		cacheMode = cacheMode
)
```

## <a name='calling_function_variable'>Вызов переменной функционального типа</a>

* Всегда использовать полный вариант с написанием `invoke` у переменной вместо использования сокращенного варианта:
```kotlin
fun runAndCall(expression: () -> Unit): Result {
        val result = run()

        //Bad
        expression()
        //Good
        expression.invoke()

        return result
}
```

# <a name='classes'>Классы</a>
- Если описание класса не помещается в одну строку, и класс реализует несколько интерфейсов, то применять стандартные правила переносов, т.е. делать перенос только в случае, когда описание не помещается на одну строку, при этом продолжать перечисление интерфейсов на следующей строке.
- Использование именованного синтаксиса аргументов остается на усмотрение разработчика. Стоит руководствоваться сложностью используемого конструктора класса: если конструктор с переданными в него параметрами понятен и очевиден, нет необходимости использовать именованные параметры.

# <a name='annotation'>Аннотации</a>
- Аннотации, как правило, располагаются над описанием класса/поля/метода, к которому они применяются.
- Если к классу/полю/методу есть несколько аннотаций, размещать каждую аннотацию с новой строки:
```kotlin
@JsonValue
@JvmField
var promoItem: PromoItem? = null
```
- Если к полю/методу применяется только одна аннотация без параметров, указывать ее над полем/методом.

# <a name='class_member_order'>Структура класса</a>
// TODO Обсудить на следующем техно
1) companion object
2) Поля: abstract, override, public, internal, protected, private
3) Блок инициализации: init, конструкторы
4) Абстрактные методы
5) Переопределенные методы родительского класса (желательно в том же порядке, в каком они следуют в родительском классе)
6) Реализации методов интерфейсов (желательно в том же порядке, в каком они следуют в описании класса, соблюдая при этом порядок описания этих методов в самом интерфейсе)
7) public методы
8) internal методы
9) protected методы
10) private методы
11) inner классы

# <a name='lambda_formating'>Форматирование лямбда-выражений</a>

- При возможности оставлять лямбда-выражение на одной строке, используя `it` в качестве аргумента.
- Если выражение возможно написать с передачей метода по ссылке, передавать метод по ссылке (Доступно с 1.1):
```kotlin
viewPager.adapter = QuestAdapter(quest, this::onQuestClicked)
```
- При написании лямбда-выражения более чем в одну строку всегда использовать именованный аргумент, вместо `it`:
```kotlin
viewPager.adapter = QuestAdapter(quest, { quest ->
        onQuestClicked(quest)
})
```
- Неиспользуемые параметры лямбда-выражений всегда заменять символом `_`.
- Избегать использования [Destructuring Declarations](https://kotlinlang.org/docs/reference/multi-declarations.html) в лямбда-выражениях.

# <a name='condition_operator'>Использование условных операторов</a>
Не обрамлять `if` выражения в фигурные скобки только если условный оператор `if` помещается в одну строку.
При возможности использовать условные операторы, как выражение:
```kotlin
return if (condition) foo() else bar()
```
// TODO Обсудить на следующем техно<br>
Если в операторе `when` хоть в одной из ветвей есть фигурные скобки, обрамлять ими все остальные ветки.
У оператора `when` для блоков с выражениями, которые состоят более чем из одной строки использовать для этих блоков фигурные скобки и отделять смежные case-блоки пустой строкой:
```kotlin
when (feed.type) {
        FeedType.PERSONAL -> {
        	with(feed as PersonalFeed) {
        		datePopupStart = dateBegin
        		datePopupEnd = dateEnd
        	}
        }

        FeedType.SUM -> {
        	with(feed as SumFeed) {
        		datePopupStart = dateBegin
        		datePopupEnd = dateEnd
        	}
        }

        FeedType.CARD -> {
        	with(feed as CardFeed) {
        		datePopupStart = dateBegin
        		datePopupEnd = dateEnd
        	}
        }

        else -> {
        	Feed.EMPTY
        }
}
```

# <a name='template_header'>Template header</a>

- Не использовать Template Header для классов (касается авторства и даты создания файла).

# <a name='files'>Файлы</a>

- Возможно описывать несколько классов в одном файле только для `sealed` классов. В остальных случаях для каждого класса необходимо использовать отдельный файл (не относится к `inner` классам).
