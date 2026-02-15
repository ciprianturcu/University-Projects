package org.example.steps.serenity;

import net.thucydides.core.annotations.Step;
import org.example.pages.ImdbPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.equalTo;
public class EndUserSteps {

    ImdbPage imdbPage;

    @Step
    public void enters(String keyword) {
        imdbPage.enter_keywords(keyword);
    }

    @Step
    public void starts_search() {
        imdbPage.lookup_data();
    }

    @Step
    public void should_see_movie_name(String name, List<String> movies) {
        assertThat(imdbPage.getMovieName(movies), equalTo(name));
    }

    @Step
    public void goes_to_the_home_page() {
        imdbPage.open();
    }

    @Step
    public void signs_in() {
        imdbPage.main_sign_in();
    }

    @Step
    public void signs_in_with_imdb() {
        imdbPage.sign_in_with_imdb();
    }

    @Step
    public void types_email(String email) {
        imdbPage.enter_email(email);
    }

    @Step
    public void types_password(String password) {
        imdbPage.enter_password(password);
    }

    @Step
    public void submits_sign_in_with_credentials() {
        imdbPage.sign_in_with_credentials();
    }

    @Step
    public void should_see_the_username(String username) {
        assertThat(imdbPage.getCurrentUserLoggedIn(), equalTo(username));
    }

    @Step
    public void looks_for(String term) {
        enters(term);
        starts_search();
    }

    @Step
    public void should_see_invalid_search_information(String data){
        assertThat(imdbPage.getNoResults(), equalTo(data));
    }

    @Step
    public void should_see_incorrect_password_prompt() {
        assertThat(imdbPage.getAlertPrompt(), not(isEmptyString()));
    }

    @Step
    public void should_see_search_information(String data) {
        assertThat(imdbPage.getMovieList(), hasItem(containsString(data)));
    }
}