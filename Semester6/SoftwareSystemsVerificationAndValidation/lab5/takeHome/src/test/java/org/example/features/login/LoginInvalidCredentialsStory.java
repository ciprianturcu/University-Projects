package org.example.features.login;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.example.steps.serenity.EndUserSteps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("src/test/resources/LoginCredentialsInvalid.csv")
public class LoginInvalidCredentialsStory {
    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public EndUserSteps endUserSteps;

    public String email;
    public String password;

    @Qualifier
    public String getQualifier() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Issue("#WIKI-1")
    @Test
    public void log_in_with_invalid_credentials_should_prompt_with_error() {
        endUserSteps.goes_to_the_home_page();
        endUserSteps.signs_in();
        endUserSteps.signs_in_with_imdb();
        endUserSteps.types_email(getEmail());
        endUserSteps.types_password(getPassword());
        endUserSteps.submits_sign_in_with_credentials();
        endUserSteps.should_see_incorrect_password_prompt();
    }
}