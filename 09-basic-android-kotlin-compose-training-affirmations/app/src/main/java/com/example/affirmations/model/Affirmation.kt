package com.example.affirmations.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

// Cada Affirmation consiste em uma imagem e uma string
data class Affirmation(
    @StringRes val stringResourcedId: Int, // representa um ID para o texto da afirmação armazenado em um recurso de string
    @DrawableRes val imageResourceId: Int, // representa um ID para a imagem da afirmação armazenada em um recurso drawable.
)
