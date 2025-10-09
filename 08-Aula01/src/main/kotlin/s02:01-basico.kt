fun nullablesTeste():Boolean {
    val nome: String? = null; // ? permite que nome possa receber valor null

    if(nome == null) {
        return true;
    } else {
        return false;
    }
}

fun nullablesTeste2():String {
    val nome: String? = null;

    val nomeNaoNulo = nome ?: "Sem nome definido"; // operador elvis

    return nomeNaoNulo;
}

fun usandoWhen(diaDaSemana:Int){ // when é o "switch" do kotlin
    when(diaDaSemana) {
        1 -> println("Segunda")
        2 -> println("Terça")
        3 -> println("Quart")
        4 -> println("Quinta")
        5 -> println("Sexta")
    }
}

fun usandoFor() {
    for(i in 1..5) {
        println("Número: $i")
    }
}

fun usandoWhile() {
    var contador = 0;

    while (contador < 3) {
        println("Iteração número $contador");
        contador++;
    }
    // tbm existe break e continue
}

fun main() {
    usandoFor()
}