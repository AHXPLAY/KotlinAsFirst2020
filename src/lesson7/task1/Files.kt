@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import lesson3.task1.digitNumber
import java.io.File
import java.util.*

// Урок 7: работа с файлами
// Урок интегральный, поэтому его задачи имеют сильно увеличенную стоимость
// Максимальное количество баллов = 55
// Рекомендуемое количество баллов = 20
// Вместе с предыдущими уроками (пять лучших, 3-7) = 55/103

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var currentLineLength = 0
    fun append(word: String) {
        if (currentLineLength > 0) {
            if (word.length + currentLineLength >= lineLength) {
                writer.newLine()
                currentLineLength = 0
            } else {
                writer.write(" ")
                currentLineLength++
            }
        }
        writer.write(word)
        currentLineLength += word.length
    }
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            writer.newLine()
            if (currentLineLength > 0) {
                writer.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(Regex("\\s+"))) {
            append(word)
        }
    }
    writer.close()
}

/**
 * Простая (8 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Некоторые его строки помечены на удаление первым символом _ (подчёркивание).
 * Перенести в выходной файл с именем outputName все строки входного файла, убрав при этом помеченные на удаление.
 * Все остальные строки должны быть перенесены без изменений, включая пустые строки.
 * Подчёркивание в середине и/или в конце строк значения не имеет.
 */
fun deleteMarked(inputName: String, outputName: String) {
    val inputFile = File(inputName)
    val outputFile = File(outputName).bufferedWriter()
    inputFile.forEachLine { line ->
        var isStartUnderscore = false
        if (line.isNotEmpty()) {
            isStartUnderscore = line[0] == '_'
        }
        if (!isStartUnderscore) outputFile.write(line + "\n")
    }
    outputFile.close()
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val inputFile = File(inputName)
    val text = inputFile.readLines().joinToString("\n").toLowerCase()
    val numOfSubstrings = mutableMapOf<String, Int>()
    for (j in substrings) {
        val counter = searchSubstrings(text, j.toLowerCase())
        numOfSubstrings[j] = counter
    }
    return numOfSubstrings
}

fun searchSubstrings(text: String, word: String): Int {
    if (word.isEmpty()) {
        return 0
    }
    var counter = 0
    val lastCharIndex = word.length - 1
    for (i in text.indices) {
        when {
            text[i] != word[lastCharIndex] -> continue
            (i + 1 >= word.length) &&
                    (i < text.length) &&
                    word == text.substring(i - word.length + 1, i + 1) -> counter++
        }
    }
    return counter
}

