package org.example;

import javax.ejb.Remote;

@Remote
public interface PayTmFacadeRemote {
    void addTransaction(PayTmTransaction tx);
}
