using exam.Models;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Web;

namespace exam.DataAbstractionLayer
{
    public class DAL
    {
        public bool userExists(string user)
        {
            bool resp = false;
            MySql.Data.MySqlClient.MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "server=localhost;uid=root;pwd=;database=exam;";

            try
            {
                conn = new MySql.Data.MySqlClient.MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "select * from user where user='" + user + "'";
                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    resp = true;
                }
                myreader.Close();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return resp;
        }

        public string userPass(string user)
        {
            string resp = "";
            MySql.Data.MySqlClient.MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "server=localhost;uid=root;pwd=;database=exam;";

            try
            {
                conn = new MySql.Data.MySqlClient.MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "select * from user where user='" + user + "'";
                MySqlDataReader myreader = cmd.ExecuteReader();

                myreader.Read();

                resp = myreader.GetString("password");

                myreader.Close();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return resp;
        }

        public int userRole(string user) {
            int resp = -1;
            MySql.Data.MySqlClient.MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "server=localhost;uid=root;pwd=;database=exam;";

            try
            {
                conn = new MySql.Data.MySqlClient.MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "select * from user where user='" + user + "'";
                MySqlDataReader myreader = cmd.ExecuteReader();

                myreader.Read();
                
                resp = myreader.GetInt32("role");
                
                myreader.Close();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return resp;
        }

        public int userId(string user)
        {
            int resp = -1;
            MySql.Data.MySqlClient.MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "server=localhost;uid=root;pwd=;database=exam;";

            try
            {
                conn = new MySql.Data.MySqlClient.MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "select * from user where user='" + user + "'";
                MySqlDataReader myreader = cmd.ExecuteReader();

                myreader.Read();

                resp = myreader.GetInt32("id");

                myreader.Close();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return resp;
        }
        public List<ContentUser> getAllContent()
        {
            MySql.Data.MySqlClient.MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "server=localhost;uid=root;pwd=;database=exam;";
            List<ContentUser> contents = new List<ContentUser>();

            try
            {
                conn = new MySql.Data.MySqlClient.MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "select * from content";
                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    ContentUser content = new ContentUser();
                    content.id = myreader.GetInt32("id");
                    content.date = myreader.GetString("date");
                    content.title = myreader.GetString("title");
                    content.description = myreader.GetString("description");
                    content.url = myreader.GetString("url");
                    content.userid = myreader.GetInt32("userid");

                    contents.Add(content);
                }
                myreader.Close();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return contents;
        }

        public void createContent(int userId, string title, string description, string url)
        {
            MySql.Data.MySqlClient.MySqlConnection conn;
            string myConnectionString = "server=localhost;uid=root;pwd=;database=exam;";

            try
            {
                conn = new MySql.Data.MySqlClient.MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "INSERT INTO content (title, description, url, userid) VALUES (@title, @description, @url, @userid)";

                // You can replace 'projectManagerID' with the appropriate value based on your application's logic.
                cmd.Parameters.AddWithValue("@title", title);
                cmd.Parameters.AddWithValue("@description", description);
                cmd.Parameters.AddWithValue("@url", url);
                cmd.Parameters.AddWithValue("@userid", userId);
                Debug.WriteLine(title, description, url);
                cmd.ExecuteNonQuery();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }
        }
    }
}