/**
 * Средняя (12 баллов)
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val inputFile = File(inputName)
    val outputFile = File(outputName).bufferedWriter()
    val setOfSizzlingLetters = setOf('ж', 'ч', 'ш', 'щ')
    val setOfWrongVowels = setOf('ы', 'я', 'ю')
    val text = inputFile.readLines().joinToString("\n")
    val textStringBuilder = StringBuilder(text)
    for (i in 0 until text.length - 1) {
        if (text[i].toLowerCase() in setOfSizzlingLetters) {
            val vowel = text[i + 1]
            if (vowel.toLowerCase() in setOfWrongVowels) {
                when (vowel) {
                    'ы' -> textStringBuilder[i + 1] = 'и'
                    'я' -> textStringBuilder[i + 1] = 'а'
                    'ю' -> textStringBuilder[i + 1] = 'у'
                    'Ы' -> textStringBuilder[i + 1] = 'И'
                    'Я' -> textStringBuilder[i + 1] = 'А'
                    'Ю' -> textStringBuilder[i + 1] = 'У'
                }

            }
        }
    }
    outputFile.write(textStringBuilder.toString())
    outputFile.close()
}

/**
 * Средняя (15 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val inputFile = File(inputName)
    val outputFile = File(outputName).bufferedWriter()
    val resultLines = mutableListOf<String>()
    var maxLength = 0
    for (line in inputFile.readLines()) {
        if (line.length > maxLength) maxLength = line.trim().length
        resultLines.add(line.trim())
    }
    for (i in resultLines.indices) {
        if (resultLines[i].length < maxLength) {
            val deltaLength = maxLength - resultLines[i].length
            resultLines[i] = " ".repeat(deltaLength / 2) + resultLines[i]
        }
    }
    outputFile.write(resultLines.joinToString("\n"))
    outputFile.close()
}

/**
 * Сложная (20 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val inputFile = File(inputName)
    val outputFile = File(outputName).bufferedWriter()
    var maxLength = 0
    val resultLines = mutableListOf<String>()
    for (line in inputFile.readLines()) {
        resultLines.add(line.trim())
    }
    for (i in resultLines.indices) {
        val wordsInLine = deleteEmptyStrings(resultLines[i].split(" "))
        resultLines[i] = wordsInLine.joinToString(" ")
        if (resultLines[i].length > maxLength) maxLength = resultLines[i].length
    }
    for (i in resultLines.indices) {
        val wordsInLine = resultLines[i].split(" ")
        val spaces = countSpaces(resultLines[i], maxLength)
        resultLines[i] = makeLineWithSpaces(wordsInLine, spaces)
    }
    outputFile.write(resultLines.joinToString("\n"))
    outputFile.close()
}

fun makeLineWithSpaces(words: List<String>, spaces: List<Int>): String {
    val resultSB = StringBuilder()
    resultSB.append(words.first())
    for (i in spaces.indices) {
        resultSB.append(" ".repeat(spaces[i] + 1) + words[i + 1])
    }
    return resultSB.toString()
}

fun countSpaces(line: String, maxLength: Int): List<Int> {
    val len = line.length
    val numOfSpaces = line.split(" ").size - 1
    val resultList = mutableListOf<Int>()
    if (numOfSpaces > 0) {
        val spacesBetweenWords = (maxLength - len) / numOfSpaces
        val numOfBiggerSpaces = (maxLength - len) % numOfSpaces
        for (i in 0 until numOfSpaces) {
            var num = spacesBetweenWords
            if (i < numOfBiggerSpaces) num++
            resultList.add(num)
        }
    }
    return resultList
}

fun deleteEmptyStrings(list: List<String>): List<String> {
    val newList = mutableListOf<String>()
    for (word in list) if (word.isNotEmpty()) newList.add(word)
    return newList
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 * Вернуть ассоциативный массив с числом слов больше 20, если 20-е, 21-е, ..., последнее слова
 * имеют одинаковое количество вхождений (см. также тест файла input/onegin.txt).
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> = TODO()

/**
 * Средняя (14 баллов)
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная (22 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val inputFile = File(inputName)
    val lines = inputFile.readLines()
    val outputFile = File(outputName).bufferedWriter()
    val openTags = listOf("<html>", "<body>", "<p>", "<i>", "<b>", "<s>")
    val closeTags = listOf("</html>", "</body>", "</p>", "</i>", "</b>", "</s>")
    val allowedSymbols = setOf('*', '~')
    val htmlSB = StringBuilder()
    val stackOfTags = Stack<Int>()

    fun chooseTags(markdown: String): String {
        val mdList = markdown.toList()
        var symbols = ""
        var addedTags = ""
        for (j in mdList.indices) {
            var lastTag = -1
            if (!stackOfTags.empty()) lastTag = stackOfTags.peek()
            when (mdList[j]) {
                '*' -> {
                    if (j + 1 < mdList.size && mdList[j + 1] == '*') {
                        symbols += '*'
                        continue
                    } else {
                        symbols += '*'
                        when (symbols.length) {
                            1 -> {
                                symbols = ""
                                when (lastTag) {
                                    3 -> {
                                        addedTags += closeTags[3]
                                        stackOfTags.pop()
                                    }
                                    else -> {
                                        addedTags += openTags[3]
                                        stackOfTags.push(3)
                                    }
                                }
                            }
                            2 -> {
                                symbols = ""
                                when (lastTag) {
                                    4 -> {
                                        addedTags += closeTags[4]
                                        stackOfTags.pop()
                                    }
                                    else -> {
                                        addedTags += openTags[4]
                                        stackOfTags.push(4)
                                    }
                                }
                            }
                            3 -> {
                                symbols = ""
                                when (lastTag) {
                                    3 -> {
                                        addedTags += closeTags[3]
                                        stackOfTags.pop()
                                        if (!stackOfTags.empty() && stackOfTags.peek() == 4) {
                                            addedTags += closeTags[4]
                                            stackOfTags.pop()
                                        } else {
                                            addedTags += openTags[4]
                                            stackOfTags.push(4)
                                        }
                                    }
                                    4 -> {
                                        addedTags += closeTags[4]
                                        stackOfTags.pop()
                                        if (!stackOfTags.empty() && stackOfTags.peek() == 3) {
                                            addedTags += closeTags[3]
                                            stackOfTags.pop()
                                        } else {
                                            addedTags += openTags[3]
                                            stackOfTags.push(3)
                                        }
                                    }
                                    else -> {
                                        addedTags += openTags[4] + openTags[3]
                                        stackOfTags.push(4)
                                        stackOfTags.push(3)
                                    }
                                }
                            }
                        }
                    }
                }
                '~' -> {
                    if (symbols != "~") {
                        symbols += '~'
                        continue
                    } else {
                        symbols = ""
                        if (lastTag == 5) {
                            addedTags += closeTags[5]
                            stackOfTags.pop()
                        } else {
                            addedTags += openTags[5]
                            stackOfTags.push(5)
                        }
                    }
                }
            }
        }
        return addedTags
    }

    htmlSB.appendLine("${openTags[0]}\n${openTags[1]}\n${openTags[2]}")
    for (i in lines.indices) {
        if (lines[i].isEmpty() && (i > 0 && lines[i - 1].isNotEmpty())) {
            htmlSB.appendLine(closeTags[2])
            if (i < lines.lastIndex) htmlSB.appendLine(openTags[2])
            continue
        }
        var markdown = ""
        for (j in lines[i].indices) {
            var nextSym = ' '
            if (j + 1 < lines[i].length) nextSym = lines[i][j + 1]

            if (lines[i][j] in allowedSymbols && nextSym in allowedSymbols) markdown += lines[i][j]
            else if (lines[i][j] in allowedSymbols && nextSym !in allowedSymbols) {
                markdown += lines[i][j]
                htmlSB.append(chooseTags(markdown))
                markdown = ""
            } else htmlSB.append(lines[i][j])


        }
        htmlSB.appendLine()
    }

    htmlSB.appendLine("${closeTags[2]}\n${closeTags[1]}\n${closeTags[0]}")

    outputFile.use {
        it.write(htmlSB.toString())
    }
}

/**
 * Сложная (23 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body><p>...</p></body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<p>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>Или колбаса</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>Фрукты
<ol>
<li>Бананы</li>
<li>Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</p>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная (30 баллов)
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}


/**
 * Сложная (25 баллов)
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198    906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val divisionSB = StringBuilder()
    val result = lhv / rhv
    val resultDigits = numberIntoDigitsList(result)
    val lhvDigits = numberIntoDigitsList(lhv).toMutableList()

    var firstNum = -1
    var lineLength = 0
    var mod = 0
    for (i in resultDigits.indices) {
        val subtrahendNum = resultDigits[i] * rhv
        if (i == 0) {
            while (subtrahendNum > firstNum) {
                if (firstNum < 0) firstNum = 0
                firstNum *= 10
                firstNum += lhvDigits[0]
                lhvDigits.removeFirst()
            }
            divisionSB.appendLine(" ".repeat("-$subtrahendNum".length - digitNumber(firstNum)) + "$lhv | $rhv")
            divisionSB.append("-$subtrahendNum")
            val numOfSpaces =
                digitNumber(lhv) - "-$subtrahendNum".length + 3 + "-$subtrahendNum".length - digitNumber(firstNum)
            divisionSB.appendLine(" ".repeat(numOfSpaces) + result)

            lineLength = "-$subtrahendNum".length
            val numOfDashes = lineLength
            divisionSB.appendLine("-".repeat(numOfDashes))
            mod = firstNum - subtrahendNum
            divisionSB.append(" ".repeat(lineLength - digitNumber(mod)) + mod)
        } else {
            if (lhvDigits.isNotEmpty()) {
                divisionSB.appendLine(lhvDigits.first())
                firstNum = mod * 10 + lhvDigits.first()
                lineLength++
                lhvDigits.removeFirst()
            }
            divisionSB.appendLine(" ".repeat(lineLength - "-$subtrahendNum".length) + "-$subtrahendNum")
            mod = firstNum - subtrahendNum
            val numOfDashes = maxOf("-$subtrahendNum".length, digitNumber(mod))
            divisionSB.appendLine(" ".repeat(lineLength - numOfDashes) + "-".repeat(numOfDashes))
            divisionSB.append(" ".repeat(lineLength - digitNumber(mod)) + mod)
        }

    }

    val outputFile = File(outputName).bufferedWriter()
    outputFile.use {
        it.write(divisionSB.toString())
    }
}

fun numberIntoDigitsList(number: Int): List<Int> {
    var num = number
    val listOfDigits = mutableListOf<Int>()
    if (num == 0) listOfDigits.add(0)
    while (num > 0) {
        listOfDigits.add(num % 10)
        num /= 10
    }
    return listOfDigits.reversed()
}


