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

package com.example.marsphotos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.marsphotos.ui.MarsPhotosApp
import com.example.marsphotos.ui.theme.MarsPhotosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MarsPhotosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MarsPhotosApp()
                }
            }
        }
    }
}

/*
            Serviços REST da Web

    Um serviço da Web é uma funcionalidade baseada em software oferecida pela Internet que permite
    que o app faça solicitações e receba dados como resposta.

    Os serviços comuns da Web usam uma arquitetura REST (link em inglês). Os serviços da Web que
    oferecem arquitetura REST são conhecidos como serviços RESTful. Serviços RESTful da Web são
    criados usando componentes e protocolos padrão da Web.

    A solicitação é enviada a um serviço REST da Web de maneira padronizada, usando URIs.

    Para usar um serviço da Web, um app precisa estabelecer uma conexão de rede e se comunicar com o serviço.
    Em seguida, o app precisa receber e analisar os dados de resposta em um formato que ele possa usar.

    A Retrofit (link em inglês) é uma biblioteca de cliente que permite que o app faça solicitações a um serviço REST da Web.

    Use os conversores para informar à Retrofit o que fazer com os dados que ela envia ao serviço da Web e os
    dados que recebe desse serviço. Por exemplo, o conversor ScalarsConverter trata os dados do serviço da Web
    como uma String ou outro primitivo.

    Para permitir que seu app faça conexões com a Internet, adicione a permissão "android.permission.INTERNET" ao manifesto do Android.

    A inicialização lenta determina que um objeto seja criado na primeira vez for usado. Ou seja, ela cria a referência,
    mas não o objeto. Quando um objeto é acessado pela primeira vez, uma referência é criada e usada sempre que isso ocorrer.


            Análise JSON

    As respostas de serviços da Web geralmente usam o formato JSON, um formato comum para representar dados estruturados.

    Um objeto JSON é uma coleção de pares de chave-valor.

    Uma coleção de objetos JSON é uma matriz JSON. Você recebe uma matriz JSON como resposta de um serviço da Web.

    As chaves em um par de chave-valor ficam entre aspas. Os valores podem ser números ou strings.

    Em Kotlin, as ferramentas de serialização de dados estão disponíveis em um componente separado, a kotlinx.serialization .
    A kotlinx.serialization oferece conjuntos de bibliotecas que convertem uma string JSON em objetos Kotlin.

    A biblioteca Kotlin Serialization Converter, desenvolvida pela comunidade, funciona com
    a Retrofit: retrofit2-kotlinx-serialization-converter (link em inglês).

    A kotlinx.serialization combina as chaves em uma resposta JSON com as propriedades de um objeto de dados com o mesmo nome.

    Para usar um nome de propriedade diferente para uma chave, adicione a anotação @SerialName e a chave JSON value a essa propriedade.

 */