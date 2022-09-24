package org.example;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
@Stateless(name ="PayTmTxFacade")
@Local(PayTmFacadeLocal.class)
@Remote(PayTmFacadeRemote.class)
public class PayTmFacade {
    @PersistenceContext(unitName="PayTm")
    private EntityManager entityManager;
    private static Log logger = LogFactory.getLog(PayTmFacade.class);
    public void addTransaction(PayTmTransaction tx) {
        logger.debug(tx.toString());
        entityManager.persist(tx);
        logger.debug("Persisted Successfully");
    }

}
