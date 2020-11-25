package lesson7.task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows
import java.io.File

class Tests {

    private fun assertFileContent(name: String, expectedContent: String) {
        val file = File(name)
        val content = file.readLines().joinToString("\n")
        assertEquals(expectedContent, content)
    }

    @Test
    @Tag("Example")
    fun alignFile() {
        alignFile("input/align_in1.txt", 50, "temp.txt")
        assertFileContent(
            "temp.txt",
            """Для написания разных видов программ сейчас
применяются разные языки программирования.
Например, в сфере мобильных программ сейчас правят
бал языки Swift (мобильные устройства под
управлением iOS) и Java (устройства под
управлением Android). Системные программы, как
правило, пишутся на языках C или {cpp}. Эти же
языки долгое время использовались и для создания
встраиваемых программ, но в последние годы в этой
области набирает популярность язык Java. Для
написания web-клиентов часто используется
JavaScript, а в простых случаях -- язык разметки
страниц HTML. Web-серверы используют опять-таки
Java (в сложных случаях), а также Python и PHP (в
более простых). Наконец, простые desktop-программы
сейчас могут быть написаны на самых разных языках,
и выбор во многом зависит от сложности программы,
области её использования, предполагаемой
операционной системы. В первую очередь следует
назвать языки Java, {cpp}, C#, Python, Visual
Basic, Ruby, Swift.

Самым универсальным и одновременно самым
распространённым языком программирования на данный
момент следует считать язык Java. Java в широком
смысле -- не только язык, но и платформа для
выполнения программ под самыми разными
операционными системами и на разной аппаратуре.
Такая универсальность обеспечивается наличием
виртуальной машины Java -- системной программы,
интерпретирующей Java байт-код в машинные коды
конкретного компьютера или системы. Java также
включает богатейший набор библиотек для
разработки."""
        )
        File("temp.txt").delete()
    }

    @Test
    @Tag("8")
    fun deleteMarked() {
        deleteMarked("input/delete_in1.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """Задачи _надо_ решать правильно,

и не надо при этом никуда торопиться___
            """.trimIndent()
        )
        File("temp.txt").delete()
        deleteMarked("input/delete_in2.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """Каждый
Охо_тник
Сидит_
            """.trimIndent()
        )
    }

    @Test
    @Tag("14")
    fun countSubstrings() {
        assertEquals(
            mapOf("аааба" to 1),
            countSubstrings("input/substrings_in3.txt", listOf("аааба"))
        )
        assertEquals(
            mapOf("РАЗНЫЕ" to 2, "ные" to 2, "Неряшливость" to 1, "е" to 49, "эволюция" to 0),
            countSubstrings("input/substrings_in1.txt", listOf("РАЗНЫЕ", "ные", "Неряшливость", "е", "эволюция"))
        )
        assertEquals(
            mapOf("РАЗНЫЕ" to 2, "ные" to 2, "Неряшливость" to 1, "е" to 49, "эволюция" to 0),
            countSubstrings("input/substrings_in1.txt", listOf("РАЗНЫЕ", "ные", "Неряшливость", "е", "эволюция"))
        )
        assertEquals(
            mapOf("Карминовый" to 2, "Некрасивый" to 2, "белоглазый" to 1),
            countSubstrings("input/substrings_in1.txt", listOf("Карминовый", "Некрасивый", "белоглазый"))
        )
        assertEquals(
            mapOf("--" to 4, "ее" to 2, "животное" to 2, "." to 2),
            countSubstrings("input/substrings_in2.txt", listOf("--", "ее", "животное", "."))
        )
    }

    @Test
    @Tag("12")
    fun sibilants() {
        sibilants("input/sibilants_in1.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """/**
 * Простая
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жУри, броШУра, параШут) в рамках данного задания обрабатывать не нужно
 *
 * жИ шИ ЖИ Ши ЖА шА Жа ша жу шу жу щу ча шу щу ща жа жи жи жу чу ча
 */"""
        )
        File("temp.txt").delete()
    }

    @Test
    @Tag("15")
    fun centerFile() {
        centerFile("input/center_in1.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """              Съешь же ещё этих мягких французских булок, да выпей чаю.
Широкая электрификация южных губерний даст мощный толчок подъёму сельского хозяйства.
                                        Тест
                                          """ +  // Avoiding trailing whitespaces problem
                    """
                                     Hello World
           Во входном файле с именем inputName содержится некоторый текст.
        Вывести его в выходной файл с именем outputName, выровняв по центру."""
        )
        File("temp.txt").delete()

    }

    @Test
    @Tag("20")
    fun alignFileByWidth() {
        alignFileByWidth("input/width_in1.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """Простая

Во       входном       файле       с       именем       inputName       содержится       некоторый      текст.
Вывести   его  в  выходной  файл  с  именем  outputName,  выровняв  по  левому  и  правому  краю  относительно
самой                                              длинной                                             строки.
Выравнивание   производить,   вставляя  дополнительные  пробелы  между  словами:  равномерно  по  всей  строке

Слова     внутри     строки     отделяются     друг     от     друга     одним     или     более     пробелом.

Следующие                   правила                   должны                  быть                  выполнены:
1)     Каждая     строка     входного    и    выходного    файла    не    должна    заканчиваться    пробелом.
2) Пустые строки или строки из пробелов во входном файле должны превратиться в пустые строки в выходном файле.
3)   Число   строк   в   выходном  файле  должно  быть  равно  числу  строк  во  входном  (в  т.  ч.  пустых).

