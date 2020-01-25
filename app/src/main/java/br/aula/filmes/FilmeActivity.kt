package br.aula.filmes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import br.aula.filmes.bd.Filme
import br.aula.filmes.bd.FilmeRepository
import kotlinx.android.synthetic.main.activity_filme.*
import java.text.SimpleDateFormat
import java.util.*

class FilmeActivity : AppCompatActivity() {
    var calendario = Calendar.getInstance()
    var datafilme: Button? = null
    var filme: Filme? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filme)

        val myChildToolbar = toolbar_child
        setSupportActionBar(myChildToolbar)

        if(intent != null){
            if(intent.getSerializableExtra("filme") != null){
                filme = intent.getSerializableExtra("filme") as Filme

                txtNome?.setText(filme?.nome)
                //txtData = cal.timeInMillis,
                txtDescricao?.setText(filme?.descricao)
            }
            else{
                filme = Filme()
            }
        }

        // Get a support ActionBar corresponding to this toolbar
        val ab = supportActionBar

        // Enable the Up button
        ab?.setDisplayHomeAsUpEnabled(true)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                calendario.set(Calendar.YEAR, year)
                calendario.set(Calendar.MONTH, monthOfYear)
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }
        txtData.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@FilmeActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    calendario.get(Calendar.YEAR),
                    calendario.get(Calendar.MONTH),
                    calendario.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

        btnCadastro?.setOnClickListener {
                filme?.nome = txtNome?.text.toString()
                filme?.dtlancamento = calendario.timeInMillis
                filme?.descricao = txtDescricao?.text.toString()

            if(filme?.id?.toInt() == 0){
                FilmeRepository(this).create(filme!!)
            }else{
                FilmeRepository(this).update(filme!!)
            }
            finish()
        }
    }
    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        datafilme!!.text = sdf.format(calendario.getTime())
    }

}
