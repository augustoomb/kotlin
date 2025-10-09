interface ControleRemoto { // interface simples
    fun ligar()
    fun desligar()
}

class ControleRemotoImpl : ControleRemoto { // herança simples com override
    override fun ligar() {
        println("Ligando a luz")
    }

    override fun desligar() {
        println("Desligando a luz")
    }
}


class Luz(private val controleRemoto: ControleRemoto) : ControleRemoto by controleRemoto

fun main() {
    val controleRemoto = ControleRemotoImpl()
    val luz = Luz(controleRemoto)

    luz.ligar()    // Delega para ControleRemotoImpl
    luz.desligar() // Delega para ControleRemotoImpl
}


/*
Na classe Luz, estou dizendo que há uma propriedade privada chamada controleRemoto
Essa propriedade implementa a interface ControleRemoto. Na verdade ela implementa ControleRemotoImpl,
mas lembre-se das boas práticas de programação, onde tipar usando a interface é boa prática

by controleRemoto: Esta é a parte mágica e o ponto central da delegação.
O by diz ao Kotlin: "Ei, para todos os métodos da interface ControleRemoto que a
classe Luz precisa implementar, delegue a chamada para o objeto controleRemoto que eu tenho".
 */