package org.dmwm.springtraining.model;

import org.dmwm.springtraining.exception.NotEnoughFundsException;
import org.dmwm.springtraining.testutil.Generator;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AccountTest {

    @Test
    public void withdraw_pass() {
        Account account = Generator.newAccount();
        account.withdraw(200);
        assertThat(account.getBalance(), equalTo(800d));
    }

    @Test(expected = NotEnoughFundsException.class)
    public void withdraw_exception() {
        Generator.newAccount(Generator.newClientAtBank()).withdraw(5000);
    }

    @Test
    public void deposit() {
        Account account = Generator.newAccount();
        account.deposit(100);
        assertThat(account.getBalance(), equalTo(1100d));
    }
}
