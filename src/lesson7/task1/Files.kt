@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import lesson3.task1.digitNumber
import java.io.File
import java.util.*
import kotlin.math.pow

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


fun main() {
    //markdownToHtmlSimple("input/markdown_tags_test.md", "")
    val str =
        "<html><body><p>,H@<b></b>D<b>C<s>I?<i>G&S#</i>sdpyt}</s>\\</b>L<s>F(Xf#<b>h</b><b>b<i>$</i>{c3</b>R<b>0j<i>(</i>P<i></i>tNoZ<i>+K#.A</i></b>9A7</s><s>js<b>C</b>cS`T9<i><b></b>tO2UTD</i>@l<i>,R</i>M/aIUJ</s>G2<i>3q<b><s>4</s><s>f|</s>cBBvB<s>LxSB</s><s>x</s>]H}</b>U`<s>[Z<b>K</b><b></b>w}fG\"<b>L&vLb</b>NZn</s>T<s>}</s>DW<b>WAG&#Yr<s>K</s><s>e5</s>0-</b>7<b></b>]'AVi7w</i>+<s>p5<i></i></s>U<i>3h<s>|<b>{</b>V\\A<b>_;V</b><b>p]v</b></s>x@;@</i><i>7E4P<s>3My<b>9</b>Hb4b#h.</s>w[t<s>g]<b>91</b>\"</s>=N<s></s>31ny_<b></b>z</i>tr3)n{8p\"9gfWV</p><p>mS<b>}\\D<i>Gs<s></s>?a'q</i></b>tM)s<i>2</i>9?<s><b>oA+<i>t</i>D<i>.f?;</i>{1SO\$Z<i></i>g[<i>+6</i>dag</b>.FBI<i><b>MgQ</b>j[u-_UJ)JR<b></b>}m</i>bro</s>A<i><b>Tj<s>.</s><s>!0m</s>;c\\wu'<s>.</s>aP</b>mUp<s>d</s>H'mY<s>aSVwp&</s>Mdy<b>;</b>R<s>N\\<b>]y</b>zY^</s>@</i><i>$:Dg=<b>e<s></s>BA</b>c<s>\\</s>EG\"<s>a^6</s><b>/[@<s>6</s></b>q<b>w<s></s>h</b>j6<b>z<s>FYXI]2</s>[?(NJ5<s></s>N</b>4<b>?U<s>syY</s>.!h92<s>A</s>&z<s>[E</s>0zy</b></i>J5m{,d}z<i>Z_ut</i>W&rD\"=h!T]<b>&]Z\\<s>y<i>bq+</i>8</s>]U)gLvZsw#dQ</b>z{<b>n'<i>XD</i>Wl\$q<i></i>u#ufg<i>X)<s>\"</s>iS5Kt</i>f@zq}OKpuL#zI0x<i>rpq}\\</i>8e1<i>&v<s>:</s>7O6<s>M&}mWt</s>@</i></b>Nl<s>C<i>9tiJ<b>\";z|</b>pBE.lge<b>Wmg(UN</b>'</i>G</s>:K</p><p>d@:8#9a<s>QZE&iBD</s>)<i>c<s>gq</s></i>A+Cw&D(af^{:5<i>$]<s>3m<b>Z#</b>X<b>I^R[L</b>9!<b>w</b>|</s>U;$<s>/p</s>U^c</i>|<b>=y\"<s>Sy</s>X<i>k\"y11:A2`<s>X|</s><s></s>=</i>jV<i>6</i>X</b>H<b>(!e={ea7g4&gM|RC(EP</b>v^<s>K<i>6et</i>}<i>LJDcQvOA<b>=</b>Vd%_%c</i><b>OmA)l\"o]{Jy=</b>5pv</s>3O#=6<s><i>\"W;N</i>H</s>YR+`WkQqOA1Z.ptDVe<s><i>m</i><b>M_rG<i>9</i></b>1+08u.)=P</s>Y<b></b><i>9H[vcp<b>=0<s>j</s>N@<s>K`IcI,</s>R</b>eR_MM|<b>1<s></s>qL?<s>x?</s>w<s>#N</s></b>J<b></b>s#</i>6;<b>E\"<i>;5<s>M8A</s><s></s>/p</i>+&8e<i>r<s>_;XE.&</s>2b</i>)6J</b>ogg\$uEm<b>'_Q<s>HtqH7AFl</s>G</b>$\"<b>jt<s>K</s>Hnv8!wZ4<s>d([MbV{B<i>c</i>dJTG'x3M5<i>fZ!=kj</i>h/0<i></i>^o</s>pF</b>|$&Xp/h<i>J\\i!3DxhX<s>a=:,<b>\"</b>`|0<b>s]iR</b>/zUlg\\++;vE<b>Yw</b></s>(<s>i</s>!o{)MS]qQkfwn<b>d?D|<s></s>5scH<s></s>w,B</b>P</i>qQJ<i><s>7v)gAOd<b></b>A<b></b>/2<b>^</b>_.</s>.JN5qK`c-M1sE</i>{Z7<s>f</s>NpGEhoFni^qk+J'#L,U'FT's,S9n1y?86<i>rR:5T0lyh<b>^mK,BA(a#<s>[_jt?n;;2?)9Xf:Wc\\aRuGw</s>E}6,A+=</b></i>8y,x<s><i>cU}W$</i>a+5@h</s>\$K@/mQPCWFYnw-hBZ1-<i>]ub,N9Q_Yw2,<b>`|aUmEZI@<s>i@a`_)ffR$</s>7B<s></s>DIk</b>#TCy</i>AKzo.R<i>0o<b>=8$;=u</b>cQKf<s>X5WO[]+Xw%</s><b>JmFKGj:E9PS\\_bjsj</b>MQPo{{tD'/eV</i>su<i>ME.o\$S</i>fW%<i>!=S[lME{5G<s>c</s>d1<b>H`Q<s></s>5Co)<s>35`Pl+</s>fdR!f38</b>gH@bxra7&x</i>[</p><p>csQzEgwbuj8t+0R7</p><p>lcZrA{O|`(g#G<i>@iC</i>=Kr?5&2FE$<s>o}@20yb)cHj<b>}<i>q</i>u#f<i>bt5bEq04</i>faJ6hd<i>/</i>(</b><b>b</b>.`},U<i>Y</i>Q|xF?{C&<i>s<b>svoF</b><b>L4}</b>2Yq6l:[</i>1WS8{2QR}{?\"<b>r+6jNRdf7O<i>f</i><i>SQ</i>6eg{L<i>et</i>S<i>69</i>z0</b>p&8<b>^</b>;j&^:E</s>m<b>)b<s></s>8-</b>&v<b>+H</b>p%wB2gGznG\"kL(ox<s>om</s>Bo4K(aDNZ!aaV;;RW!j]o3-IPT##1!w:\$Pd7PuZW<b>j<i><s></s>+c3'a</i><s>2LvA@NS\$SQ</s>VasgBy\"</b>\\:8sbC\")auR!vS?<s>Qg<b>X.3`SO}<i>mA^Z/N}</i>HcTW</b>2EJ=</s>`.d|<s><b>F&</b>g-foL_</s>yW[{g\\<i>XT<b>oY\$FmnQm)<s>D?l1</s>U`Z]i]IqHW</b>&y</i>-TG}2-XjzPA_STrzFOl2\\b<s>l<i>,DooL7L%</i>qW\\{<b>|t9d;By!h}ZMR<i>q;)dq</i>|.tS%&+kg</b><b>W%MI,</b>X-^</s>\\.7]<b><s></s>kS</b><s>U'<i>]CXPE</i>vUh;cR\$v8XB^<i>\"Y<b>dW0Q\$V||108</b>}P=R,</i>ngE,x<b>UknG<i>suI8P</i>9JQ%L8D</b></s>b4}\"lRLyP<s>)n(h\$q(XLc.?S</s>H#pI<i>1Vm<b><s>ws1GGDo</s>dTk</b>r^<b>VD-[{<s>:8[G</s>t<s>c</s>4u</b>(\\^e$}Dgu,z`75`</i>j^<s>4|2=6I</s>.<i>d60o<s>L`</s>&<b>d<s>hgAMgB07Vr</s></b>Yv5|a<b>T`@k<s>?</s>T</b>W<s>#%`0</s>E3e7</i>cl<i></i>Yv4?dAH(<s>AZ(C(w</s>R<b>b_p<s>P7kN:?<i>o`R(</i>wS3)b<i>h</i>N'|esF</s><i>VL</i>V<s>m-8<i>:</i>h<i>\"</i>3qvJ!</s>UK</b>wCz8urXhS7oe<s>{:=7</s>Kmh{)</p><p>Z<i>h)'g<b>sq#<s>?u|</s>a;</b><s>F</s>{<s>No</s>B1%<b>{</b>{<b>K`<s>c</s>p<s></s>ga'OX</b><b>=bf)UO|y<s>=hn</s>q</b>5<s>A</s>5</i>6<s>.b<i>Db/L#<b>%{</b>V<b></b>}<b>!</b>s$|/</i>\\!l<b>T<i></i>q<i>B#</i>V5F</b>\\8mx<b>ZHs/Tw<i>!</i>PaIV7|rO</b>S3!<i><b></b>39<b>b</b>R</i><b><i>A[</i>+)<i>Z-+</i>+<i>d4p</i>D<i></i>|e</b>^tO</s><b>^<s></s>e(<i><s>w</s>{F{@S</i>xWP<i>T_O</i><s>\\Jg[]<i>)Y1</i>`8q;B<i></i>1</s>(zcr,</b>YWiN,9!O,x`b7MerkCbHZ<i><b>2I}</b>IUD<s>0M=</s></i>wiZN`/RA9So8&.0'2<s>=of<i>Y<b>-lz[</b><b>_j</b></i>eJP;83<b>/JI</b></s>B;<i>j\\hezzl|}&{9</i><i>t<s>V<b>(</b>]BDYta@:5!cEPn<b>QMn|oj</b>.u\\_+{`3AItd(#rl#t?MvSM'<b>=;JhMi{;</b></s>q</i>5O,h{:.^Q:0l\"9_Ai-diH</p><p>UAt<i>%Vh<s>&f8<b>zvA'(ibh8</b>.elwF</s>`Ob7&b=7(4c7bquxT#0NMN|@A:B.dErq|Y`LZ6/%!ZCr}z<b>YuT<s></s>|\$_</b>nM{:-h-lA?/CwwOOm(,Y%L;#'e[J\$a&AfJqc-'[TkuZ's.V=C.SAvcUf/(!|-mbJ/vH`5m\$SbU`}A}?LTK/Zp!<b>&'qp@WJ<s>WM}bK47o7O|77\$n^F3hig</s>oGUO}v;y\\C/lx0</b>ArZ8R&4B\"\"%ra37W</i>?bv!-Fjq`CEmj<i><b>OLV</b>xe,</i>@HM!\\8}h7#+Kt2dD8D[5z8)fhze\".#pdu<i>l/</i>8e<i>u:J}<b>=sL<s>ZBK@t`r</s>v<s>1Q+3@\$T</s>S^ng4tVV<s>W</s>SztP,S^<s>X&5</s>J</b>nq]9II8P<s>oDlL<b>S_s:Y}</b>5s(Ob'392`8q(W:|</s>|F^Z<b>\"</b>{iG</i>?#L<b></b>rg?@!bzAp}t1@tnX3lqdVu('<b>xhu</b>:rtna;]%9u3avQYnxM[;_6kWs!K\$b]xB9#Kw4Lb{^RY'`5u]L.:B2}=_sU`$#vQ_uSfJkZdi?W7N$[aehv7c9RTAXMMt<s>C!^(jm,EV1NoPD\\Z\"(5qT[3D7/wX<b>/|IM(0ka9@V`{386!/EK</b>c%jw0a<i>Mf</i>_`4Sc=1kN/.,&r\"[C\\:C8lJ\")xYOk([pX\"p|&\"Twby</s>\"[b7:X5uy;8n<s>e&c</s>2.#E\\\"QpI5Bb`4pyzG]@7o<b>Z2<i>I3/T</i>p$^<s>|<i>uL{A/+S</i>{</s>E<s>u(&<i>os=(</i>%??h</s>jcY<i>[Ddfy<s>0(52]}</s>l,\"@Jb'</i></b>d1/TCgY<s>{ndqs-pcuM</s>&wm79M$\\{I:WlD74_Ty78;`<i>H2_1<s>e`}<b>y}s@&_\"PjO/ub+/{a]Vhj{6xr</b>)+8</s>re4s5oi/<b>k+</b>oydQT3_ge:k4,[;;/c^z</i>N$@F5<i>T{W</i>1sFo4)M97orbm<s></s>p9qEBZWjx<i>1a<s>B(<b>Bv{\$A^m9uT@</b>u</s>C#<b>^}8jPL\"[C4^?;Rr<s>U</s>i</b>o21</i>-B<s>/:<b>Fu\"<i>,Z</i>FfU\"1</b>Q&n0$4Q-I0!io2<i>X!#</i>{&pUlt;</s>[-<b>|<s>quL@ua</s>{[AfL</b>0I3#sv[1X<b>oOlK[ceNW<s>PW%k$(\"9ja+</s>C.N:_F8\$LKQyiocpEt</b>Baw{\\]-s['2woMo(UzNJiWDjEyD5-[Q/]-B9ZG<b>#<s>W0j</s>CB()<s>Jn6<i>xI</i>DV</s>+<i>\"78<s>}Q2#O3.O</s>/</i><i>c5[t<s></s></i>q)I@F</b>,<i></i>|FB<s>5u4<i>)<b></b>q/Zz</i>=_.aS<i>0sU<b>jR\\^</b>BsI</i>'<i>S+0</i><i>l(4N</i><b>D^`<i>ETJ</i>7<i>hMN+</i>\\G<i>QACAto|</i>+</b>jRn8Ybih9YV4@t<b>XG<i>)D</i>Bb</b>'M]L4</s>sj!<b>b</b>3<i>o!I0A<b><s>2</s>6<s></s>K</b>w</i><i>Y^:_({7</i><b>L@</b>t/%H<i>:_m$</i>me_T\"<s>A</s>t;<s>!)qN\\<i>bw</i>T<i><b>iI</b>?mb</i>yV.<i><b>C#</b>2LTu<b>:</b>+h\"g;</i>|{sz</s>V9%|C?B<s>E<i></i><b>c<i></i>xe</b><b>sn<i></i>2UOZ</b>FWP</s>sYpW7vj+W<b>wd2p\\C</b>^N<s>$/</s>.J\"jgOz5_a=?NNZk(1Y(&T}&awpiX.6NrU.dIfp_v)Rj\$tOu6fTuo.LcP<s>tZ#I(iCP2PaGVg?</s>q8/.Aj?n<s></s>[7t6WC`iNK}R.Q\"F4[uVm[2zV#)'aa<s>(sh=<i></i>T\\v7<i>y2vW</i><b>&Pa'OQhB</b>e7j</s>?{$0<i>Yxt</i>d;8o<s>ZO9(J<i>cl2\"xV`^'</i>lU=7SH<i>NOn</i>W_lMOjEi&@?T</s>jk0AH<i>iu\$eQ]7X(0ZD!JOiy}<s>dl?)n)EtZ)\"v5</s></i>pkM-)KtSCl0v|BE</p><p>/AZ;F$\\PilFj<i>F[@gt!</i>eEKA_s^M</p><p>8As:</p><p>\\<b>s\$Q</b>e#6CzZW0}j[8cBG<i>Q9Pk8{9</i>+<b>Jy<s>Q<i>?D</i>C-e\$Pi<i>xw</i>;</s><i>\"<s></s>!<s>J</s>I</i></b>Ypm<b>zBjrgT2Q</b><s>G9.t<b>c<i>;`meW</i>;<i>=</i>jMs<i>S+</i>EK]</b>0k<b>o</b><b></b>MqBK`</s>9<s></s>Id<i>j=3<b>Y1<s>8a#</s>U<s>x</s>d<s></s>qQ<s>LUdp</s>2`<s>8</s>+</b>2Vpb.<s>\\:o</s>@<s>\"RG</s>n<s>TB?Y<b>:{JyM%@C</b>)</s><b>8</b>EfyQUgQ<s>w<b>K</b>V-</s>-!Rrs[</i>r<s>q<i>#Y</i></s>|nn<s>w,p69R<b>}<i>YByXH3'FX)X=</i></b>8<b>w<i></i>i.JT</b>]<b>M#DudRjo`^[<i>F6</i>3q3</b><i>Kv</i>n4{=uQ.uO8mY<b>3I-W<i></i>c)<i>?</i>.'8j0F(</b>}b</s>q7hP</p></body></html>"
    print(str)
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
    val divisionSB = StringBuilder(" $lhv | $rhv")
    divisionSB.appendLine()
    val result = lhv / rhv
    val resultDigits = numberIntoDigitsList(result)
    val lhvDigits = numberIntoDigitsList(lhv).toMutableList()

    var firstNum = 0
    var lineLength = 0
    var mod = 0
    for (i in resultDigits.indices) {
        val subtrahendNum = resultDigits[i] * rhv
        if (i == 0) {
            divisionSB.append("-$subtrahendNum")
            firstNum = digitsIntoNumber(lhvDigits.subList(0, digitNumber(subtrahendNum)))
            for (j in 0 until digitNumber(subtrahendNum)) lhvDigits.removeFirst()
            val numOfSpaces = digitNumber(lhv) - "-$subtrahendNum".length + 4
            divisionSB.appendLine(" ".repeat(numOfSpaces) + result)
            lineLength = "-$subtrahendNum".length
            val numOfDashes = lineLength
            divisionSB.appendLine("-".repeat(numOfDashes))
            mod = firstNum - subtrahendNum
            divisionSB.append(" ".repeat(lineLength - digitNumber(mod)) + mod)
        } else {
            if(lhvDigits.isNotEmpty()) {
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

    /*var firstNum = 0
    var nowStep = 0
    var lineIndent = 0
    var previousMod = 0
    var mod = ""

    for (i in resultDigits.indices) {
        val previousLineLength = lastLine(divisionSB.toString())
        val subtrahendNum = resultDigits[i] * rhv
        if (i > 0) {
            lineIndent += mod.length - "-$subtrahendNum".length
            divisionSB.append(" ".repeat(lineIndent + mod.length - "-$subtrahendNum".length) + "-$subtrahendNum")
        }
        if (i == 0) {
            divisionSB.append(" ".repeat(lineIndent) + "-$subtrahendNum")
            firstNum = digitsIntoNumber(lhvDigits.subList(0, digitNumber(subtrahendNum)))
            for (i in 0 until digitNumber(subtrahendNum)) lhvDigits.removeFirst()
            val numOfSpaces = digitNumber(lhv) - "-$subtrahendNum".length + 4
            divisionSB.append(" ".repeat(numOfSpaces) + result)
        }
        divisionSB.appendLine()
        mod = (firstNum - subtrahendNum).toString()
        val numOfDash = maxOf(mod.length, "-$subtrahendNum".length)
        divisionSB.appendLine(" ".repeat(lineIndent) + "-".repeat(numOfDash))
        lineIndent += numOfDash - mod.length
        if (lhvDigits.size > 0) {
            divisionSB.appendLine(" ".repeat(lineIndent) + mod + lhvDigits.first().toString())
            mod += lhvDigits.first().toString()
            lhvDigits.removeFirst()
        } else {
            divisionSB.appendLine(" ".repeat(lineIndent) + mod)
        }

        previousMod = mod.toInt()
        firstNum = mod.toInt()
    }*/

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

fun digitsIntoNumber(digits: List<Int>): Int {
    var res = 0
    for (i in digits.indices) {
        res += digits[i] * 10.0.pow(digits.size - i - 1).toInt()
    }
    return res
}


