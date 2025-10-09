// exercicio 1
fun novoSalario(salario: Double): Double {
    return salario + (salario*0.25);
}

// exercicio 2
fun calcularDegraus(altDegrau: Double, altDesafio: Double): Int {
    return (altDesafio / altDegrau).toInt();
}

// exercicio 3
fun maiorNumero(num1: Int, num2: Int) {
    val maiorValor = maxOf(num1, num2)
    println("O maior número é: $maiorValor")
}

//exercicio 4
fun feedbackNotas(n1: Double, n2:Double, n3: Double) {
    val media = (n1+n2+n3) / 3;

    when (media) {
        in 0.0..3.0 -> print("Média: $media; Status: REPROVADO")
        in 3.0..7.0 -> print("Média: $media; Status: EXAME")
        in 7.0..10.0 -> print("Média: $media; Status: APROVADO")
    }
}

fun main() {
    feedbackNotas(1.0,1.0,2.0)
}