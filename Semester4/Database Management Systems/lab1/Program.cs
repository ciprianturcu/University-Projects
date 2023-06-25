using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab1
{
    internal class Program
    {
        static void Main(string[] args)
        {
            string connectionString = "Data Source=DESKTOP-OG8HD08;" +
                "Initial Catalog=lawfirm; Integrated Security = true";

            SqlConnection connection = new SqlConnection(connectionString);

            connection.Open();

            string strAtourney = "SELECT * FROM Atourney";
            SqlCommand cmd = new SqlCommand(strAtourney, connection);

            //sql data reader
            using (SqlDataReader reader = cmd.ExecuteReader())
            {
                while (reader.Read())
                {
                    Console.WriteLine("{0}, {1}", reader[0], reader[1]);
                }
            }

            connection.Close();

            //sql data adapter, data set
            SqlDataAdapter daAtourney = new SqlDataAdapter(strAtourney,connection);
            DataSet dset = new DataSet();
            //fill
            daAtourney.Fill(dset, "Atourney");

            foreach (DataRow a in dset.Tables["Atourney"].Rows)
            {
                Console.WriteLine("{0}, {1}", a["id"], a["first_name"]);

            }
        }
    }
}
