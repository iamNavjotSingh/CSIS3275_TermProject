package com.CSIS3275FinalProject.ras.service;

import com.CSIS3275FinalProject.ras.entity.Registration;
import com.CSIS3275FinalProject.ras.repository.OrderDetailRepository;
import com.CSIS3275FinalProject.ras.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
   @Autowired
   OrderDetailRepository orderDetailRepository;
   public void Insert(OrderDetail orderDetail)
   {
       orderDetailRepository.save(orderDetail);
   }

   public List<OrderDetail> getList(String productName,int price)
   {
      return orderDetailRepository.findByProductNameAndAndProductPriceAndFlag(productName,price,0);
   }

   public void deleteList(OrderDetail orderDetail)
   {
      orderDetailRepository.delete(orderDetail);
   }

   public List<OrderDetail> getListForOrderPLacing(Registration registration, int temp)
   {
       return orderDetailRepository.findByRegistrationAndFlag(registration,temp);
   }

}
