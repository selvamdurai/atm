package com.atm;


import com.atm.controller.AccountController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AtmControllerAcceptanceTest extends ATMTest{
    @Autowired
    private WebApplicationContext webApplicationContext;
    @InjectMocks
    private AccountController accountController;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAccountBalance() throws Exception {
        UsernamePasswordAuthenticationToken principal =
                this.getPrincipal("123456789");

        SecurityContextHolder.getContext().setAuthentication(principal);
        mockMvc.perform(get("/api/atm")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.totalCashAvailable").value(1500.00))
                .andExpect(jsonPath("$.lowestCurrencyNoteAvailable").value(5));
    }

    @Test
    public void testWithdrawAmount() throws Exception{

    }
}
