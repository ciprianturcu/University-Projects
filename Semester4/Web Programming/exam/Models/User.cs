using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace exam.Models
{
    public class User
    {
        public int id { get; set; }
        public string user { get; set; }
        public string password { get; set; }
        public int role { get; set; }
    }
}