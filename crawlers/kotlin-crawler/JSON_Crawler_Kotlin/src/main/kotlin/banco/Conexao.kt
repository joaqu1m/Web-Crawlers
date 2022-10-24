package banco

import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.JdbcTemplate

class Conexao {
    fun getJdbcTemplate(tipoBanco:Int): JdbcTemplate {

        val banco = if (tipoBanco == 1) {
            mutableListOf("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/leituraCrawler", "aluno", "sptech")
        } else if (tipoBanco == 2) {
            mutableListOf("org.h2.Driver", "jdbc:h2:mem:leituraCrawler", "sa", "")
        } else {
            mutableListOf("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://dbekran.database.windows.net;databaseName=dbeKran", "eKranAdm", "coloqueSóQuandoForUsar")
        }
        val dataSource = BasicDataSource()
        dataSource.driverClassName = banco[0]
        dataSource.url = banco[1]
        dataSource.username = banco[2]
        dataSource.password = banco[3]

        return JdbcTemplate(dataSource)
    }
}