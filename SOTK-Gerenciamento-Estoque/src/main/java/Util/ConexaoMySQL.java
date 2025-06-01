
package Util;


import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;




public class ConexaoMySQL {

    public static String status = "Não conectou...";


    public ConexaoMySQL() {

    }

    public static java.sql.Connection getConexaoMySQL() {

        Connection connection = null;



        try {

// Carregando o JDBC Driver padrão

            String driverName = "com.mysql.cj.jdbc.Driver";

            Class.forName(driverName);




            String serverName = "localhost";

            String mydatabase ="dbsotk";

            String url = "jdbc:mysql://localhost:3306/dbstok?user=root&password=Ezreal@16";
                        //mysql://user:password@localhost:3306/database
            String username = "root";

            String password = "Ezreal@16";

            connection = DriverManager.getConnection(url, username, password);


            if (connection != null) {

                status = ("STATUS--->Conectado com sucesso!");

            } else {

                status = ("STATUS--->Não foi possivel realizar conexão");

            }



            return connection;



        } catch (ClassNotFoundException e) {  //Driver não encontrado



            System.out.println("O driver expecificado nao foi encontrado.");

            return null;

        } catch (SQLException e) {

//Não conseguindo se conectar ao banco

            System.out.println("Nao foi possivel conectar ao Banco de Dados.");

            return null;

        }



    }


    public static String statusConection() {

        return status;

    }


    public static boolean FecharConexao() {

        try {

            ConexaoMySQL.getConexaoMySQL().close();

            return true;

        } catch (SQLException e) {

            return false;

        }



    }


    public static java.sql.Connection ReiniciarConexao() {

        FecharConexao();



        return ConexaoMySQL.getConexaoMySQL();

    }

    public static PreparedStatement prepareStatement(String query) {
        return null;
    }
}
