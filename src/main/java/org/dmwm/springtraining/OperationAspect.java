package org.dmwm.springtraining;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.dmwm.springtraining.data.AccountRepository;
import org.dmwm.springtraining.data.history.AccountHistoryRepository;
import org.dmwm.springtraining.model.Account;
import org.dmwm.springtraining.model.history.AccountHistory;
import org.dmwm.springtraining.model.history.AccountOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class OperationAspect {

    @Autowired
    AccountHistoryRepository historyRepository;

    @Autowired
    AccountRepository accountRepository;

    @Pointcut("execution(* org.dmwm.springtraining.controller.AccountController.deposit(..))")
    private void depositLog(){}

    @Pointcut("execution(* org.dmwm.springtraining.controller.AccountController.withdraw(..))")
    private void withdrawLog(){}

    @Pointcut("execution(* org.dmwm.springtraining.controller.AccountController.transfer(..))")
    private void transferLog(){}

    @Before("depositLog() || withdrawLog() || transferLog()")
    public void operationHistorySave(JoinPoint jp) {
        System.out.println("LOGGING OPERATION = " + jp.getSignature().getName().toUpperCase());
        AccountOperation operation = AccountOperation.valueOf(jp.getSignature().getName().toUpperCase());

        Object [] args = jp.getArgs();

        AccountHistory logRecord = new AccountHistory();
        String accountNumber = null, fromAccount = null;
        double amount = 0;
        Account a;

        switch (operation){
            case DEPOSIT:
                accountNumber = (String)args[0];
                amount = (double)args[1];
                a = accountRepository.findByAccountNumber(accountNumber);

                logRecord.setAccountNumber(accountNumber);
                logRecord.setBalanceBefore(a.getBalance());
                logRecord.setBalanceAfter(a.getBalance() + amount);

                System.out.println("DEPOSIT --> " + a.getBalance() + " --> " + (a.getBalance() + amount));

                break;

            case WITHDRAW:
                accountNumber = (String)args[0];
                amount = (double)args[1];
                a = accountRepository.findByAccountNumber(accountNumber);

                logRecord.setBalanceBefore(a.getBalance());
                logRecord.setBalanceAfter(a.getBalance() + amount);

                System.out.println("WITHDRAW --> " + a.getBalance() + " --> " + (a.getBalance() - amount));

                break;

            case TRANSFER:
                accountNumber = (String)args[1];
                fromAccount = (String)args[0];
                amount = (double)args[2];
                a = accountRepository.findByAccountNumber(accountNumber);

                logRecord.setFromAccountNumber(fromAccount);
                logRecord.setBalanceBefore(a.getBalance());
                logRecord.setBalanceAfter(a.getBalance() + amount);

                System.out.println("TRANSFER --> " + fromAccount + " --> " + accountNumber + " = " + a.getBalance());

                break;
        }


        logRecord.setTime(LocalDateTime.now());
        logRecord.setOperation(operation);
        logRecord.setAccountNumber(accountNumber);
        System.out.println(logRecord.toString());
        historyRepository.save(logRecord);

    }

}
