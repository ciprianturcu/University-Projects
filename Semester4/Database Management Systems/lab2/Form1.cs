using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Configuration;

namespace lab2
{
    public partial class Form1 : Form
    {

        SqlConnection conn;
        SqlDataAdapter daParent;
        SqlDataAdapter daChild;
        DataSet dataset;
        BindingSource bsParent;
        BindingSource bsChild;

        SqlCommandBuilder cmdBuilder;

        string querryParent;
        string querryChild;

        public Form1()
        {
            InitializeComponent();
            FillData();

            
        }

        void FillData()
        {
            conn = new SqlConnection(getConnectionString());

            querryParent = ConfigurationManager.AppSettings["SelectParent"];
            querryChild = ConfigurationManager.AppSettings["SelectChild"];

            //sql adaptors for both tables(Invoice is Parent, client is Child)
            daChild = new SqlDataAdapter(querryChild, conn);
            daParent = new SqlDataAdapter(querryParent, conn);
            dataset = new DataSet();
            daParent.Fill(dataset, ConfigurationManager.AppSettings["ParentTable"]);
            daChild.Fill(dataset, ConfigurationManager.AppSettings["ChildTable"]);

            //fill in insert, update,delete
            cmdBuilder = new SqlCommandBuilder(daChild);

            //data relation added to dataset
            dataset.Relations.Add(ConfigurationManager.AppSettings["ForeignKey"],
            dataset.Tables[ConfigurationManager.AppSettings["ParentTable"]].Columns[ConfigurationManager.AppSettings["ParentReferencedKey"]],
            dataset.Tables[ConfigurationManager.AppSettings["ChildTable"]].Columns[ConfigurationManager.AppSettings["ChildForeignKey"]]);

            //this.dataGridView1.DataSource = dataset.Tables["Invoice"];
            //this.dataGridView2.DataSource = this.dataGridView1.DataSource;
            //this.dataGridView2.DataMember = "InvoiceClient";

            bsParent = new BindingSource();
            bsParent.DataSource = dataset.Tables[ConfigurationManager.AppSettings["ParentTable"]];
            bsChild = new BindingSource(bsParent, ConfigurationManager.AppSettings["ForeignKey"]);

            this.dataGridView1.DataSource = bsParent;
            this.dataGridView2.DataSource = bsChild;
        }

        string getConnectionString()
        {
            return ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                daChild.Update(dataset, ConfigurationManager.AppSettings["ChildTable"]);
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message, "error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void dataGridView2_DataError(object sender, DataGridViewDataErrorEventArgs error)
        {
            MessageBox.Show("input data is not in the correct format.", "error", MessageBoxButtons.OK, MessageBoxIcon.Error);
        }

        private void dataGridView2_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }
    }
}   
