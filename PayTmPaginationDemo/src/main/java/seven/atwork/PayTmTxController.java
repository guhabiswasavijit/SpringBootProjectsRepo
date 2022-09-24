package seven.atwork;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class PayTmTxController {
    @Autowired
    private PayTmTxRepository payTmTxRepository;
    @PostMapping(path = "/fetchData")
    public Page<PayTmTransaction> findTransactions(@RequestBody PayTmTxPaginationParam requestParam){
        Pageable sortedData =  PageRequest.of(requestParam.getPageNumber(),requestParam.getLimit(), Sort.by(requestParam.getSort()));
        log.info("got pagination request {}",requestParam);
        return payTmTxRepository.findAll(sortedData);
    }
}
