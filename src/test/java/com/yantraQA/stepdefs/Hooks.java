package com.yantraQA.stepdefs;

import com.google.inject.Inject;
import com.yantraQA.base.TestContextAPI;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
    @Inject
    TestContextAPI testContext;

    @Before
    public void setUp(Scenario s){
        testContext.setScenario(s);
    }

    @After
    public void softAssertion(){
        this.testContext.getSoftAssertions().assertAll();
    }

}
