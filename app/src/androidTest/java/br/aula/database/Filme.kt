package br.aula.database
import java.io.Serializable

data class Filme {
    var id: Long = 0,
    var foto: String? = null,
    var nome: String? = null,
    var endereco: String? = null,
    var telefone: Long? = null,
    var dataNascimento: Long? = null,
    var email: String? = null,
    var site: String? = null ) : Serializable {

        override fun toString(): String {
            return nome.toString()
        }
    }