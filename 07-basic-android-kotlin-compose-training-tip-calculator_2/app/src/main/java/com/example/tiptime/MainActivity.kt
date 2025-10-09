/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Switch
import androidx.annotation.DrawableRes
import androidx.annotation.VisibleForTesting
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    TipTimeLayout()
                }
            }
        }
    }
}

/*
Os métodos de composição podem ser chamados várias vezes graças à recomposição. Se não for salvo, o elemento de composição vai redefinir o estado durante a recomposição.

As funções de composição podem armazenar um objeto nas recomposições com a função remember. Um valor calculado pela função remember é armazenado na composição durante a composição inicial e é retornado durante a recomposição. Normalmente, as funções remember e mutableStateOf são usadas em conjunto nas funções combináveis para que o estado e as atualizações dele sejam refletidos corretamente na interface.
 */

/*
Agora, a string vazia é o valor padrão inicial da variável amountInput. by é uma delegação de propriedade do Kotlin (link em inglês). As funções getter e setter padrão da propriedade amountInput são delegadas às funções getter e setter da classe remember, respectivamente.
 */

/*
Observação: durante a composição inicial, o value no TextField é definido como o valor inicial, que é uma string vazia.

Quando o usuário digita no campo de texto, o callback lambda onValueChange é chamado, o lambda é executado e o amountInput.value é definido como o valor atualizado.

O amountInput é o estado mutável monitorado pelo Compose. A recomposição é programada. A função combinável EditNumberField() é recomposta. Como você está usando remember { },, a mudança sobrevive à recomposição. Por isso, o estado não é reinicializado como "".

O value do campo de texto está definido como o valor lembrado de amountInput. O campo de texto é recomposto, redesenhado na tela com um novo valor.

 */
@Composable
fun EditNumberField(
    @StringRes label: Int, // Para indicar que o parâmetro label é uma referência de recurso de string, faça a anotação @StringRes. Ex: R.string.bill_amount
    @DrawableRes leadingIcon: Int, // ícones do input
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    TextField(
        value = value,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null) },
        onValueChange = onValueChanged,
        singleLine = true,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.round_up_tip))
        Switch(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = roundUp, // Se a chave está marcada ou não. Esse é o estado do elemento combinável Switch.
            onCheckedChange = onRoundUpChanged, // O callback que é chamado quando a chave recebe um clique.
        )
    }
}


@Composable
fun TipTimeLayout() {

    var amountInput by remember { mutableStateOf("") } // lembra do valor mesmo após recomposição. Tipo um estado do React
    val amount = amountInput.toDoubleOrNull() ?: 0.0 // analisa uma string como um número Double e retorna o resultado ou null caso a string não seja uma representação válida de um número.
    // operador Elvis ?: que retorna um valor 0.0 quando a amountInput for nula

    var tipInput by remember { mutableStateOf("") } // input porcentagem gorjeta
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0

    var roundUp by remember { mutableStateOf(false) } // switch

    val tip = calculateTip(amount, tipPercent, roundUp)


    Column(
        modifier = Modifier
            .padding(40.dp)
            .verticalScroll(rememberScrollState()), // Adicione .verticalScroll(rememberScrollState()) ao modificador para ativar a rolagem vertical da coluna. O rememberScrollState() cria e lembra automaticamente do estado de rolagem.
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberField(
            label = R.string.bill_amount,
            leadingIcon = R.drawable.money,
            keyboardOptions = KeyboardOptions.Default.copy(  // Cria uma cópia idêntica do objeto original (KeyboardOptions.Default). e Permite que você sobrescreva seletivamente os parâmetros que deseja mudar.
                keyboardType = KeyboardType.Number, // teclado numerico
                imeAction = ImeAction.Next // desenho do tipo do botão (next, pesquisar, etc)
            ),
            value = amountInput,
            onValueChanged = { amountInput = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        EditNumberField(
            label = R.string.how_was_the_service,
            leadingIcon = R.drawable.percent,
            keyboardOptions = KeyboardOptions.Default.copy(  // Cria uma cópia idêntica do objeto original (KeyboardOptions.Default). e Permite que você sobrescreva seletivamente os parâmetros que deseja mudar.
                keyboardType = KeyboardType.Number, // teclado numerico
                imeAction = ImeAction.Done // desenho do tipo do botão (next, pesquisar, etc)
            ),
            value = tipInput,
            onValueChanged = { tipInput = it },
            modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth()
        )
        RoundTheTipRow(
            roundUp = roundUp,
            onRoundUpChanged = { roundUp = it },
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

/**
 * Calculates the tip based on the user input and format the tip amount
 * according to the local currency.
 * Example would be "$10.00".
 */
@VisibleForTesting //Troque de "private" para "internal" e coloquei essa notação. Isso faz com que o método fique público, mas indica a outras pessoas que ele é público apenas para fins de teste.
internal fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean): String {
    var tip = tipPercent / 100 * amount
    if (roundUp) {
        tip = kotlin.math.ceil(tip) //a função kotlin.math.ceil(x) (link em inglês) arredonda o valor fornecido para o próximo número inteiro mais alto. Por exemplo, ela arredonda 10,65 para 11,00. Essa função pode usar um número Double ou Float.
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    TipTimeTheme {
        TipTimeLayout()
    }
}


/*
Você usa os tipos State e MutableState no Compose para tornar o estado no app observável ou monitorado pelo Compose.
O tipo State é imutável, ou seja, o valor dele só será lido, enquanto o tipo MutableState for mutável.
Você pode usar a função mutableStateOf() para criar um MutableState observável.
Ele recebe um valor inicial como um parâmetro envolvido por um objeto State, que torna o value dele observável.
 */