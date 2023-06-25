using exam.DataAbstractionLayer;
using exam.Models;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace exam.Controllers
{
    public class MainController : Controller
    {
        // GET: Main
        public ActionResult Index()
        {
            return View("Authenticate");
        }

        private string GetCurrentUsername()
        {
            if (Session["currentUsername"] != null)
            {
                return Session["currentUsername"].ToString();
            }
            else
            {
                return null;
            }
        }
        private void SetCurrentUsername(string username)
        {
            Session["currentUsername"] = username;
        }

        private void SetCurrentPassword(string password)
        {
            Session["currentPassword"] = password;
        }


        [HttpPost]
        public ActionResult Authenticate(string username, string password)
        {
            DAL dal = new DAL();
            if (dal.userPass(username) == password && dal.userExists(username))
            {
                this.SetCurrentUsername(username);
                this.SetCurrentPassword(password);
                return RedirectToAction("DecideRole");
            }
            else
            {
                return View("Authenticate");
            }
        }

        public ActionResult DecideRole()
        {
            DAL dal = new DAL();
            string username = GetCurrentUsername();
            int role = dal.userRole(username);

            if (role == 1) //reader
            {
                return RedirectToAction("ViewContent");
                
            }
            else if (role == 2) // writer
            {
                return View("ViewAddEntity");
            }
            else {
                return View("Authenticate");
            }
        }

        public ActionResult ViewContent() {
            DAL dal = new DAL();
            List<ContentUser> content = dal.getAllContent();
            ViewData["content"] = content;
            return View("ViewAllContent");
        }

        public ActionResult AddEntity() {
            DAL dal = new DAL();
            string titles = Request.Params["titlesList"];
            string descriptions = Request.Params["descriptionsList"];
            string urls = Request.Params["urlsList"];
            string username = GetCurrentUsername();
            int userId = dal.userId(username);

            Debug.WriteLine(titles,descriptions,urls);

            if (!string.IsNullOrEmpty(titles) || !string.IsNullOrEmpty(descriptions) || !string.IsNullOrEmpty(urls) || userId != -1)
            {

                string[] titleList = titles.Split(';');
                string[] descriptionList = descriptions.Split(';');
                string[] urlList = urls.Split(';');

                if (titleList.Length == descriptionList.Length && descriptionList.Length == urlList.Length)
                {
                    Debug.WriteLine(titleList.Length);
                    Debug.WriteLine(descriptionList.Length);
                    Debug.WriteLine(urlList.Length);
                    for (int i = 0; i < titleList.Length; i++)
                    {
                        Debug.WriteLine(titleList[i]);
                        Debug.WriteLine(descriptionList[i]);
                        Debug.WriteLine(urlList[i]);
                        dal.createContent(userId, titleList[i], descriptionList[i], urlList[i]);
                    }
                }
            }
            
            return View("ViewAddEntity");

        }

        public ActionResult Refresh() {
            return RedirectToAction("ViewContent");
        }

    }
}