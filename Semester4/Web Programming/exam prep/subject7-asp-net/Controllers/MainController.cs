using subiect7.DataAbstractionLayer;
using subiect7.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace subiect7.Controllers
{
    public class MainController : Controller
    {
        // GET: Main
        public ActionResult Index()
        {
            return RedirectToAction("GetAllDocuments");
        }

        public ActionResult GetAllDocuments()
        {
            DAL dal = new DAL();
            List<Document> documents = dal.GetAllDocuments();
            ViewData["documents"] = documents;
            return View("GetDocuments");
        }

        public ActionResult GetAllWebsites() {
            DAL dal = new DAL();
            List<Website> websites = dal.GetAllWebsites();
            System.Diagnostics.Debug.WriteLine(websites);
            ViewData["websites"] = websites;
            return View("GetWebsites");
        }

        public ActionResult RedirectToWebsites()
        {
            return RedirectToAction("GetAllWebsites");
        }

        public ActionResult RedirectToDocuments() {
            return RedirectToAction("GetAllDocuments");
        }

        public ActionResult RedirectToUpdateDocument(int id) {
            DAL dal = new DAL();
            Document document = dal.GetDocumentById(id);
            System.Diagnostics.Debug.WriteLine(document);
            ViewData["document"] = document;
            return View("UpdateDocument");
        }

        public ActionResult UpdateKeywordDocument() {

            DAL dal = new DAL();

            string documentId = Request.Params["documentId"];
            string documentKeyWord1 = Request.Params["documentKeyWord1"];
            string documentKeyWord2 = Request.Params["documentKeyWord2"];
            string documentKeyWord3 = Request.Params["documentKeyWord3"];
            string documentKeyWord4 = Request.Params["documentKeyWord4"];
            string documentKeyWord5 = Request.Params["documentKeyWord5"];

            if (!string.IsNullOrEmpty(documentKeyWord1) || !string.IsNullOrEmpty(documentKeyWord2) || !string.IsNullOrEmpty(documentKeyWord3) || !string.IsNullOrEmpty(documentKeyWord4) || !string.IsNullOrEmpty(documentKeyWord5)) {
                dal.UpdateDocument(int.Parse(documentId), documentKeyWord1, documentKeyWord2, documentKeyWord3, documentKeyWord4, documentKeyWord5);
            }
            return RedirectToAction("GetAllDocuments");

        }

        public ActionResult RedirectToKeyWordDocuments() {
            DAL dal = new DAL();
            List<Document> documents = dal.GetAllDocuments();
            ViewData["documents"] = documents;
            return View("FilterKeyWordDocuments");
        }


    }
}