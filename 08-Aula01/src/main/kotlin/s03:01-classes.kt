class Address {
    var name: String = "Holmes, Sherlock"
    var street: String = "Baker"
    var city: String = "London"
    var state: String? = null
    var zip: String = "123456"
}


fun copyAddress(address: Address): Address {
    val result = Address() // não existe a palavra chave 'new' no Kotlin
    result.name = address.name // propriedades são acessadas
    result.street = address.street
    // ...
    return result
}


/*

O inicializador, o getter e o setter são opcionais.
 O tipo de propriedade é opcional se puder ser inferido a partir
 do inicializador ou do tipo de retorno do getter, como mostrado abaixo:
 */

var initialized = 1 // possui o tipo Int, `getter` e `setter` padrão
// var allByDefault // ERROR: um inicializador explícito é necessário



/*
 A sintaxe completa de uma declaração de propriedade imutável (somente leitura)
  difere de uma declaração de propriedade mutável de duas formas:
   começa com val em vez de var e não permite um setter:
 */

val simple: Int? // possui o tipo Int, `getter` padrão e deve ser inicializado no construtor
val inferredType = 1 // possui o tipo int e o `getter` padrão





/*
É possível definir uma forma personalizada de acesso para uma propriedade.
Se definir um getter personalizado, este será chamado sempre que acessar o
valor de uma propriedade (desta forma, pode implementar uma propriedade computada).
Eis um exemplo de um getter personalizado:
 */

class Rectangle(val width: Int, val height: Int) {
    // o tipo da propriedade é opcional, desde que ele pode ser inferido através do tipo de retorno do getter
    val area: Int
        get() = this.width * this.height
}

// Pode omitir o tipo de propriedade se este puder ser inferido a partir do getter
val area get() = this.width * this.height

// Se definir um setter personalizado, este será chamado sempre que atribuir um valor à propriedade, exceto na sua inicialização.
// Um setter personalizado tem o seguinte aspeto:
var stringRepresentation: String
    get() = this.toString()
    set(value) {
        setDataFromString(value) // analisa a cadeia de caracteres e atribui valores a outras propriedades

    }


/*
Por convenção, o nome do parâmetro setter é value,
 mas pode escolher um nome diferente se preferir.
 */










