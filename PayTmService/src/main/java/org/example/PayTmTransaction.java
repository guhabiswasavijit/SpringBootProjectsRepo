package org.example;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="PAYTM_TX")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PayTmTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tx_date")
    private Date txDate;
    @Column(name = "activity")
    private String activity;
    @Column(name = "sourceDestination")
    private String sourceDestination;
    @Column(name = "walletTxId")
    private String walletTxId;
    @Column(name = "usr_comment")
    private String usr_comment;
    @Column(name = "debit")
    private Integer debit;
    @Column(name = "credit")
    private Integer credit;
    @Column(name = "transaction_breakup")
    private String transaction_breakup;
    @Column(name = "status")
    private String status;
}
