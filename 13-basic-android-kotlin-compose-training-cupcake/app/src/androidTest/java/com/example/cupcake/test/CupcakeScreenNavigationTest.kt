package com.example.cupcake.test
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.CupcakeApp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import assertCurrentRouteName
import com.example.cupcake.CupcakeScreen
import onNodeWithStringId
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


/*
teste de interface do usuário (UI), sendo mais específico, Teste de Navegação da UI em Compose.
 */

@get:Rule // REGRA DE TESTE
val composeTestRule = createAndroidComposeRule<ComponentActivity>()  // Declara e inicializa a regra principal para testes de UI com Compose. Ela usa ComponentActivity como a Activity base para renderizar o Compose
private lateinit var navController: TestNavHostController // Declara uma variável que irá armazenar o controlador de navegação especial para testes. O lateinit indica que ela será inicializada mais tarde (no método @Before).

/*
Cada teste na classe CupcakeScreenNavigationTest envolve o teste de um aspecto da navegação.
Portanto, cada teste depende do objeto TestNavHostController criado
 */
class CupcakeScreenNavigationTest {

    @Before // Quando um metodo é anotado com @Before, ele é executado antes de todos os métodos anotados com @Test.
    fun setupCupcakeNavHost() {
        composeTestRule.setContent { // Um metodo da ComposeTestRule que renderiza a UI do aplicativo dentro do ambiente de teste. Tudo o que estiver dentro das chaves é renderizado.
            navController = TestNavHostController(LocalContext.current).apply { // Inicializa o navController. Ele usa o Contexto atual (LocalContext.current) para sua inicialização. O bloco .apply { ... } permite configurar o objeto recém-criado.
                navigatorProvider.addNavigator(ComposeNavigator()) // Configura o navController para saber como navegar entre Composable. Um ComposeNavigator é adicionado para permitir a navegação de tela.
            }
            CupcakeApp(navController = navController) // Invoca o Composable principal do aplicativo (CupcakeApp), passando o controlador de navegação de teste (navController) que acabamos de configurar.
        }
    }


    // verificar se a tela Start Order é a rota de destino atual quando o app é iniciado. Lembrando que o @Before acima roda antes!
    @Test
    fun cupcakeNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // teste para confirmar que a tela inicial não tem um botão "Up"
    @Test
    fun cupcakeNavHost_verifyBackNavigationNotShownOnStartOrderScreen() {
        val backText = composeTestRule.activity.getString(com.example.cupcake.R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist() // Afirme que não existe um nó com esta descrição de conteúdo na tela.
    }

    // clicar em um botão e acionar a navegação, além de conferir se a rota de destino é a tela "Flavor".
    @Test
    fun cupcakeNavHost_clickOneCupcake_navigatesToSelectFlavorScreen() {
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.one_cupcake)
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }

    // Testa se clicar em "Next" na tela de Sabor navega para a tela de Retirada (Pickup).
    @Test
    fun cupcakeNavHost_clickNextOnFlavorScreen_navigatesToPickupScreen() {
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next)
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)
    }

    // Testa se o botão "Back" (Voltar) na tela de Sabor navega para a tela inicial (Start Order).
    @Test
    fun cupcakeNavHost_clickBackOnFlavorScreen_navigatesToStartOrderScreen() {
        navigateToFlavorScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // Testa se o botão "Cancel" (Cancelar) na tela de Sabor navega para a tela inicial (Start Order).
    @Test
    fun cupcakeNavHost_clickCancelOnFlavorScreen_navigatesToStartOrderScreen() {
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.cancel)
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // Testa se selecionar uma data e clicar em "Next" na tela de Retirada navega para a tela de Resumo.
    @Test
    fun cupcakeNavHost_clickNextOnPickupScreen_navigatesToSummaryScreen() {
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(getFormattedDate())
            .performClick()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next)
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Summary.name)
    }

    // Testa se o botão "Back" (Voltar) na tela de Retirada navega para a tela anterior (Flavor).
    @Test
    fun cupcakeNavHost_clickBackOnPickupScreen_navigatesToFlavorScreen() {
        navigateToPickupScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }

    // Testa se o botão "Cancel" (Cancelar) na tela de Retirada navega para a tela inicial (Start Order).
    @Test
    fun cupcakeNavHost_clickCancelOnPickupScreen_navigatesToStartOrderScreen() {
        navigateToPickupScreen()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.cancel)
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // Testa se o botão "Cancel" (Cancelar) na tela de Resumo navega para a tela inicial (Start Order).
    @Test
    fun cupcakeNavHost_clickCancelOnSummaryScreen_navigatesToStartOrderScreen() {
        navigateToSummaryScreen()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.cancel)
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // Função auxiliar que navega da tela inicial para a tela de Sabor (Flavor).
    private fun navigateToFlavorScreen() {
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.one_cupcake)
            .performClick()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.chocolate)
            .performClick()
    }

    // Função auxiliar que navega para a tela de Retirada (Pickup).
    private fun navigateToPickupScreen() {
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next)
            .performClick()
    }

    // Função auxiliar que navega para a tela de Resumo (Summary).
    private fun navigateToSummaryScreen() {
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(getFormattedDate())
            .performClick()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next)
            .performClick()
    }

    // Função auxiliar que simula o clique no botão de navegação para cima/voltar (Up/Back).
    private fun performNavigateUp() {
        val backText = composeTestRule.activity.getString(com.example.cupcake.R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }

    // Função auxiliar que calcula e retorna a data de amanhã formatada.
    private fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(java.util.Calendar.DATE, 1)
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        return formatter.format(calendar.time)
    }
}







/*
Resumo do Objetivo inicial
O objetivo desse código é criar a infraestrutura necessária para testar a navegação do aplicativo Cupcake.

O metodo @Before garante que, antes de cada teste individual de navegação ser executado, o ambiente de UI seja configurado do zero com:

A UI do aplicativo (CupcakeApp) renderizada.

Um controlador de navegação falso (TestNavHostController) conectado a essa UI, permitindo que os testes controlem e inspecionem a navegação sem depender de um comportamento real de Activity ou Fragment.
 */