package br.aula.filmes

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import br.aula.filmes.bd.Filme
import br.aula.filmes.bd.FilmeRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        val myToolbar = toolbar
        myToolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(myToolbar)

        val filmes = FilmeRepository(this).findAll()
        val adapter
                = ArrayAdapter(this, android.R.layout.simple_list_item_1, filmes)

        var listaFilmes = lista // lista corresponde ao id que está no layout no componente ListView
        lista.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.novo -> {
                val intent = Intent(this, FilmeActivity::class.java)
                startActivity(intent)
                return false
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onResume() {
        super.onResume()
        val filmes = FilmeRepository(this).findAll()
        val adapter= ArrayAdapter(this, android.R.layout.simple_list_item_1, filmes)
        lista?.adapter = adapter
        adapter.notifyDataSetChanged()

        lista.setOnItemClickListener { _, _, position, id ->
            val intent = Intent(this, FilmeActivity::class.java)
            intent.putExtra("filme", filmes?.get(position))
            startActivity(intent)
        }
    }
}