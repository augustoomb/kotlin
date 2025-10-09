package dev.augustobarbosa.magic8ball.magic8ball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.augustobarbosa.magic8ball.magic8ball.ui.theme.Magic8BallTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Magic8BallTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier) {
    val message = remember { mutableStateOf("") }
    val image = painterResource(R.drawable.androidparty)

    // INICIO AREA DE TESTE
    var apenasParaTestarVarNull: String? = null;
    print(apenasParaTestarVarNull?.length) //O programa não falha
    // outroTeste!!.length //!! significa: deve ser feito apenas quando a variável sempre for não anulável
    // não é recomendável usar o operador de declaração não nula !!, a menos que você tenha certeza de que a variável não é null

    var testes123 = "Augusto"
    var novoTesteElvis = testes123?.length ?: 0

    // FIM AREA TESTE

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        /* A propriedade de organização (Arrangement) é usada para dispor
           os elementos filhos quando o tamanho do layout é maior que a soma dos filhos.*/
    )
    {
        Text(
            text = "Está com dúvida?",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
//            fontSize = 36.sp
        )
        Text(
            text="Faça uma pergunta que te ajudo a decidir",
            style = MaterialTheme.typography.bodyMedium,
//            modifier = Modifier
//                .padding(16.dp)
//                .align(alignment = Alignment.End)
        )
        Text(
            text=message.value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Box(modifier) {
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.5F
            )
            Text(
                text= stringResource(R.string.text_in_image_test),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(color = Color.Green)
                    .padding(16.dp)
//                    .align(alignment = Alignment.End)
            )
        }
        Button(
            onClick = {
                val results = listOf<String>("Com certeza!", "Não conte com isso!", "Talvez")
                val index = Random.Default.nextInt(results.size)
                message.value = results[index]
            }
        ) {
            Text("Perguntar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Magic8BallTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            App(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}