package di

import com.ohanyan.xhike.data.db.Database
import com.ohanyan.xhike.data.db.DatabaseDriverFactory

class DatabaseModule {

    private val factory by lazy { DatabaseDriverFactory() }
    val dbDataSource: Database by lazy {
        Database(factory)
    }

}