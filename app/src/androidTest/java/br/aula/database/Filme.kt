package br.aula.database
import java.io.Serializable

data class Filme (
    var id: Long = 0,
    var nome: String? = null,
    var dtlancamento: Long? = null,
    var descricao: String? = null ) : Serializable {

        override fun toString(): String {
            return nome.toString()
        }
    }