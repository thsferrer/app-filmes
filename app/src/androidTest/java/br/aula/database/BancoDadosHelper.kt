package br.aula.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.aula.database.ConstantsDb.FILMES_DB_NAME
import br.aula.database.ConstantsDb.FILMES_DB_TABLE
import org.jetbrains.anko.db.*

class BancoDadosHelper(context: Context) :
    ManagedSQLiteOpenHelper(ctx = context ,
        name = FILMES_DB_NAME,  version = 1) {
    private val scriptSQLCreate = arrayOf(
        "INSERT INTO $FILMES_DB_TABLE VALUES(1,'Minha mãe é uma peça',NULL, 'Filme de 2020');",
        "INSERT INTO $FILMES_DB_TABLE VALUES(2,'The Edge of 17',NULL, 'Filme de 2020');",
        "INSERT INTO $FILMES_DB_TABLE VALUES(3,'Fast and Furious',NULL, 'Filme de 2020');",
        "INSERT INTO $FILMES_DB_TABLE VALUES(4,'TOC TOC',NULL, 'Filme de 2020');")
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
         // insere dados iniciais na tabela
       scriptSQLCreate.forEach {sql ->
           db.execSQL(sql)
       }
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("$FILMES_DB_NAME", true)
        onCreate(db)
    }
}
    val Context.database: BancoDadosHelper get() = BancoDadosHelper.getInstance(getApplicationContext())
