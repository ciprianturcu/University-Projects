package org.example.features.search;

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
@UseTestDataFrom("src/test/resources/SearchMovie.csv")
public class SearchByKeywordStory {
    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public EndUserSteps endUserSteps;

    public String keyword;
    public String information;

    @Qualifier
    public String getQualifier() {
        return keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Issue("#WIKI-1")
    @Test
    public void searching_by_keyword_should_display_the_corresponding_information_ddt() {
        endUserSteps.goes_to_the_home_page();
        endUserSteps.looks_for(getKeyword());
        endUserSteps.should_see_search_information(getInformation());
    }
} 