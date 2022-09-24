package seven.atwork;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public class PayTmTxPaginationParam {
    private int pageNumber;
    private int limit;
    private String sort;
}