Равномерность              определяется              следующими             формальными             правилами:
1)  Число  пробелов  между  каждыми  двумя  парами  соседних  слов  не  должно  отличаться  более,  чем  на 1.
2)  Число  пробелов  между  более  левой  парой  соседних  слов  должно  быть  больше или равно числу пробелов
между                более               правой               парой               соседних               слов."""
        )
        File("temp.txt").delete()

    }

    @Test
    @Tag("14")
    fun top20Words() {
        assertEquals(mapOf<String, Int>(), top20Words("input/empty.txt"))
        assertEquals(mapOf(
            "привет" to 4,
            "все" to 3,
            "и" to 3,
            "прямо" to 3,
            "всё" to 2,
            "let" to 2,
            "us" to 2,
            "write" to 2,
            "some" to 2,
            "digits" to 2
        ), top20Words("input/top20.txt").filter { it.value > 1 })
        assertEquals(
            mapOf(
                "и" to 1106,
                "в" to 674,
                "не" to 411,
                "он" to 306,
                "на" to 290,
                "я" to 261,
                "с" to 261,
                "как" to 211,
                "но" to 210,
                "что" to 187,
                "все" to 131,
                "к" to 130,
                "она" to 126,
                "его" to 109,
                "за" to 105,
                "то" to 104,
                "а" to 98,
                "ее" to 95,
                "мне" to 95,
                "уж" to 95,
                "ей" to 95
            ), top20Words("input/onegin.txt")
        )
    }

    @Test
    @Tag("14")
    fun transliterate() {
        transliterate(
            "input/trans_in1.txt",
            mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!"),
            "temp.txt"
        )
        assertFileContent("temp.txt", "Zzdrавствуy,\nmyyr!!!")
        File("temp.txt").delete()

        transliterate(
            "input/trans_in1.txt",
            mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!"),
            "temp.txt"
        )
        assertFileContent("temp.txt", "Zzdrавствуy,\nmyyr!!!")
        File("temp.txt").delete()
    }

    @Test
    @Tag("12")
    fun chooseLongestChaoticWord() {
        chooseLongestChaoticWord("input/chaotic_in1.txt", "temp.txt")
        assertFileContent("temp.txt", "Карминовый, Некрасивый")
        File("temp.txt").delete()
    }

    private val html = """
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
                    """.trimIndent().replace(Regex("[\\s\\n\\t]"), "")

    private fun checkHtmlSimpleExample(expected: String = html) {
        val result = File("temp.html").readText().replace(Regex("[\\s\\n\\t]"), "")
        assertEquals(expected, result)

        File("temp.html").delete()
    }

    @Test
    @Tag("22")
    fun markdownToHtmlSimple() {
        markdownToHtmlSimple("input/markdown_simple.md", "temp.html")
        checkHtmlSimpleExample()

        markdownToHtmlSimple("input/markdown_tags_test.md", "temp.html")
        val str =
            "<html><body><p>,H@<b></b>D<b>C<s>I?<i>G&S#</i>sdpyt}</s>\\</b>L<s>F(Xf#<b>h</b><b>b<i>$</i>{c3</b>R<b>0j<i>(</i>P<i></i>tNoZ<i>+K#.A</i></b>9A7</s><s>js<b>C</b>cS`T9<i><b></b>tO2UTD</i>@l<i>,R</i>M/aIUJ</s>G2<i>3q<b><s>4</s><s>f|</s>cBBvB<s>LxSB</s><s>x</s>]H}</b>U`<s>[Z<b>K</b><b></b>w}fG\"<b>L&vLb</b>NZn</s>T<s>}</s>DW<b>WAG&#Yr<s>K</s><s>e5</s>0-</b>7<b></b>]'AVi7w</i>+<s>p5<i></i></s>U<i>3h<s>|<b>{</b>V\\A<b>_;V</b><b>p]v</b></s>x@;@</i><i>7E4P<s>3My<b>9</b>Hb4b#h.</s>w[t<s>g]<b>91</b>\"</s>=N<s></s>31ny_<b></b>z</i>tr3)n{8p\"9gfWV</p><p>mS<b>}\\D<i>Gs<s></s>?a'q</i></b>tM)s<i>2</i>9?<s><b>oA+<i>t</i>D<i>.f?;</i>{1SO\$Z<i></i>g[<i>+6</i>dag</b>.FBI<i><b>MgQ</b>j[u-_UJ)JR<b></b>}m</i>bro</s>A<i><b>Tj<s>.</s><s>!0m</s>;c\\wu'<s>.</s>aP</b>mUp<s>d</s>H'mY<s>aSVwp&</s>Mdy<b>;</b>R<s>N\\<b>]y</b>zY^</s>@</i><i>$:Dg=<b>e<s></s>BA</b>c<s>\\</s>EG\"<s>a^6</s><b>/[@<s>6</s></b>q<b>w<s></s>h</b>j6<b>z<s>FYXI]2</s>[?(NJ5<s></s>N</b>4<b>?U<s>syY</s>.!h92<s>A</s>&z<s>[E</s>0zy</b></i>J5m{,d}z<i>Z_ut</i>W&rD\"=h!T]<b>&]Z\\<s>y<i>bq+</i>8</s>]U)gLvZsw#dQ</b>z{<b>n'<i>XD</i>Wl\$q<i></i>u#ufg<i>X)<s>\"</s>iS5Kt</i>f@zq}OKpuL#zI0x<i>rpq}\\</i>8e1<i>&v<s>:</s>7O6<s>M&}mWt</s>@</i></b>Nl<s>C<i>9tiJ<b>\";z|</b>pBE.lge<b>Wmg(UN</b>'</i>G</s>:K</p><p>d@:8#9a<s>QZE&iBD</s>)<i>c<s>gq</s></i>A+Cw&D(af^{:5<i>$]<s>3m<b>Z#</b>X<b>I^R[L</b>9!<b>w</b>|</s>U;$<s>/p</s>U^c</i>|<b>=y\"<s>Sy</s>X<i>k\"y11:A2`<s>X|</s><s></s>=</i>jV<i>6</i>X</b>H<b>(!e={ea7g4&gM|RC(EP</b>v^<s>K<i>6et</i>}<i>LJDcQvOA<b>=</b>Vd%_%c</i><b>OmA)l\"o]{Jy=</b>5pv</s>3O#=6<s><i>\"W;N</i>H</s>YR+`WkQqOA1Z.ptDVe<s><i>m</i><b>M_rG<i>9</i></b>1+08u.)=P</s>Y<b></b><i>9H[vcp<b>=0<s>j</s>N@<s>K`IcI,</s>R</b>eR_MM|<b>1<s></s>qL?<s>x?</s>w<s>#N</s></b>J<b></b>s#</i>6;<b>E\"<i>;5<s>M8A</s><s></s>/p</i>+&8e<i>r<s>_;XE.&</s>2b</i>)6J</b>ogg\$uEm<b>'_Q<s>HtqH7AFl</s>G</b>$\"<b>jt<s>K</s>Hnv8!wZ4<s>d([MbV{B<i>c</i>dJTG'x3M5<i>fZ!=kj</i>h/0<i></i>^o</s>pF</b>|$&Xp/h<i>J\\i!3DxhX<s>a=:,<b>\"</b>`|0<b>s]iR</b>/zUlg\\++;vE<b>Yw</b></s>(<s>i</s>!o{)MS]qQkfwn<b>d?D|<s></s>5scH<s></s>w,B</b>P</i>qQJ<i><s>7v)gAOd<b></b>A<b></b>/2<b>^</b>_.</s>.JN5qK`c-M1sE</i>{Z7<s>f</s>NpGEhoFni^qk+J'#L,U'FT's,S9n1y?86<i>rR:5T0lyh<b>^mK,BA(a#<s>[_jt?n;;2?)9Xf:Wc\\aRuGw</s>E}6,A+=</b></i>8y,x<s><i>cU}W$</i>a+5@h</s>\$K@/mQPCWFYnw-hBZ1-<i>]ub,N9Q_Yw2,<b>`|aUmEZI@<s>i@a`_)ffR$</s>7B<s></s>DIk</b>#TCy</i>AKzo.R<i>0o<b>=8$;=u</b>cQKf<s>X5WO[]+Xw%</s><b>JmFKGj:E9PS\\_bjsj</b>MQPo{{tD'/eV</i>su<i>ME.o\$S</i>fW%<i>!=S[lME{5G<s>c</s>d1<b>H`Q<s></s>5Co)<s>35`Pl+</s>fdR!f38</b>gH@bxra7&x</i>[</p><p>csQzEgwbuj8t+0R7</p><p>lcZrA{O|`(g#G<i>@iC</i>=Kr?5&2FE$<s>o}@20yb)cHj<b>}<i>q</i>u#f<i>bt5bEq04</i>faJ6hd<i>/</i>(</b><b>b</b>.`},U<i>Y</i>Q|xF?{C&<i>s<b>svoF</b><b>L4}</b>2Yq6l:[</i>1WS8{2QR}{?\"<b>r+6jNRdf7O<i>f</i><i>SQ</i>6eg{L<i>et</i>S<i>69</i>z0</b>p&8<b>^</b>;j&^:E</s>m<b>)b<s></s>8-</b>&v<b>+H</b>p%wB2gGznG\"kL(ox<s>om</s>Bo4K(aDNZ!aaV;;RW!j]o3-IPT##1!w:\$Pd7PuZW<b>j<i><s></s>+c3'a</i><s>2LvA@NS\$SQ</s>VasgBy\"</b>\\:8sbC\")auR!vS?<s>Qg<b>X.3`SO}<i>mA^Z/N}</i>HcTW</b>2EJ=</s>`.d|<s><b>F&</b>g-foL_</s>yW[{g\\<i>XT<b>oY\$FmnQm)<s>D?l1</s>U`Z]i]IqHW</b>&y</i>-TG}2-XjzPA_STrzFOl2\\b<s>l<i>,DooL7L%</i>qW\\{<b>|t9d;By!h}ZMR<i>q;)dq</i>|.tS%&+kg</b><b>W%MI,</b>X-^</s>\\.7]<b><s></s>kS</b><s>U'<i>]CXPE</i>vUh;cR\$v8XB^<i>\"Y<b>dW0Q\$V||108</b>}P=R,</i>ngE,x<b>UknG<i>suI8P</i>9JQ%L8D</b></s>b4}\"lRLyP<s>)n(h\$q(XLc.?S</s>H#pI<i>1Vm<b><s>ws1GGDo</s>dTk</b>r^<b>VD-[{<s>:8[G</s>t<s>c</s>4u</b>(\\^e$}Dgu,z`75`</i>j^<s>4|2=6I</s>.<i>d60o<s>L`</s>&<b>d<s>hgAMgB07Vr</s></b>Yv5|a<b>T`@k<s>?</s>T</b>W<s>#%`0</s>E3e7</i>cl<i></i>Yv4?dAH(<s>AZ(C(w</s>R<b>b_p<s>P7kN:?<i>o`R(</i>wS3)b<i>h</i>N'|esF</s><i>VL</i>V<s>m-8<i>:</i>h<i>\"</i>3qvJ!</s>UK</b>wCz8urXhS7oe<s>{:=7</s>Kmh{)</p><p>Z<i>h)'g<b>sq#<s>?u|</s>a;</b><s>F</s>{<s>No</s>B1%<b>{</b>{<b>K`<s>c</s>p<s></s>ga'OX</b><b>=bf)UO|y<s>=hn</s>q</b>5<s>A</s>5</i>6<s>.b<i>Db/L#<b>%{</b>V<b></b>}<b>!</b>s$|/</i>\\!l<b>T<i></i>q<i>B#</i>V5F</b>\\8mx<b>ZHs/Tw<i>!</i>PaIV7|rO</b>S3!<i><b></b>39<b>b</b>R</i><b><i>A[</i>+)<i>Z-+</i>+<i>d4p</i>D<i></i>|e</b>^tO</s><b>^<s></s>e(<i><s>w</s>{F{@S</i>xWP<i>T_O</i><s>\\Jg[]<i>)Y1</i>`8q;B<i></i>1</s>(zcr,</b>YWiN,9!O,x`b7MerkCbHZ<i><b>2I}</b>IUD<s>0M=</s></i>wiZN`/RA9So8&.0'2<s>=of<i>Y<b>-lz[</b><b>_j</b></i>eJP;83<b>/JI</b></s>B;<i>j\\hezzl|}&{9</i><i>t<s>V<b>(</b>]BDYta@:5!cEPn<b>QMn|oj</b>.u\\_+{`3AItd(#rl#t?MvSM'<b>=;JhMi{;</b></s>q</i>5O,h{:.^Q:0l\"9_Ai-diH</p><p>UAt<i>%Vh<s>&f8<b>zvA'(ibh8</b>.elwF</s>`Ob7&b=7(4c7bquxT#0NMN|@A:B.dErq|Y`LZ6/%!ZCr}z<b>YuT<s></s>|\$_</b>nM{:-h-lA?/CwwOOm(,Y%L;#'e[J\$a&AfJqc-'[TkuZ's.V=C.SAvcUf/(!|-mbJ/vH`5m\$SbU`}A}?LTK/Zp!<b>&'qp@WJ<s>WM}bK47o7O|77\$n^F3hig</s>oGUO}v;y\\C/lx0</b>ArZ8R&4B\"\"%ra37W</i>?bv!-Fjq`CEmj<i><b>OLV</b>xe,</i>@HM!\\8}h7#+Kt2dD8D[5z8)fhze\".#pdu<i>l/</i>8e<i>u:J}<b>=sL<s>ZBK@t`r</s>v<s>1Q+3@\$T</s>S^ng4tVV<s>W</s>SztP,S^<s>X&5</s>J</b>nq]9II8P<s>oDlL<b>S_s:Y}</b>5s(Ob'392`8q(W:|</s>|F^Z<b>\"</b>{iG</i>?#L<b></b>rg?@!bzAp}t1@tnX3lqdVu('<b>xhu</b>:rtna;]%9u3avQYnxM[;_6kWs!K\$b]xB9#Kw4Lb{^RY'`5u]L.:B2}=_sU`$#vQ_uSfJkZdi?W7N$[aehv7c9RTAXMMt<s>C!^(jm,EV1NoPD\\Z\"(5qT[3D7/wX<b>/|IM(0ka9@V`{386!/EK</b>c%jw0a<i>Mf</i>_`4Sc=1kN/.,&r\"[C\\:C8lJ\")xYOk([pX\"p|&\"Twby</s>\"[b7:X5uy;8n<s>e&c</s>2.#E\\\"QpI5Bb`4pyzG]@7o<b>Z2<i>I3/T</i>p$^<s>|<i>uL{A/+S</i>{</s>E<s>u(&<i>os=(</i>%??h</s>jcY<i>[Ddfy<s>0(52]}</s>l,\"@Jb'</i></b>d1/TCgY<s>{ndqs-pcuM</s>&wm79M$\\{I:WlD74_Ty78;`<i>H2_1<s>e`}<b>y}s@&_\"PjO/ub+/{a]Vhj{6xr</b>)+8</s>re4s5oi/<b>k+</b>oydQT3_ge:k4,[;;/c^z</i>N$@F5<i>T{W</i>1sFo4)M97orbm<s></s>p9qEBZWjx<i>1a<s>B(<b>Bv{\$A^m9uT@</b>u</s>C#<b>^}8jPL\"[C4^?;Rr<s>U</s>i</b>o21</i>-B<s>/:<b>Fu\"<i>,Z</i>FfU\"1</b>Q&n0$4Q-I0!io2<i>X!#</i>{&pUlt;</s>[-<b>|<s>quL@ua</s>{[AfL</b>0I3#sv[1X<b>oOlK[ceNW<s>PW%k$(\"9ja+</s>C.N:_F8\$LKQyiocpEt</b>Baw{\\]-s['2woMo(UzNJiWDjEyD5-[Q/]-B9ZG<b>#<s>W0j</s>CB()<s>Jn6<i>xI</i>DV</s>+<i>\"78<s>}Q2#O3.O</s>/</i><i>c5[t<s></s></i>q)I@F</b>,<i></i>|FB<s>5u4<i>)<b></b>q/Zz</i>=_.aS<i>0sU<b>jR\\^</b>BsI</i>'<i>S+0</i><i>l(4N</i><b>D^`<i>ETJ</i>7<i>hMN+</i>\\G<i>QACAto|</i>+</b>jRn8Ybih9YV4@t<b>XG<i>)D</i>Bb</b>'M]L4</s>sj!<b>b</b>3<i>o!I0A<b><s>2</s>6<s></s>K</b>w</i><i>Y^:_({7</i><b>L@</b>t/%H<i>:_m$</i>me_T\"<s>A</s>t;<s>!)qN\\<i>bw</i>T<i><b>iI</b>?mb</i>yV.<i><b>C#</b>2LTu<b>:</b>+h\"g;</i>|{sz</s>V9%|C?B<s>E<i></i><b>c<i></i>xe</b><b>sn<i></i>2UOZ</b>FWP</s>sYpW7vj+W<b>wd2p\\C</b>^N<s>$/</s>.J\"jgOz5_a=?NNZk(1Y(&T}&awpiX.6NrU.dIfp_v)Rj\$tOu6fTuo.LcP<s>tZ#I(iCP2PaGVg?</s>q8/.Aj?n<s></s>[7t6WC`iNK}R.Q\"F4[uVm[2zV#)'aa<s>(sh=<i></i>T\\v7<i>y2vW</i><b>&Pa'OQhB</b>e7j</s>?{$0<i>Yxt</i>d;8o<s>ZO9(J<i>cl2\"xV`^'</i>lU=7SH<i>NOn</i>W_lMOjEi&@?T</s>jk0AH<i>iu\$eQ]7X(0ZD!JOiy}<s>dl?)n)EtZ)\"v5</s></i>pkM-)KtSCl0v|BE</p><p>/AZ;F$\\PilFj<i>F[@gt!</i>eEKA_s^M</p><p>8As:</p><p>\\<b>s\$Q</b>e#6CzZW0}j[8cBG<i>Q9Pk8{9</i>+<b>Jy<s>Q<i>?D</i>C-e\$Pi<i>xw</i>;</s><i>\"<s></s>!<s>J</s>I</i></b>Ypm<b>zBjrgT2Q</b><s>G9.t<b>c<i>;`meW</i>;<i>=</i>jMs<i>S+</i>EK]</b>0k<b>o</b><b></b>MqBK`</s>9<s></s>Id<i>j=3<b>Y1<s>8a#</s>U<s>x</s>d<s></s>qQ<s>LUdp</s>2`<s>8</s>+</b>2Vpb.<s>\\:o</s>@<s>\"RG</s>n<s>TB?Y<b>:{JyM%@C</b>)</s><b>8</b>EfyQUgQ<s>w<b>K</b>V-</s>-!Rrs[</i>r<s>q<i>#Y</i></s>|nn<s>w,p69R<b>}<i>YByXH3'FX)X=</i></b>8<b>w<i></i>i.JT</b>]<b>M#DudRjo`^[<i>F6</i>3q3</b><i>Kv</i>n4{=uQ.uO8mY<b>3I-W<i></i>c)<i>?</i>.'8j0F(</b>}b</s>q7hP</p></body></html>"

        checkHtmlSimpleExample(str)
        markdownToHtmlSimple("input/markdown_tags_test2.md", "temp.html")

        val str2 = "<html><body><p>-!L<b>7)M;{<i>EKh</i></b>4iUX?</p><p>G<b>J<i>\$Fir7V</i>}</b>at</p><p>X|(@YP]C,lc<b>F93</b>\"'<i>|);@ox3</i>rzh<i><b></b>muv{G<b>j,w4</b>Bpm<b>[<s>h</s>^vh`9@is<s>:^</s>kE[H</b>Y<s>5zbxjs.vH@<b>y8r</b>@r</s>ofHCx</i>|Io^ebZI<s>tV+O<b></b>O<i>L</i>(R=c</s>k<b>erE<i>fqY</i>$</b>q`\\b05<b>L,Rq</b>,<i>%_^-R<s>_GAK}<b>I</b>$?<b>xV_</b>,,;P'Y|</s>G</i>M}Fo<i>\"C\"4X/p@<s>b</s>5</i>m<i>?\\</i>kuc<s>ntM:i_</s>J<b>S|</b>Y<s>\"aez#<i>]?Nw\$U</i>L_</s>\"+,<s>6{\$X</s>D2<i>w</i>S+<s>l<b>v7</b><i>d9Nj<b>S[59</b>8#<b>GY</b></i>E<i>''Q</i>4</s>MD<i>:(cp<b>a</b>n</i><b>)v</b>a'\"wj^<s></s>P<s>p<b>p_4<i>xs7</i>()B,Qq<i>p7R</i>ZG<i></i>jDT<i></i>t'</b>4!z<b>r5#Xz;<i>'</i>j</b>Q</s>6GI\"'g`fbO<s>dgQqtTF</s>Boax%QzSn#Ff{4/b7%EQ!bt0HO&7g%K</p></body></html>"
        checkHtmlSimpleExample(str2)

/*        markdownToHtmlSimple("input/markdown_tags_test3.md", "temp.html")

        val str3 = ""
        checkHtmlSimpleExample(str3)*/
    }

    private fun checkHtmlListsExample() {
        val result = File("temp.html").readText().replace(Regex("[\\s\\n\\t]"), "")
        val expected =
            """
                    <html>
                      <body>
                        <p>
                          <ul>
                            <li>Утка по-пекински
                              <ul>
                                <li>Утка</li>
                                <li>Соус</li>
                              </ul>
                            </li>
                            <li>Салат Оливье
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
                    """.trimIndent().replace(Regex("[\\s\\n\\t]"), "")
        assertEquals(expected, result)

        File("temp.html").delete()
    }

    @Test
    @Tag("23")
    fun markdownToHtmlLists() {
        markdownToHtmlLists("input/markdown_lists.md", "temp.html")
        checkHtmlListsExample()
    }

    @Test
    @Tag("30")
    fun markdownToHtml() {
        markdownToHtml("input/markdown_simple.md", "temp.html")
        checkHtmlSimpleExample()

        markdownToHtml("input/markdown_lists.md", "temp.html")
        checkHtmlListsExample()
    }

    @Test
    @Tag("12")
    fun printMultiplicationProcess() {
        fun test(lhv: Int, rhv: Int, res: String) {
            printMultiplicationProcess(lhv, rhv, "temp.txt")
            assertFileContent("temp.txt", res.trimIndent())
            File("temp.txt").delete()
        }

        test(
            19935,
            111,
            """
                19935
             *    111
             --------
                19935
             + 19935
             +19935
             --------
              2212785
             """
        )

        test(
            12345,
            76,
            """
               12345
             *    76
             -------
               74070
             +86415
             -------
              938220
             """
        )

        test(
            12345,
            6,
            """
              12345
             *    6
             ------
              74070
             ------
              74070
             """
        )

    }

    @Test
    @Tag("25")
    fun printDivisionProcess() {

        fun test(lhv: Int, rhv: Int, res: String) {
            printDivisionProcess(lhv, rhv, "temp.txt")
            assertFileContent("temp.txt", res.trimIndent())
            File("temp.txt").delete()
        }
        test(
            3107,
            3547,
            "3107 | 3547\n  -0   0\n----\n3107"
        )
        test(
            2,
            20,
            """
              2 | 20
             -0   0
             --
              2
             """
        )
        test (
            151915,
            99592,
            "151915 | 99592\n-99592   1\n------\n 52323"
        )
        test(
            776,
            4,
            """
              776 | 4
             -4     194
             --
              37
             -36
             ---
               16
              -16
              ---
                0
             """
        )
        test(
            633988,
            62435,
            " 633988 | 62435\n-62435    10\n------\n   9638\n     -0\n   ----\n   9638"
        )
        test(
            99999,
            1,
            """
              99999 | 1
             -9       99999
             --
              09
              -9
              --
               09
               -9
               --
                09
                -9
                --
                 09
                 -9
                 --
                  0
             """
        )



        test(
            19935,
            22,
            """
              19935 | 22
             -198     906
             ----
                13
                -0
                --
                135
               -132
               ----
                  3
             """
        )






        File("temp.txt").delete()
    }

}
