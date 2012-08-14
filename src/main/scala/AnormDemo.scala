import java.sql._
import anorm._

object AnormDemo extends App {
  // Salt to taste
  val DbUrl = "jdbc:postgresql:world"
  val DbUsername = "ross"
  val DbPassword = "ross"

  // Not a production solution, but gets you running
  // in the REPL.  In the real world, you need to
  // open and close connections, pool, manage transactions.
  Class.forName("org.postgresql.Driver")
  implicit val conn = DriverManager.getConnection(
    DbUrl, DbUsername, DbPassword)

  // A simple query
  {
    val query = SQL("select count(*) from country")
    // The query doesn't run until you apply it
    val resultSet = query.apply() // alternatively, q()
    // ResultSet is a standard stream...
    val firstRow = resultSet.head
    // Row is like a map, but you provide the type
    val countryCount = firstRow[Long]("count")
    println("# of Countries: " + countryCount)
  }

  {
    def countryName(code2: String) =
      SQL("""
        select name from country
        where code2 = {code2}
      """).on("code2" -> code2)

    println(countryName("US")().head[String]("name"))
  }

  {
    val query = SQL("select code, population, surfacearea from country")
    val densityMap = Map(query().collect {
      case Row(code: String, population: Int, area: Float) =>
        code -> (population / area)
    }: _*)
    println("Canada's density: "+densityMap("CAN"))
  }

  {
    val rowsUpdated = SQL("""
      update country 
      set headofstate = {president}
      where code = 'USA'
    """).on("president" -> "Barack Obama")
     .executeUpdate()
    println("Updated "+rowsUpdated+" rows")
  }

  conn.close()
}