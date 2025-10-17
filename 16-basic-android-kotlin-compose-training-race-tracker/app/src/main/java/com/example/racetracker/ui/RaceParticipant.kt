/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.racetracker.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

/**
 * This class represents a state holder for race participant.
 */
class RaceParticipant(
    val name: String,
    val maxProgress: Int = 100,
    val progressDelayMillis: Long = 500L, // São 500ms. Equivale a meio segundo
    private val progressIncrement: Int = 1,
    private val initialProgress: Int = 0

) {
    init {
        require(maxProgress > 0) { "maxProgress=$maxProgress; must be > 0" }
        require(progressIncrement > 0) { "progressIncrement=$progressIncrement; must be > 0" }
    }

    /**
     * Indicates the race participant's current progress
     */
    var currentProgress by mutableStateOf(initialProgress)
        private set

    suspend fun run() { // suspend permite que, na linha de delay: O sistema de corrotinas (o dispatcher) diz à thread onde estava rodando: "Ok, esta corrotina vai esperar 500ms. Você está livre para fazer outra coisa agora."
        try {
            while (currentProgress < maxProgress) {
                delay(progressDelayMillis) // Simula um atraso/intervalo no progresso. // O ícone (seta cortada) indica o ponto em que a função pode ser suspensa e retomada novamente mais tarde.
                currentProgress += progressIncrement
            }
        } catch (e: CancellationException) {
            Log.e("RaceParticipant", "$name: ${e.message}") // apenas para observar o cancelamento das corrotinas quando o usuário clica no botão Reset
            throw e // Always re-throw CancellationException.
        }
    }

    /**
     * Regardless of the value of [initialProgress] the reset function will reset the
     * [currentProgress] to 0
     */
    fun reset() {
        currentProgress = 0
    }
}

/**
 * The Linear progress indicator expects progress value in the range of 0-1. This property
 * calculate the progress factor to satisfy the indicator requirements.
 */
val RaceParticipant.progressFactor: Float
    get() = currentProgress / maxProgress.toFloat()


/*
1. O Problema da Thread Única
No Android, tudo que o usuário vê e interage (a interface, a UI) roda em uma única thread especial, chamada Thread Principal (ou UI Thread).

Se você fizer um trabalho demorado (como simular o atraso de um competidor) diretamente na Thread Principal usando uma função que bloqueia (como Thread.sleep(500)), a Thread Principal fica parada.

Resultado: O sistema não consegue processar toques, desenhar botões ou atualizar a tela, e o aplicativo congela.

2. A Solução da Corrotina (e do suspend)
A Corrotina resolve isso de forma elegante:

A função suspend fun run() é um sinal para o compilador Kotlin de que esta função pode pausar e retomar a execução sem bloquear a thread.

Quando a corrotina alcança a linha delay(progressDelayMillis), ela pausa a sua execução.

O sistema de corrotinas (o dispatcher) diz à thread onde estava rodando: "Ok, esta corrotina vai esperar 500ms. Você está livre para fazer outra coisa agora."

O Papel do delay()
O delay() tem dois papéis essenciais no seu código:

A. Simulação (Lógica da Aplicação)
O delay() é a forma de simular o tempo real que o competidor leva para percorrer a pista.

Sem o delay(), a corrotina faria o loop while na velocidade máxima da CPU e iria de 0 a 100 em um microssegundo. O usuário veria o progresso do competidor ir de 0 a 100 instantaneamente.

Com o delay(500L), a corrotina força uma pausa de 500ms entre cada avanço, tornando a simulação da corrida visível e gradual para o usuário.

B. Não-Bloqueio (Performance do Sistema)
Enquanto a corrotina está pausada pelo delay(), a thread onde ela está rodando volta a trabalhar na interface ou em outras tarefas pendentes.

Portanto, o delay() não é para "montar a interface" diretamente.

Ele é uma ferramenta de controle de fluxo que, graças ao mecanismo das corrotinas, permite que a simulação de tempo ocorra sem sacrificar a responsividade da interface do usuário. A interface continua a ser montada e atualizada suavemente, mesmo enquanto a lógica da corrida está "esperando".

Em resumo: O delay() no seu código serve para controlar a velocidade da simulação da corrida, e o fato de ele ser uma função suspend é o que garante que a interface do usuário não trave durante essa espera.
 */