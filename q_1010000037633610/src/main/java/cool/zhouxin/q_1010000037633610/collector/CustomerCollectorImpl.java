package cool.zhouxin.q_1010000037633610.collector;

import cool.zhouxin.q_1010000037633610.constants.JdConstants;
import cool.zhouxin.q_1010000037633610.entity.ApproveCustomerEntity;
import cool.zhouxin.q_1010000037633610.entity.CustomerEntity;
import cool.zhouxin.q_1010000037633610.enums.AgeGroup;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author zhouxin
 * @since 2020/10/28 16:56
 */
public class CustomerCollectorImpl implements Collector<CustomerEntity,
        Map<AgeGroup, ApproveCustomerEntity>, List<ApproveCustomerEntity>> {

    @Override
    public Supplier<Map<AgeGroup, ApproveCustomerEntity>> supplier() {
        return () -> new HashMap<>();
    }

    @Override
    public BiConsumer<Map<AgeGroup, ApproveCustomerEntity>, CustomerEntity> accumulator() {
        return (map, customer) -> {
            AgeGroup ageGroup = AgeGroup.doMatch(customer.getAge());
            ApproveCustomerEntity approveCustomer = map.get(ageGroup);
            if (approveCustomer == null) {
                approveCustomer = new ApproveCustomerEntity(ageGroup.getRange().formatString());
                map.put(ageGroup,approveCustomer);
            }
            approveCustomer.setPeopleNum(approveCustomer.getPeopleNum() + 1);

            if (JdConstants.REJECT_APPROVAL.equals(customer.getRejectCode())) {
                approveCustomer.setRejectNum(approveCustomer.getRejectNum() + 1);
            }else {
                approveCustomer.setPassNum(approveCustomer.getPassNum() + 1);
            }
        };
    }

    @Override
    public BinaryOperator<Map<AgeGroup, ApproveCustomerEntity>> combiner() {
        return (map1, map2) -> {
            map1.putAll(map2);
            return map1;
        };
    }

    @Override
    public Function<Map<AgeGroup, ApproveCustomerEntity>, List<ApproveCustomerEntity>> finisher() {
        return map -> map.entrySet()
                         .stream()
                         .sorted(Comparator.comparing(Map.Entry::getKey))
                         .map(Map.Entry::getValue)
                         .collect(Collectors.toList());
    }

    @Override
    public Set<Characteristics> characteristics() {
//        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
        return Collections.emptySet();
    }
}
