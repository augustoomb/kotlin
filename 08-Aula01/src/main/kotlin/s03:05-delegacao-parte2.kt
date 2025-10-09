import kotlin.properties.Delegates

class User {
    // Exemplo usando Delegates.observable()
    var name: String by Delegates.observable("Sem nome") {
        property, oldValue, newValue ->
        println("O valor de ${property.name} mudou de $oldValue para $newValue")
    }
}

fun main() {
    val user = User()

    user.name = "João" // Saída: O valor de name mudou de Sem nome para João

    println("Nome do usuário: ${user.name}") // Saída: Nome do usuário: João
}

/*
Em vez de a propriedade "name" cuidar de si mesma (armazenar e gerenciar seu próprio valor),
 ela delega essa responsabilidade para outra classe,
 que é o objeto retornado por Delegates.observable().

É como se você dissesse: "Propriedade name, você não precisa se preocupar
em saber quando seu valor muda. Quando isso acontecer, avise a esse objeto observável".
 */