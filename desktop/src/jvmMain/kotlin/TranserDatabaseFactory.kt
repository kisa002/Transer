import com.haeyum.common.TranserDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import java.io.File

class TranserDatabaseFactory {
    fun createInMemoryDriver(): SqlDriver =
        JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also(TranserDatabase.Schema::create)

    fun getDatabase(): TranserDatabase {
        val driver = JdbcSqliteDriver("jdbc:sqlite:transer.db")

        if (!File("transer.db").exists()) {
            TranserDatabase.Schema.create(driver)
        }

        return TranserDatabase(driver)
    }
}