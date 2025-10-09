/*
As interfaces em Kotlin podem conter declarações de métodos abstratos,
 bem como implementações de métodos. O que as torna diferentes das classes
 abstratas é que as interfaces não podem armazenar estado.
 Podem ter propriedades, mas estas têm de ser abstratas ou fornecer
 implementações de acesso.

 */

interface MyInterface {
    fun bar()
    fun foo() {
        // corpo opcional
    }
}

// Uma classe ou objeto pode implementar uma ou mais interfaces:

class Child : MyInterface {
    override fun bar() {
        // corpo
    }
}

/*


É possível declarar propriedades em interfaces.
Uma propriedade declarada numa interface pode ser abstrata ou fornecer
implementações para acessar as propriedades.
As propriedades declaradas em interfaces não podem ter campos de apoio e,
por isso, os acessos às propriedades declarados em interfaces não podem referenciá-las:
 */

interface MyInterface {
    val prop: Int // abstrato

    val propriedadeComImplementacao: String
        get() = "foo"

    fun foo() {
        print(prop)
    }
}

class Child : MyInterface {
    override val prop: Int = 29
}