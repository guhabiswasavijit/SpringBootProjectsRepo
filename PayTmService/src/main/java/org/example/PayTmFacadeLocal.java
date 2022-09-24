package org.example;
import javax.ejb.Local;

@Local
public interface PayTmFacadeLocal {
    void addTransaction(PayTmTransaction tx);
}
