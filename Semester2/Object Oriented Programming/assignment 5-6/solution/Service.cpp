#include "Service.h"
#include <string>

Service::Service(Repository repo)
{
	this->repo = repo;
}

Service::~Service()
{
}

Service::Service()
{
}

Service::Service(const Service& s)
{
	this->repo = s.repo;
}

bool Service::addToRepo(std::string size, std::string colour, double price, double quantity, std::string photo_link)
{
	Coat c = Coat(size, colour, price, quantity, photo_link);
	return repo.addCoat(c);
}

bool Service::deleteCoat(const Coat& c)
{
	return repo.deleteCoat(c);
}

bool Service::updateCoat(std::string size, std::string colour, double price, double quantity, std::string photo_link, int position)
{
	if (position > repo.getArrSize() || position < 0)
		return false;
	Coat c = repo.getCoatFromPos(position);
	Coat u = Coat(size, colour, price, quantity, photo_link);
	return repo.updateCoat(c, u);
}

Coat* Service::getCoats() const
{
	return repo.getAllCoats();
}

int Service::getSize() const
{
	return repo.getArrSize();
}

void Service::initializeRepo()
{
	addToRepo("M", "Black", 50.00, 14.00, "https://www.google.com/search?q=black+trench+coat&tbm=isch&ved=2ahUKEwiH1LeR1_r2AhXq8eAKHSkMBXUQ2-cCegQIABAA&oq=black+trench+coat&gs_lcp=CgNpbWcQA1AAWABgAGgAcAB4AIABAIgBAJIBAJgBAKoBC2d3cy13aXotaW1n&sclient=img&ei=AQlLYsfPKerjgwepmJSoBw&bih=924&biw=1707#imgrc=ZNXzUuwsNKBOCM");
	addToRepo("XXL", "Grey", 100.00, 3.00, "https://www.google.com/search?q=grey+trench+coats&tbm=isch&ved=2ahUKEwjvmI7M1_r2AhWg8LsIHfP4Bv0Q2-cCegQIABAA&oq=grey+trench+coats&gs_lcp=CgNpbWcQAzIFCAAQgAQ6BwgjEO8DECc6CggjEO8DEOoCECc6BAgAEENQjgdYkx5giyRoAXAAeACAAWmIAdcMkgEEMTUuM5gBAKABAaoBC2d3cy13aXotaW1nsAEKwAEB&sclient=img&ei=fAlLYu_qLKDh7_UP8_Gb6A8&bih=924&biw=1707#imgrc=kf6qXlqW_U-deM");
	addToRepo("XXS", "White", 78.00, 20.00, "https://www.google.com/search?q=white+trench+coats&sxsrf=APq-WBsbeuwLjQG2Y7wLy4BXPJwzHcNDdA:1649085117092&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjn9u7k2Pr2AhURr6QKHQ8fDnsQ_AUoAXoECAEQAw&biw=1707&bih=924&dpr=1.5#imgrc=qktmg4MTY6ctfM");
	addToRepo("L", "White", 48.00, 150.00, "https://www.google.com/search?q=white+trench+coats&sxsrf=APq-WBsbeuwLjQG2Y7wLy4BXPJwzHcNDdA:1649085117092&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjn9u7k2Pr2AhURr6QKHQ8fDnsQ_AUoAXoECAEQAw&biw=1707&bih=924&dpr=1.5#imgrc=ex4ASemevCYv7M");
	addToRepo("XL", "Grey", 230.00, 80.00, "https://www.google.com/search?q=grey+trench+coats&tbm=isch&ved=2ahUKEwivre3m2Pr2AhULjxoKHaZtCecQ2-cCegQIABAA&oq=grey+trench+coats&gs_lcp=CgNpbWcQAzIHCCMQ7wMQJzoECAAQGDoGCAAQBxAeOggIABAHEAUQHjoICAAQCBAHEB5QgwdYiAxglw5oAHAAeACAAV6IAa8DkgEBNZgBAKABAaoBC2d3cy13aXotaW1nwAEB&sclient=img&ei=wQpLYu_ID4ueaqbbpbgO&bih=924&biw=1707#imgrc=H1efVFI2lh9FKM");
	addToRepo("S", "Blue", 60.00, 940.00, "https://www.google.com/search?q=blue+trench+coats&tbm=isch&ved=2ahUKEwjl2-Xl1_r2AhUaSkEAHX7SCpcQ2-cCegQIABAA&oq=blue+trench+coats&gs_lcp=CgNpbWcQAzIFCAAQgAQ6BwgjEO8DECc6BggAEAcQHjoICAAQBxAFEB5Q5gdYvgpg2wtoAHAAeACAAV2IAZ4DkgEBNZgBAKABAaoBC2d3cy13aXotaW1nwAEB&sclient=img&ei=sglLYuW6JJqUhbIP_qSruAk&bih=924&biw=1707#imgrc=UKsZWv6j3O9qvM");
	addToRepo("M", "Green", 99.00, 80.00, "https://www.google.com/search?q=green+trench+coats&tbm=isch&ved=2ahUKEwjp4N2J2fr2AhXkZ_EDHShZAFEQ2-cCegQIABAA&oq=green+trench+coats&gs_lcp=CgNpbWcQAzIFCAAQgAQyBggAEAUQHjIECAAQGDoHCCMQ7wMQJzoGCAAQBxAeOggIABAHEAUQHjoICAAQCBAHEB5Q3A1YihRgrRZoAHAAeACAAVWIAe4DkgEBNpgBAKABAaoBC2d3cy13aXotaW1nwAEB&sclient=img&ei=CgtLYumzGOTPxc8PqLKBiAU&bih=924&biw=1707#imgrc=AESSDcygtV3qtM");
	addToRepo("S", "Red", 105.00, 30.00, "https://www.google.com/search?q=red+trench+coats&tbm=isch&ved=2ahUKEwiW5M-T2fr2AhXjAmMBHZFXCkEQ2-cCegQIABAA&oq=red+trench+coats&gs_lcp=CgNpbWcQAzIFCAAQgAQ6BwgjEO8DECc6BggAEAcQHjoECAAQGFDqBlj_CGCHC2gAcAB4AIABbYgB5wKSAQMzLjGYAQCgAQGqAQtnd3Mtd2l6LWltZ8ABAQ&sclient=img&ei=HwtLYtbYCOOFjLsPka-piAQ&bih=924&biw=1707#imgrc=oNe3g1F6ZU8iYM");
	addToRepo("XXL", "White", 138.00, 27.00, "https://www.google.com/search?q=whitetrench+coats&tbm=isch&ved=2ahUKEwi-07mA2fr2AhULpBoKHfBgCSYQ2-cCegQIABAA&oq=whitetrench+coats&gs_lcp=CgNpbWcQAzIGCAAQBxAeOgcIIxDvAxAnOgQIABBDOgUIABCABFDDCFjrDmDuD2gAcAB4AIABYogB5QSSAQE3mAEAoAEBqgELZ3dzLXdpei1pbWfAAQE&sclient=img&ei=9gpLYr6AOYvIavDBpbAC&bih=924&biw=1707#imgrc=JOEGx0rlHAl1yM");
	addToRepo("M", "Beige", 140.00, 100.00, "https://www.google.com/search?q=beige+trench+coats&tbm=isch&ved=2ahUKEwjQsdKr2fr2AhUJ4hoKHV2ECzIQ2-cCegQIABAA&oq=beige+trench+coats&gs_lcp=CgNpbWcQAzIFCAAQgAQ6BwgjEO8DECc6BggAEAcQHlCACVjREWDaFGgAcAB4AIABZIgB6QSSAQM2LjGYAQCgAQGqAQtnd3Mtd2l6LWltZ8ABAQ&sclient=img&ei=UQtLYpDFH4nEa92IrpAD&bih=924&biw=1707#imgrc=d1xOmQdTvr6BKM");

}


