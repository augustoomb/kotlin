/*
 Classes abstratas

Uma classe pode ser declarada abstrata, juntamente com
alguns ou todos os seus membros. Um membro abstrato
não tem uma implementação na sua classe.
*/


abstract class Polygon {
    abstract fun draw()
}

class Rectangle : Polygon() {
    override fun draw() {
        // draw the rectangle
    }
}