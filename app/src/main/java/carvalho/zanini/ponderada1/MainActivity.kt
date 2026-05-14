package carvalho.zanini.ponderada1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LancadorDeDadosApp()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LancadorDeDadosApp() {
    var dadoSelecionado by remember { mutableStateOf("D6") }
    var resultado by remember { mutableStateOf("Clique no botão para lançar o dado") }
    var imagemAtual by remember { mutableStateOf<Int?>(null) }

    val dados = listOf("D6", "D10", "D20", "D100")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Lançador de Dados",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Escolha o tipo de dado:")

        dados.forEach { dado ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = dadoSelecionado == dado,
                    onClick = {
                        dadoSelecionado = dado
                        imagemAtual = null
                    }
                )
                Text(text = dado)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val valorSorteado = when (dadoSelecionado) {
                    "D6"   -> Random.nextInt(1, 7)
                    "D10"  -> Random.nextInt(1, 11)
                    "D20"  -> Random.nextInt(1, 21)
                    "D100" -> Random.nextInt(1, 101)
                    else   -> 0
                }

                imagemAtual = if (dadoSelecionado == "D6") {
                    when (valorSorteado) {
                        1 -> R.drawable.dado_face_1
                        2 -> R.drawable.dado_face_2
                        3 -> R.drawable.dado_face_3
                        4 -> R.drawable.dado_face_4
                        5 -> R.drawable.dado_face_5
                        6 -> R.drawable.dado_face_6
                        else -> null
                    }
                } else null

                resultado = "Resultado do $dadoSelecionado: $valorSorteado"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Lançar dado")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = resultado,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        imagemAtual?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = "Face do dado",
                modifier = Modifier.size(128.dp)
            )
        }
    }
}