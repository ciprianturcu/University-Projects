//#include "Tests.h"
//
//Test_Coat::Test_Coat()
//{
//}
//
//void Test_Coat::testEmptyConstructor()
//{
//	Coat c;
//	assert(c.getSize() == "");
//	assert(c.getColour() == "");
//	assert(c.getPrice() == 0);
//	assert(c.getQuantity() == 0);
//	assert(c.getLink() == "");
//}
//
//void Test_Coat::testFullConstructor()
//{
//	Coat c = Coat("A", "B", 0, 0, "C");
//	assert(c.getSize() == "A");
//	assert(c.getColour() == "B");
//	assert(c.getPrice() == 0);
//	assert(c.getQuantity() == 0);
//	assert(c.getLink() == "C");
//
//}
//
//void Test_Coat::testCopyConstructor()
//{
//	Coat c = Coat("A", "B", 0, 0, "C");
//	Coat cc = c;
//	assert(c.getSize() == "A");
//	assert(c.getColour() == "B");
//	assert(c.getPrice() == 0);
//	assert(c.getQuantity() == 0);
//	assert(c.getLink() == "C");
//}
//
//void Test_Coat::testGetSize()
//{
//	Coat c = Coat("A", "B", 0, 0, "C");
//	assert(c.getSize() == "A");
//}
//
//void Test_Coat::testGetColour()
//{
//	Coat c = Coat("A", "B", 0, 0, "C");
//	assert(c.getColour() == "B");
//}
//
//void Test_Coat::testGetPrice()
//{
//	Coat c = Coat("A", "B", 1, 0, "C");
//	assert(c.getPrice() == 1);
//}
//
//void Test_Coat::testGetQuantity()
//{
//	Coat c = Coat("A", "B", 0, 1, "C");
//	assert(c.getQuantity() == 1);
//}
//
//void Test_Coat::testGetLink()
//{
//	Coat c = Coat("A", "B", 0, 0, "C");
//	assert(c.getLink() == "C");
//}
//
//void Test_Coat::testSetSize()
//{
//	Coat c = Coat("A", "B", 0, 0, "C");
//	assert(c.getSize() == "A");
//	c.setSize("L");
//	assert(c.getSize() == "L");
//}
//
//void Test_Coat::testSetColour()
//{
//	Coat c = Coat("A", "B", 0, 0, "C");
//	assert(c.getColour() == "B");
//	c.setColour("L");
//	assert(c.getColour() == "L");
//}
//
//void Test_Coat::testSetPrice()
//{
//	Coat c = Coat("A", "B", 2, 0, "C");
//	assert(c.getPrice() == 2);
//	c.setPrice(5);
//	assert(c.getPrice() == 5);
//}
//
//void Test_Coat::testSetQuantity()
//{
//	Coat c = Coat("A", "B", 2, 2, "C");
//	assert(c.getQuantity() == 2);
//	c.setQuantity(5);
//	assert(c.getQuantity() == 5);
//}
//
//void Test_Coat::testSetLink()
//{
//	Coat c = Coat("A", "B", 0, 0, "C");
//	assert(c.getLink() == "C");
//	c.setLink("L");
//	assert(c.getLink() == "L");
//}
//
//void Test_Coat::testOverloadEqual()
//{
//	Coat c1 = Coat("A", "B", 0, 0, "C");
//	Coat c2 = Coat("A", "B", 0, 0, "C");
//	assert(c1 == c2);
//}
//
//void Test_Coat::testToString()
//{
//	Coat c = Coat("A", "B", 0, 0, "C");
//	assert(c.coat_to_string() == "Size: A,Colour: B,Price: 0.000000,Quantity: 0.000000,Link: C");
//}
//
//void Test_Coat::testCoatAll()
//{
//	testEmptyConstructor();
//	testFullConstructor();
//	testCopyConstructor();
//	testGetSize();
//	testGetColour();
//	testGetPrice();
//	testGetQuantity();
//	testGetLink();
//	testSetSize();
//	testSetColour();
//	testSetPrice();
//	testSetQuantity();
//	testSetLink();
//	testOverloadEqual();
//	testToString();
//}
//
//Test_Repository::Test_Repository()
//{
//}
//
//void Test_Repository::testGetAllCoats()
//{
//	Repository r;
//	r.addCoat(Coat("A", "B", 0, 0, "C"));
//	std::vector<Coat> list = r.getAllCoats();
//	assert(list[0].getSize() == "A");
//}
//
//void Test_Repository::testAddCoat()
//{
//	Repository r;
//	assert(r.getArrSize() == 0);
//	assert(r.addCoat(Coat("A", "B", 0, 0, "C")) == true);
//	assert(r.getArrSize() == 1);
//	assert(r.addCoat(Coat("A", "B", 0, 0, "C")) == false);
//	assert(r.getArrSize() == 1);
//}
//
//void Test_Repository::testDeleteCoat()
//{
//	Repository r;
//	assert(r.getArrSize() == 0);
//	r.addCoat(Coat("A", "B", 0, 0, "C"));
//	assert(r.getArrSize() == 1);
//	assert(r.deleteCoat(Coat("A", "C", 0, 0, "C")) == false);
//	assert(r.getArrSize() == 1);
//	r.addCoat(Coat("V", "B", 0, 0, "C"));
//	r.addCoat(Coat("X", "B", 0, 0, "C"));
//	r.addCoat(Coat("Z", "B", 0, 0, "C"));
//	assert(r.getArrSize() == 4);
//	assert(r.deleteCoat(Coat("A", "B", 0, 0, "C")) == true);
//	assert(r.getArrSize() == 3);
//}
//
//void Test_Repository::testUpdateCoat()
//{
//	Repository r;
//	r.addCoat(Coat("A", "B", 0, 0, "C"));
//	std::vector<Coat> list = r.getAllCoats();
//	assert(list[0].getSize() == "A");
//	assert(r.updateCoat(Coat("A", "B", 0, 0, "C"), Coat("C", "B", 0, 0, "A")) == true);
//	std::vector<Coat> list2 = r.getAllCoats();
//	assert(list2[0].getSize() == "C");
//	assert(r.updateCoat(Coat("V", "B", 0, 0, "C"), Coat("C", "B", 0, 0, "A")) == false);
//}
//
//void Test_Repository::testGetArrSize()
//{
//	Repository r;
//	r.addCoat(Coat("A", "B", 0, 0, "C"));
//	assert(r.getArrSize() == 1);
//}
//
//void Test_Repository::testGetCoatFromPos()
//{
//	Repository r;
//	r.addCoat(Coat("A", "B", 0, 0, "C"));
//	r.addCoat(Coat("F", "P", 0, 0, "C"));
//	r.addCoat(Coat("M", "Q", 0, 0, "C"));
//	assert(r.getCoatFromPos(0).getSize() == "A");
//	assert(r.getCoatFromPos(1).getSize() == "F");
//	assert(r.getCoatFromPos(2).getSize() == "M");
//}
//
//void Test_Repository::testGetCoatByAtr()
//{
//	Repository r;
//	Coat c1 = Coat("A", "B", 0, 0, "C");
//	Coat c2 = Coat("F", "P", 0, 0, "C");
//	Coat c3 = Coat("M", "Q", 0, 0, "C");
//	r.addCoat(c1);
//	r.addCoat(c2);
//	r.addCoat(c3);
//	assert(r.getCoatByAtr("F", "P", 0, "C") == c2);
//	assert(r.getCoatByAtr("F", "P", 1, "C") == Coat());
//}
//
//void Test_Repository::testRepositoryAll()
//{
//	testGetAllCoats();
//	testAddCoat();
//	testDeleteCoat();
//	testUpdateCoat();
//	testGetArrSize();
//	testGetCoatFromPos();
//	testGetCoatByAtr();
//}
//
//Test_Service::Test_Service()
//{
//}
//
//void Test_Service::testFullConstructor()
//{
//	Repository r;
//	r.addCoat(Coat("A", "B", 0, 0, "C"));
//	r.addCoat(Coat("F", "P", 0, 0, "C"));
//	r.addCoat(Coat("M", "Q", 0, 0, "C"));
//	Service s = Service(r);
//	assert(s.getSize() == 3);
//}
//
//void Test_Service::testCopyConstructor()
//{
//	Repository r;
//	r.addCoat(Coat("A", "B", 0, 0, "C"));
//	r.addCoat(Coat("F", "P", 0, 0, "C"));
//	r.addCoat(Coat("M", "Q", 0, 0, "C"));
//	Service s = Service(r);
//	assert(s.getSize() == 3);
//	Service sc = s;
//	assert(sc.getSize() == 3);
//}
//
//void Test_Service::testAddRepo()
//{
//	Repository r;
//	r.addCoat(Coat("S", "B", 0, 0, "httpsC"));
//	r.addCoat(Coat("L", "P", 0, 0, "httpsC"));
//	r.addCoat(Coat("M", "Q", 0, 0, "httpsC"));
//	Service s = Service(r);
//	assert(s.getSize() == 3);
//	assert(s.addToRepo("XL", "BB", 0, 0, "httpsCC") == true);
//	assert(s.getSize() == 4);
//	assert(s.addToRepo("S", "B", 0, 0, "httpsC") == false);
//	assert(s.getSize() == 4);
//	assert(s.addToRepo("B", "B", 0, 0, "httpsc") == false);
//	assert(s.getSize() == 4);
//	assert(s.addToRepo("XL", "B", 0, 0, "c") == false);
//	assert(s.getSize() == 4);
//}
//
//void Test_Service::testDeleteCoat()
//{
//	Repository r;
//	r.addCoat(Coat("S", "B", 0, 0, "httpsC"));
//	r.addCoat(Coat("L", "P", 0, 0, "httpsC"));
//	r.addCoat(Coat("M", "Q", 0, 0, "httpsC"));
//	Service s = Service(r);
//	assert(s.getSize() == 3);
//	s.addToRepo("XL", "BB", 0, 0, "httpsCC");
//	assert(s.getSize() == 4);
//	assert(s.deleteCoat(Coat("XL", "BB", 0, 0, "httpsCC")) == true);
//	assert(s.getSize() == 3);
//	assert(s.deleteCoat(Coat("XL", "BB", 0, 0, "httpsCC")) == false);
//	assert(s.getSize() == 3);
//	assert(s.deleteCoat(Coat("S", "P", 0, 10, "httpsC")) == false);
//	assert(s.getSize() == 3);
//}
//
//void Test_Service::testUpdateCoat()
//{
//	Repository r;
//	r.addCoat(Coat("S", "B", 0, 0, "httpsC"));
//	r.addCoat(Coat("L", "P", 0, 0, "httpsC"));
//	r.addCoat(Coat("M", "Q", 0, 0, "httpsC"));
//	Service s = Service(r);
//	assert(s.getSize() == 3);
//	assert(s.updateCoat("XL", "BB", 0, 0, "httpsCC", 1) == true);
//	assert(s.getSize() == 3);
//	std::vector<Coat> list = s.getCoats();
//	assert(list[1] == Coat("XL", "BB", 0, 0, "httpsCC"));
//	assert(s.updateCoat("XL", "BB", 0, 0, "httpsCC", 5) == false);
//	assert(s.updateCoat("XL", "BB", 0, 0, "CC", 5) == false);
//}
//
//void Test_Service::testGetCoats()
//{
//	Repository r;
//	r.addCoat(Coat("A", "B", 0, 0, "C"));
//	r.addCoat(Coat("F", "P", 0, 0, "C"));
//	r.addCoat(Coat("M", "Q", 0, 0, "C"));
//	Service s = Service(r);
//	assert(s.getSize() == 3);
//	std::vector<Coat> list = s.getCoats();
//	assert(list[0] == Coat("A", "B", 0, 0, "C"));
//	assert(list[1] == Coat("F", "P", 0, 0, "C"));
//	assert(list[2] == Coat("M", "Q", 0, 0, "C"));
//}
//
//void Test_Service::testGetSize()
//{
//	Repository r;
//	r.addCoat(Coat("A", "B", 0, 0, "C"));
//	r.addCoat(Coat("F", "P", 0, 0, "C"));
//	r.addCoat(Coat("M", "Q", 0, 0, "C"));
//	Service s = Service(r);
//	assert(s.getSize() == 3);
//}
//
//void Test_Service::testInitializeRepo()
//{
//	Repository r;
//	Service s = Service(r);
//	s.initializeRepo("data.txt");
//	assert(s.getSize() == 10);
//
//}
//
//void Test_Service::testFilterCoatsBySize()
//{
//	Repository r;
//	r.addCoat(Coat("A", "B", 0, 0, "C"));
//	r.addCoat(Coat("A", "P", 0, 0, "C"));
//	r.addCoat(Coat("M", "Q", 0, 0, "C"));
//	Service s = Service(r);
//	std::vector<Coat> result = s.filterCoatsBySize("A");
//	assert(result.size() == 2);
//
//	Repository r1;
//	r1.addCoat(Coat("A", "B", 0, 0, "C"));
//	r1.addCoat(Coat("A", "P", 0, 0, "C"));
//	r1.addCoat(Coat("M", "Q", 0, 0, "C"));
//	Service s1 = Service(r1);
//	std::vector<Coat> result1 = s1.filterCoatsBySize("");
//	assert(result1.size() == 3);
//
//	Repository r2;
//	r2.addCoat(Coat("A", "B", 0, 0, "C"));
//	r2.addCoat(Coat("A", "P", 0, 0, "C"));
//	r2.addCoat(Coat("M", "Q", 0, 0, "C"));
//	Service s2 = Service(r2);
//	std::vector<Coat> result2 = s2.filterCoatsBySize("F");
//	assert(result2.size() == 0);
//
//}
//
//void Test_Service::testAddToBasket()
//{
//	Service s = Service();
//	assert(s.addToBasket(Coat("A", "B", 0, 0, "C")) == true);
//	assert(s.getBasketSize() == 1);
//}
//
//void Test_Service::testDeleteCoatFromBasket()
//{
//	Service s = Service();
//	s.addToBasket(Coat("A", "B", 0, 0, "C"));
//	assert(s.getBasketSize() == 1);
//	assert(s.deleteCoatFromBasket(Coat("A", "B", 0, 0, "C")) == true);
//	assert(s.getBasketSize() == 0);
//	assert(s.deleteCoatFromBasket(Coat("A", "B", 0, 0, "C")) == false);
//}
//
//void Test_Service::testGetBasketSum()
//{
//	Service s = Service();
//	assert(s.getBasketSum() == 0);
//	s.addToBasketSum(100);
//	assert(s.getBasketSum() == 100);
//
//}
//
//void Test_Service::testAddToBasketSum()
//{
//	Service s = Service();
//	assert(s.getBasketSum() == 0);
//	s.addToBasketSum(180);
//	assert(s.getBasketSum() == 180);
//}
//
//void Test_Service::testGetCoatsFromBasket()
//{
//	Service s = Service();
//	s.addToBasket(Coat("A", "B", 0, 0, "C"));
//	std::vector<Coat> list = s.getCoatsFromBasket();
//	assert(list[0] == Coat("A", "B", 0, 0, "C"));
//}
//
//void Test_Service::testGetBasketSize()
//{
//	Service s = Service();
//	assert(s.getBasketSize() == 0);
//	s.addToBasket(Coat("A", "B", 0, 0, "C"));
//	assert(s.getBasketSize() == 1);
//}
//
//void Test_Service::testServiceAll()
//{
//	testFullConstructor();
//	testCopyConstructor();
//	testAddRepo();
//	testDeleteCoat();
//	testUpdateCoat();
//	testGetCoats();
//	testGetSize();
//	testInitializeRepo();
//	testFilterCoatsBySize();
//	testAddToBasket();
//	testDeleteCoatFromBasket();
//	testGetBasketSum();
//	testAddToBasketSum();
//	testGetCoatsFromBasket();
//	testGetBasketSize();
//
//}
//
//Test_All::Test_All()
//{
//}
//
//void Test_All::testAll()
//{
//	Test_Coat t1;
//	Test_Repository t2;
//	Test_Service t3;
//	t1.testCoatAll();
//	t2.testRepositoryAll();
//	t3.testServiceAll();
//}
//
