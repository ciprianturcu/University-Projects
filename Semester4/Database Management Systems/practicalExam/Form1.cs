using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;

namespace practicalExam
{
    public partial class Form1 : Form
    {
        SqlConnection conn;
        SqlDataAdapter daParent;
        SqlDataAdapter daChild;
        DataSet ds;
        BindingSource bsParent;
        BindingSource bsChild;

        SqlCommandBuilder cmdBuilder;

        string queryParent;
        string queryChild;

        public Form1()
        {
            InitializeComponent();
            FillData();
        }

        void FillData()
        {
            // SQL connection
            conn = new SqlConnection(getConnectionString());

            queryParent = "SELECT * FROM Producer"; //fill parent table
            queryChild = "SELECT * FROM Toy"; //fill child table
            ds = new DataSet();

            // SQL Data Adapters & Data Set
            daChild = new SqlDataAdapter(queryChild, conn);
            daParent = new SqlDataAdapter(queryParent, conn);
            daParent.Fill(ds, "Producer"); //parent table name
            daChild.Fill(ds, "Toy"); // child table name

            // fill in insert, update and delete commands
            cmdBuilder = new SqlCommandBuilder(daChild);

            // Data Relation (parent-child) added to Data Set
            ds.Relations.Add("Producer-Toy", ds.Tables["Producer"].Columns["id"], ds.Tables["Toy"].Columns["producerId"]);
            // parent-child, parent table name, parent primary key, child table name, child foreign key to parent

            // fill into DataGridViews using Data Binding
            bsParent = new BindingSource();
            bsParent.DataSource = ds.Tables["Producer"]; //parent table name
            bsChild = new BindingSource(bsParent, "Producer-Toy"); // name of parent-child above

            this.dataGridViewParent.DataSource = bsParent;
            this.dataGridViewChild.DataSource = bsChild;

            cmdBuilder.GetUpdateCommand();
        }

        string getConnectionString()
        {
            return "Data Source=DESKTOP-OG8HD08;Initial Catalog=practic;Integrated Security=true;";
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                daChild.Update(ds, "Toy"); //child table name
            }
            catch (SqlException ex)
            {
                MessageBox.Show(ex.Message);
            }

        }
    }
}
