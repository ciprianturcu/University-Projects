package org.example.pages;

import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import net.serenitybdd.core.pages.WebElementFacade;
import java.util.stream.Collectors;

import net.serenitybdd.core.annotations.findby.FindBy;

import net.thucydides.core.pages.PageObject;

import java.util.List;


@DefaultUrl("https://www.imdb.com/")
public class ImdbPage extends PageObject {
    @FindBy(id = "suggestion-search")
    private WebElementFacade searchOnImdb;

    @FindBy(id = "suggestion-search-button")
    private WebElementFacade lookupButton;

    @FindBy(className = "navbar__user")
    private WebElementFacade signInButton;

    @FindBy(id = "ap_email")
    private WebElementFacade emailInput;

    @FindBy(id = "ap_password")
    private WebElementFacade passwordInput;

    @FindBy(id = "signInSubmit")
    private WebElementFacade signInRealButton;

    public void enter_keywords(String keyword) {
        searchOnImdb.type(keyword);
    }

    public void lookup_data() {
        lookupButton.click();
    }

    public List<String> getMovieList() {
        WebElementFacade movieList = find(By.className("ditJlF"));
        return movieList.findElements(By.className("find-title-result")).stream().map(row -> row.findElement(By.className("ipc-metadata-list-summary-item__c")).
                findElement(By.className("ipc-metadata-list-summary-item__tc")).findElement(By.tagName("a")).
                getText()).collect(Collectors.toList());
    }

    public String getNoResults(){
        WebElementFacade div = find(By.className("ditJlF"));
        return div.getText();
    }


    public String getMovieName(List<String> moviesList) {
        if(moviesList.isEmpty())
            return "";

        return moviesList.get(0);
    }


    public void main_sign_in() {
        signInButton.click();
    }

    public void sign_in_with_imdb() {
        find(By.className("list-group")).findElement(By.tagName("a")).click();
    }

    public void enter_email(String email) {
        emailInput.type(email);
    }

    public void enter_password(String password) {
        passwordInput.type(password);
    }

    public void sign_in_with_credentials() {
        signInRealButton.click();
    }

    public String getCurrentUserLoggedIn() {
        return find(By.className("navbar__user-name")).getText();
    }

    public String getAlertPrompt() {
        return find(By.className("a-alert-content")).getText();
    }

    public String getCaptchaPrompt() {
        return find(By.className("cvf-widget-link-new-captcha")).getText();
    }
}