package seven.atwork;

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
    @Column(name = "TX_DATE")
    private Date txDate;
    @Column(name = "ACTIVITY")
    private String activity;
    @Column(name = "SOURCEDESTINATION")
    private String sourceDestination;
    @Column(name = "WALLETTXID")
    private String walletTxId;
    @Column(name = "USR_COMMENT")
    private String usr_comment;
    @Column(name = "DEBIT")
    private Integer debit;
    @Column(name = "CREDIT")
    private Integer credit;
    @Column(name = "TRANSACTION_BREAKUP")
    private String transaction_breakup;
    @Column(name = "status")
    private String STATUS;
}

