package com.frank.user.pact.v3;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import com.frank.user.MsUserApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;


@RunWith(SpringRestPactRunner.class)
@SpringBootTest(classes = MsUserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PactFolder("pacts/v3")
@Provider("UserDetails_provider")
public class UserDetailsPactTest {
    @TestTarget
    public final Target target = new HttpTarget("http", "localhost", 8080, "/api/userdetails");

    @State("User service running")
    public void toGetStates() {

    }
}
