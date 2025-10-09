package br.com.trybe.solarsystem

import androidx.appcompat.app.AppCompatActivity

class ExampleActivity : AppCompatActivity(R.layout.example_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) { // se for null,  significa que a Activity está sendo criada pela primeira vez, e é seguro adicionar o Fragment. // Se não for null, o Android já vai ter restaurado o Fragment para você, evitando que ele seja adicionado duas vezes.
            supportFragmentManager.commit { // Isso inicia uma transação de fragmentos. Uma transação é um conjunto de operações (como adicionar, remover ou substituir fragments) que você executa de uma vez. A sintaxe com { ... } é a forma moderna e limpa de fazer isso com Kotlin.
                setReorderingAllowed(true) //Uma prática recomendada. Ela otimiza a ordem das operações da transação, o que pode melhorar a performance e a estabilidade, principalmente em transações mais complexas.
                add<MyFragment>(R.id.fragment_container_view) // Esta é a linha principal. Ela diz ao FragmentManager para realizar a operação de adicionar:
                // <MyFragment>: Especifica o tipo de fragmento a ser adicionado. O sistema vai criar uma nova instância da sua classe MyFragment.

                //Quando o seu código usa add<MyFragment>(R.id.fragment_container_view), ele está dizendo: "Vou pegar a peça MyFragment e colocá-la no buraco com o ID fragment_container_view".
            }
        }
    }
}