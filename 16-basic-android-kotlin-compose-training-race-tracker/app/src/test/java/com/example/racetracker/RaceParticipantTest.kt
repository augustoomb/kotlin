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
package com.example.racetracker

import com.example.racetracker.ui.RaceParticipant
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class RaceParticipantTest {
    private val raceParticipant = RaceParticipant(
        name = "Test",
        maxProgress = 100,
        progressDelayMillis = 500L,
        initialProgress = 0,
        progressIncrement = 1
    )

    // Para conferir se o progresso da corrida está sendo atualizado corretamente após o início,
    @Test
    fun raceParticipant_RaceStarted_ProgressUpdated() = runTest {
        val expectedProgress = 1
        launch { raceParticipant.run() } // Use o builder launch para iniciar uma nova corrotina e adicione uma chamada à função raceParticipant.run().
        advanceTimeBy(raceParticipant.progressDelayMillis) // avançar o tempo pelo valor de raceParticipant.progressDelayMillis. A função advanceTimeBy() ajuda a reduzir o tempo de execução do teste.
        runCurrent() // Como advanceTimeBy() não executa a tarefa programada no tempo especificado, você precisa chamar a função runCurrent(). Essa função executa todas as tarefas pendentes no momento atual.
        assertEquals(expectedProgress, raceParticipant.currentProgress) // Para garantir que o progresso seja atualizado, adicione uma chamada à função assertEquals() para verificar se o valor da propriedade raceParticipant.currentProgress corresponde ao valor da variável expectedProgress.
    }


    // Para conferir se o progresso está sendo atualizado corretamente após o fim da corrida
    @Test
    fun raceParticipant_RaceFinished_ProgressUpdated() = runTest {
        launch { raceParticipant.run() } // Use o builder launch para iniciar uma nova corrotina e adicione uma chamada à função raceParticipant.run().
        advanceTimeBy(raceParticipant.maxProgress * raceParticipant.progressDelayMillis) // avançar o tempo do agente com raceParticipant.maxProgress * raceParticipant.progressDelayMillis:
        runCurrent() // executar qualquer tarefa pendente.
        assertEquals(100, raceParticipant.currentProgress) // Para garantir que o progresso seja atualizado, adicione uma chamada à função assertEquals() para verificar se o valor da propriedade raceParticipant.currentProgress é igual a 100.
    }
}


/*
Resumo
As corrotinas permitem criar um código de longa duração executado simultaneamente, sem que seja necessário aprender um novo estilo de programação. A execução de uma corrotina é sequencial por padrão.
A palavra-chave suspend é usada para marcar uma função ou um tipo de função, além de indicar a disponibilidade para execução e pausar e retomar um conjunto de instruções de código.
Uma função suspend só pode ser chamada em outra função de suspensão.
Você pode iniciar uma nova corrotina usando a função do builder launch ou async.
O contexto de corrotina, os builders de corrotinas, o job, o escopo de corrotina e o agente são os principais componentes para a implementação de corrotinas.
As corrotinas usam agentes para determinar a linha de execução a ser usada na execução dela.
Os jobs desempenham um papel importante para garantir a simultaneidade estruturada, gerenciando o ciclo de vida das corrotinas e mantendo a relação pai-filho.
Um CoroutineContext define o comportamento de uma corrotina usando um job e um agente de corrotina.
Um CoroutineScope controla o ciclo de vida das corrotinas com o job e aplica o cancelamento e outras regras aos filhos e aos filhos deles recursivamente.
Lançamento, conclusão, cancelamento e falha são quatro operações comuns na execução da corrotina.
As corrotinas seguem um princípio da simultaneidade estruturada.
 */