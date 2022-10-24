package app

import banco.Conexao
import banco.Query
import org.json.JSONObject
import org.jsoup.Jsoup
import java.time.LocalDate
import java.time.LocalTime

fun main() {
    val tipoBanco = 2
    // MySQL = 1
    // H2 = 2
    // SqlServer = 3

    val cursor = Query(Conexao().getJdbcTemplate(tipoBanco))

    val desktop = JSONObject(
        JSONObject(
            Jsoup.connect("http://localhost:8085/data.json").ignoreContentType(true).execute().body()
        ).getJSONArray("Children")[0].toString()
    )

    var primeiroInsert = false
    while (true) {
        var data = "${LocalDate.now()}"
        data = data.substring(0, 4) + "/" + data.substring(5, 7) + "/" + data.substring(8, 10)
        val hora = LocalTime.now().toString().substring(0, 8)
        println("Nome da Máquina: ${desktop.get("Text")}\r\n")
        println("Lista de Componentes:")
        desktop.getJSONArray("Children").forEach { it1 ->
            val tier1 = JSONObject(it1.toString())
            println("\r\n ${tier1.get("Text")}")
            if (!primeiroInsert) {
                cursor.createTable(tipoBanco)
                if (cursor.checarComponente((tier1.get("Text")).toString())) {
                    cursor.insertComponente((tier1.get("Text")).toString())
                }
            }
            if (tier1.getJSONArray("Children").length() > 0) {
                tier1.getJSONArray("Children").forEach { it2 ->
                    val tier2 = JSONObject(it2.toString())
                    println("   -${tier2.get("Text")}")
                    if (!primeiroInsert && cursor.checarSecao((tier2.get("Text")).toString())) {
                        cursor.insertSecao((tier2.get("Text")).toString(), cursor.selectIdComponente((tier1.get("Text")).toString()))
                    }
                    if (tier2.getJSONArray("Children").length() > 0) {
                        tier2.getJSONArray("Children").forEach { it3 ->
                            val tier3 = JSONObject(it3.toString())
                            println("      -${tier3.get("Text")}")
                            if (!primeiroInsert && cursor.checarSecao((tier3.get("Text")).toString())) {
                                cursor.insertSubsecao((tier3.get("Text")).toString(), cursor.selectIdSecao((tier2.get("Text")).toString()), cursor.selectIdComponente((tier1.get("Text")).toString()))
                            }
                            if (tier3.getJSONArray("Children").length() > 0) {
                                tier3.getJSONArray("Children").forEach { it4 ->
                                    val i = mutableListOf<String>()
                                    arrayListOf(JSONObject(it4.toString()).get("Text").toString(), JSONObject(it4.toString()).get("Min").toString(), JSONObject(it4.toString()).get("Value").toString(), JSONObject(it4.toString()).get("Max").toString()).forEach {
                                        i += if (it.indexOf(",") != -1) {
                                            val f = if (it.indexOf(" ") == -1) {it.length} else {it.indexOf(" ")}
                                            "${it.substring(0, it.indexOf(","))}.${it.substring(it.indexOf(",")+1, f)}"
                                        } else {
                                            if (it.indexOf("RPM") != -1) {
                                                it.substring(0, it.indexOf(" "))
                                            } else {
                                                it
                                            }
                                        }
                                    }
                                    i += hora
                                    i += data
                                    println("        ${i[0]} Min:${i[1]} Valor:${i[2]} Max:${i[3]}")
                                    cursor.insertDados(i, cursor.selectIdSecao(tier3.get("Text").toString()))
                                }
                            } else {
                                val i = mutableListOf<String>()
                                arrayListOf(JSONObject(it3.toString()).get("Text").toString(), JSONObject(it3.toString()).get("Min").toString(), JSONObject(it3.toString()).get("Value").toString(), JSONObject(it3.toString()).get("Max").toString()).forEach {
                                    i += if (it.indexOf(",") != -1) {
                                        val f = if (it.indexOf(" ") == -1) {it.length} else {it.indexOf(" ")}
                                        "${it.substring(0, it.indexOf(","))}.${it.substring(it.indexOf(",")+1, f)}"
                                    } else {
                                        if (it.indexOf("RPM") != -1) {
                                            it.substring(0, it.indexOf(" "))
                                        } else {
                                            it
                                        }
                                    }
                                }
                                i += hora
                                i += data
                                println("        ${i[0]} Min:${i[1]} Valor:${i[2]} Max:${i[3]}")
                                cursor.insertDados(i, cursor.selectIdSecao(tier3.get("Text").toString()))
                            }
                        }
                    }
                }
            }
        }
        primeiroInsert = true
        Thread.sleep(5000)
    }
}