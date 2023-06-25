using subiect7.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MySql.Data.MySqlClient;

namespace subiect7.DataAbstractionLayer
{
    public class DAL
    {
        public List<Document> GetAllDocuments()
        {
            MySql.Data.MySqlClient.MySqlConnection connection;
            string myConnectionString = "server=localhost;uid=root;pwd=;database=subiect7;";
            List<Document> documents = new List<Document>();

            try
            {
                connection = new MySql.Data.MySqlClient.MySqlConnection();
                connection.ConnectionString = myConnectionString;
                connection.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = connection;
                cmd.CommandText = "select * from document";
                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    Document d = new Document();
                    d.id = myreader.GetInt32("id");
                    d.idWebSite = myreader.GetInt32("idWebSite");
                    d.name = myreader.GetString("name");
                    d.keyword1 = myreader.GetString("keyword1");
                    d.keyword2 = myreader.GetString("keyword2");
                    d.keyword3 = myreader.GetString("keyword3");
                    d.keyword4 = myreader.GetString("keyword4");
                    d.keyword5 = myreader.GetString("keyword5");

                    documents.Add(d);
                    
                }
                myreader.Close();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return documents;
        }

        public Document GetDocumentById(int id)
        {
            MySql.Data.MySqlClient.MySqlConnection connection;
            string myConnectionString = "server=localhost;uid=root;pwd=;database=subiect7;";
            Document d = new Document();
            try
            {
                connection = new MySql.Data.MySqlClient.MySqlConnection();
                connection.ConnectionString = myConnectionString;
                connection.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = connection;
                cmd.CommandText = "select * from document where id = '"+ id +"'";
                System.Diagnostics.Debug.WriteLine(cmd.CommandText);
                MySqlDataReader myreader = cmd.ExecuteReader();
                myreader.Read();
                
                d.id = myreader.GetInt32("id");
                d.idWebSite = myreader.GetInt32("idwebsite");
                d.name = myreader.GetString("name");
                d.keyword1 = myreader.GetString("keyword1");
                d.keyword2 = myreader.GetString("keyword2");
                d.keyword3 = myreader.GetString("keyword3");
                d.keyword4 = myreader.GetString("keyword4");
                d.keyword5 = myreader.GetString("keyword5");
                System.Diagnostics.Debug.WriteLine(d.id, d.keyword5);
                myreader.Close();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return d;
        }

        public void UpdateDocument(int id, string keyword1, string keyword2, string keyword3, string keyword4, string keyword5) {
            MySql.Data.MySqlClient.MySqlConnection connection;
            MySql.Data.MySqlClient.MySqlConnection connection2;
            string myConnectionString = "server=localhost;uid=root;pwd=;database=subiect7;";

            try
            {
                connection = new MySql.Data.MySqlClient.MySqlConnection();
                connection.ConnectionString = myConnectionString;
                connection.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = connection;
                cmd.CommandText = "update `document` set `keyword1`='" + keyword1 + "',`keyword2`='" + keyword2 + "',`keyword3`='" + keyword3 + "',`keyword4`='" + keyword4 + "',`keyword5`='" + keyword5 + "' where id ='" + id + "'";
                cmd.ExecuteNonQuery();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }
        }

        public List<Website> GetAllWebsites()
        {
            MySql.Data.MySqlClient.MySqlConnection connection;
            MySql.Data.MySqlClient.MySqlConnection connection2;
            string myConnectionString = "server=localhost;uid=root;pwd=;database=subiect7;";
            List<Website> websites = new List<Website>();

            try
            {
                connection = new MySql.Data.MySqlClient.MySqlConnection();
                connection.ConnectionString = myConnectionString;
                connection2 = new MySql.Data.MySqlClient.MySqlConnection();
                connection2.ConnectionString = myConnectionString;
                connection.Open();
                connection2.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = connection;
                cmd.CommandText = "select * from website";
                MySqlDataReader myreader = cmd.ExecuteReader();

                

                while (myreader.Read())
                {

                    Website w = new Website();
                    w.id = myreader.GetInt32("id");
                    w.url = myreader.GetString("url");

                    System.Diagnostics.Debug.WriteLine(w.url);

                    using (MySqlCommand cmd2 = new MySqlCommand()) {
                        
                        cmd2.Connection = connection2;
                        cmd2.CommandText = "select count(*) as total from document where idwebsite = '" + w.id + "'";

                        int total = Convert.ToInt32(cmd2.ExecuteScalar());
                        w.documents = total;

                        System.Diagnostics.Debug.WriteLine(cmd2.CommandText);
                    }

                    websites.Add(w);

                }
                myreader.Close();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return websites;
        }
    }
}