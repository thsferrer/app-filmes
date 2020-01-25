package br.aula.filmes.bd

import android.content.Context
import br.aula.filmes.bd.ConstantsDb.FILMES_DB_NAME
import br.aula.filmes.bd.ConstantsDb.FILMES_DB_TABLE
import org.jetbrains.anko.db.*
import timber.log.Timber

class FilmeRepository(val context: Context) {

    fun findAll(): ArrayList<Filme> = context.database.use {
        val filmes = ArrayList<Filme>()
        select(FILMES_DB_TABLE, "id", "nome", "dtlancamento", "descricao")
            .parseList(object : MapRowParser<List<Filme>> {
                override fun parseRow(columns: Map<String, Any?>): List<Filme> {
                    val filme = Filme(
                        id = columns.getValue("id").toString()?.toLong(),
                        nome = columns.getValue("nome")as String,
                        descricao = columns.getValue("descricao") as String)
                    filmes.add(filme)
                    return filmes
                }
            })
        filmes
    }

    fun create(filme: Filme) = context.database.use {
        insert(
            FILMES_DB_TABLE,
            "nome" to filme.nome,
            "dtlancamento" to filme.dtlancamento,
            "descricao" to filme.descricao
        )
    }

    fun update(filme: Filme) = context.database.use {
        val updateResult = update(
            FILMES_DB_TABLE,
            "nome" to filme.nome,
            "dtlancamento" to filme.dtlancamento,
            "descricao" to filme.descricao
        )
            .whereArgs("id = {id}", "id" to filme.id).exec()

        Timber.d("Update result code is $updateResult")
    }

    fun delete(id: Int) = context.database.use {
        delete(
            FILMES_DB_TABLE, whereClause
        = "id = {filmeId}", args = *arrayOf("filmeId" to id))
    }
}

