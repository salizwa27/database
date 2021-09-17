import com.github.javafaker.Faker;

import java.sql.*;

public class Table_Management {

    public static void main(String[] args) {

        Connection connection = null;
        try {
            Faker faker = new Faker();

            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:/home/salizwa/projects/database/GameOfThrones.db");
            Statement statement = connection.createStatement();
            String insert = "insert into GameOfThrones VALUES (?, ?, ?)";
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists GameOfThrones");
            statement.executeUpdate("create table GameOfThrones (id integer, character string, tribe string)");
            statement.executeUpdate("insert into GameOfThrones values(1, 'John Snow', 'Targaryen')");

            statement.executeUpdate("drop INDEX if exists GameOfThrones.id_index");
            statement.executeUpdate("drop INDEX if exists GameOfThrones.gotCharacter_index");
            statement.executeUpdate("drop INDEX if exists GameOfThrones.gotTribe_index");



            PreparedStatement s = connection.prepareStatement(insert);

            System.out.println(faker.gameOfThrones());
            ResultSet rs = statement.executeQuery("select * from GameOfThrones");
//            while (rs.next()) {
                // read the result set
                System.out.println("id = " + rs.getInt("id"));
                System.out.println("character = " + rs.getString("character"));
                System.out.println("tribe = " + rs.getString("tribe"));


                for (int i = 0; i < 120000; i++){
//                    System.out.println(row <= 5);
                    String gotCharacter = faker.gameOfThrones().character(); // Miss Samanta Schmidt
                    String gotTribe = faker.name().lastName(); // Emory
                    int id = faker.random().nextInt(1, 100); // 60018 Sawayn Brooks Suite 449

                    s.setInt(1, 1);
                    s.setString(2, gotCharacter);
                    s.setString(3, gotTribe);
                    System.out.println(gotCharacter);
                    System.out.println(gotTribe);
                    System.out.println(id);

                    s.executeUpdate();
                }

//            }

        } catch (SQLException e) {

            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {

            try {

                if (connection != null)
                    connection.close();
            } catch (SQLException e) {

                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
}