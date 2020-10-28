package cool.zhouxin.q_1010000037633610;

import cool.zhouxin.q_1010000037633610.collector.CustomerCollectorImpl;
import cool.zhouxin.q_1010000037633610.entity.ApproveCustomerEntity;
import cool.zhouxin.q_1010000037633610.entity.CustomerEntity;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhouxin
 * @since 2020/10/28 16:54
 */
public class Main {

    public static void main(String[] args) {
        List<CustomerEntity> customerEntities = Arrays.asList(
                new CustomerEntity(15, "REJECT_APPROVAL"),
                new CustomerEntity(100, "APPROVAL"),
                new CustomerEntity(19, "APPROVAL"),
                new CustomerEntity(19, "REJECT_APPROVAL"),
                new CustomerEntity(20, "REJECT_APPROVAL"),
                new CustomerEntity(30, "REJECT_APPROVAL")
        );

        List<ApproveCustomerEntity> collect = customerEntities.stream().collect(new CustomerCollectorImpl());
        System.out.println(collect);
    }
}
