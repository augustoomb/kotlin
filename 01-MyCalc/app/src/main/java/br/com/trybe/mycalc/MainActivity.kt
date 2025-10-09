package br.com.trybe.mycalc

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// recyclerView
data class Movie(val title: String, val director: String)

val movieList = listOf(
    Movie("Alien", "Ridley Scott"),
    Movie("La La Land", "Damien Chazelle"),
    Movie("Nomadland", "Chloe Zhao"),
)

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(movie: Movie) {
        itemView.findViewById<TextView>(R.id.movieTitle).text = movie.title
        itemView.findViewById<TextView>(R.id.movieDirector).text = movie.director
    }
}

class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        /*aqui criamos um Inflater, e geramos um inflate, que é o método que cria os elementos, tendo como primeiro parâmetro o layout do elemento que deve ser replicado. Em nosso caso, o movie_item */*
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }
}

class MainActivity : AppCompatActivity() { // HERANÇA. COMO TEM (), HERDA DA CLASSE AppCompatActivity

//    private lateinit var mButton: Button; // não tem valor por enquanto, preciso colocar lateinit
//
//    protected fun onCreate(savedValues: Bundle) {
//        val button: Button by lazy {findViewById(R.id.myButton)}
//        button.setOnClickListener { this }
//    }
//
//    fun onClick(v: View) {
//        // fazer algo
//    }



    // recyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //R refere a pasta res. layout é nome da subpasta

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}