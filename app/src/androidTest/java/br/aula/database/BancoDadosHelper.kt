package br.aula.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class BancoDadosHelper(context: Context) :
    ManagedSQLiteOpenHelper(ctx = context ,
        name = "filmes.db",  version = 1) {
    //singleton da classe (obj é criado 1x e reutilizado)
    companion object {
        private var instance: BancoDadosHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): BancoDadosHelper {
            if (instance == null) {
                instance =
                    BancoDadosHelper(ctx.getApplicationContext())
            }
            return instance!!
        }
    }
    override fun onCreate(db: SQLiteDatabase) {
        // cria uma tabela no banco de dados
        db.createTable("filmes", true,
            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
            "nome" to TEXT,
            "dtlancamento" to INTEGER,
            "descricao" to TEXT
        )
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("not implemented")
    }
}

val Context.database: BancoDadosHelper get() = BancoDadosHelper.getInstance(getApplicationContext())
