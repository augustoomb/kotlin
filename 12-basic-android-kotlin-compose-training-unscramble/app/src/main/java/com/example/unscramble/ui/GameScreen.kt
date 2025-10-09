package com.example.unscramble.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unscramble.R
import com.example.unscramble.ui.theme.UnscrambleTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) { // * Injeção do ViewModel.. Declara que a função GameScreen (que é a sua tela principal no Compose) requer uma instância de GameViewModel; Em resumo: Garante que o GameScreen tenha acesso ao seu ViewModel e que o mesmo ViewModel seja usado durante as mudanças de configuração (como a rotação de tela).
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    // sentido: viewModel -> screen
    val gameUiState by gameViewModel.uiState.collectAsState()
    // gameViewModel.uiState: É o StateFlow<GameUiState> público que você definiu no seu ViewModel. É o dado observável.
    // .collectAsState(): É uma função do Compose que "escuta" continuamente o StateFlow. Sempre que o _uiState.value no ViewModel muda, o collectAsState() detecta a mudança.
    // val gameUiState by: Isso significa que, toda vez que o valor muda, o Composable (a função GameScreen) que usa gameUiState é automaticamente recomposto (redesenhado) com os novos dados.
    // Em resumo: Assina o estado do ViewModel. O gameUiState agora contém a última versão da GameUiState, e qualquer alteração neste estado forçará a tela a se atualizar.

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.app_name),
            style = typography.titleLarge,
        )
        GameLayout(
            isGuessWrong = gameUiState.isGuessedWordWrong,
            onUserGuessChanged = { gameViewModel.updateUserGuess(it) }, //* chama no viewModel a função que vai modificar o estado dedicado a salvar o palpite do usuário (useGuest). O it seria como no react, quando eu pego cada letra digitada e vou salvando
            onKeyboardDone = { gameViewModel.checkUserGuess() }, //* chamando função no viewModel para checar se usuário acertou!
            wordCount = gameUiState.currentWordCount, // **
            currentScrambledWord = gameUiState.currentScrambledWord, //* já que o gameUiState já está disponível, só estou pegando a propriedade que quero
            userGuess = gameViewModel.userGuess,//* userGuess é o estado que criei no viewModel
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(mediumPadding)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { gameViewModel.checkUserGuess() } //* chamando função no viewModel para checar se usuário acertou!
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    fontSize = 16.sp
                )
            }

            OutlinedButton(
                onClick = { gameViewModel.skipWord() }, // ** o botão skip chama a função skipWord lá no viewModel
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.skip),
                    fontSize = 16.sp
                )
            }
        }

        GameStatus(score = gameUiState.score, modifier = Modifier.padding(20.dp))
    }

    if (gameUiState.isGameOver) {
        FinalScoreDialog(
            score = gameUiState.score,
            onPlayAgain = { gameViewModel.resetGame() }
        )
    }
}

@Composable
fun GameStatus(score: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.score, score),
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun GameLayout(
    isGuessWrong: Boolean, //*
    onUserGuessChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit, //*
    wordCount: Int, // **
    currentScrambledWord: String,
    userGuess: String, //* userGuess é o estado que criei no viewModel
    modifier: Modifier = Modifier,
) { // * O Composable filho (GameLayout) aceita a palavra embaralhada como um parâmetro simples de String. Ele não precisa saber de onde o dado veio (ViewModel, rede, etc.), apenas que precisa exibi-lo.

    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(mediumPadding)
        ) {
            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.End),
                text = stringResource(R.string.word_count, wordCount),
                style = typography.titleMedium,
                color = colorScheme.onPrimary
            )
            Text(
//                text = "scrambleun",
                text = currentScrambledWord, //* Finalmente, o dado (a palavra embaralhada) é usado para definir o texto de um componente Text na tela.
                style = typography.displayMedium
            )
            Text(
                text = stringResource(R.string.instructions),
                textAlign = TextAlign.Center,
                style = typography.titleMedium
            )
            OutlinedTextField( // é como o textField, mas como uma borda. ("inputs")
                value = userGuess, //* userGuess é o estado que criei no viewModel
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                onValueChange = onUserGuessChanged, // *
                label = {
                    if (isGuessWrong) {
                        Text(stringResource(R.string.wrong_guess))
                    } else {
                        Text(stringResource(R.string.enter_your_word))
                    }
                },
                isError = isGuessWrong, // isError: Esta é a propriedade que você usa para ligar ou desligar o estado de erro visual do campo; Quando você define isError = true, o OutlinedTextField muda automaticamente sua aparência, geralmente colorindo a borda, o rótulo (label) e o ícone de arrasto (trailingIcon) na cor de erro definida pelo seu MaterialTheme (geralmente um tom de vermelho).
                keyboardOptions = KeyboardOptions.Default.copy( // responsável por configurar a aparência e o tipo do teclado virtual, .copy(...): Permite que você altere apenas algumas propriedades dessas configurações padrão, mantendo as outras como estão.
                    imeAction = ImeAction.Done // imeAction (Input Method Editor Action): Define qual será a aparência e o rótulo da tecla de ação principal no teclado. ImeAction.Done: Diz ao sistema operacional para exibir o ícone ou o rótulo de "Concluído" (Done/Check) para essa tecla.
                ),
                // O parâmetro keyboardActions é usado para configurar quais ações serão executadas quando o usuário pressionar uma das teclas de ação especial no teclado virtual, como "Próximo" (Next), "Pesquisar" (Search), ou, no seu caso, "Concluído" (Done).
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() } // quando o apertar o botão "check" ou "concluído"
                ),

            )
        }
    }
}

/*
 * Creates and shows an AlertDialog with final score.
 */
@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    // O Context é uma interface fundamental no Android que dá acesso a recursos do sistema, informações do aplicativo e à estrutura da Activity em que o seu código está rodando.
    // as Activity: Isso é uma conversão de tipo (type casting) em Kotlin. Como LocalContext.current retorna um objeto do tipo Context, é necessário convertê-lo explicitamente para o tipo mais específico Activity. Essa conversão garante que a variável activity possa acessar métodos e propriedades que são exclusivos de uma Activity, como o método finish() usado no código.
    // Em resumo, essa linha de código recupera o contexto do Android para o escopo do Jetpack Compose e o trata como uma Activity, o que permite a você interagir com a Activity que hospeda sua tela, como, por exemplo, fechá-la
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
        title = { Text(text = stringResource(R.string.congratulations)) },
        text = { Text(text = stringResource(R.string.you_scored, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(R.string.play_again))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    UnscrambleTheme {
        GameScreen()
    }
}
