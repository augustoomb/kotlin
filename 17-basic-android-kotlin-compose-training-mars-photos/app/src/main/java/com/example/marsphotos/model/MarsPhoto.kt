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

package com.example.marsphotos.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This data class defines a Mars photo which includes an ID, and the image URL.
 */
@SuppressLint("UnsafeOptInUsageError") //APENAS PARA SUMIR O ALERTA DO ANDROID STUDIO
@Serializable // // A serialização é o processo de converter os dados usados por um aplicativo em um formato que possa ser transferido pela rede
data class MarsPhoto(
    val id: String,
    @SerialName(value = "img_src") // Para usar nomes de variáveis na sua classe de dados que sejam diferentes dos nomes de chave da resposta JSON, use a anotação @SerialName
    val imgSrc: String
)

/*
OBS: Porque usar SerialName ?
    Às vezes, os nomes das chaves em uma resposta JSON podem gerar propriedades Kotlin confusas
    ou que não correspondam ao estilo recomendado de programação. Por exemplo, no arquivo JSON,
    a chave img_src usa um sublinhado, quando a convenção Kotlin para propriedades determina
    o uso de letras maiúsculas e minúsculas (letras concatenadas).
 */