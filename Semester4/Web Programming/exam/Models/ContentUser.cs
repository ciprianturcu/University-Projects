using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace exam.Models
{
    public class ContentUser
    {
        public int id { get; set; }
        public string date { get; set; }
        public string title { get; set; }
        public string description { get; set; }
        public string url { get; set; }
        public int userid { get; set; }
    }
}