package com.CSIS3275FinalProject.ras.repository;

import com.CSIS3275FinalProject.ras.entity.OrderDetail;
import com.CSIS3275FinalProject.ras.entity.Registration;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrderDetail,Integer> {

    List<OrderDetail> findByProductNameAndAndProductPriceAndFlag(String productName,int price,int flag);

    List<OrderDetail> findByRegistrationAndFlag(Registration registration,int flag);

}
