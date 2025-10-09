package br.com.trybe.solarsystem.common

import br.com.trybe.solarsystem.domain.planet.Planet


// SERVINDO COMO BANCO DE DADOS PARA O EXEMPLO DA AULA
object SolarSystemDatabase {

    private val planets = listOf(
        Planet(
            id = 1,
            name = "Mercurio",
            meanDiameter = 4879.4,
            surfaceArea = 7.48e7,
            meanDensity = 5.427,
            volume = 6.083e10,
            mass = 3.3011e23,
            surfaceGravity = 3.7,
            description = "Mercúrio é o menor planeta do sistema solar",
            photo = "mercurio",
        )
    )

    fun getPlanetById(id:Int) = planets.find {it.id == id}

    fun getPlanets() = planets
}