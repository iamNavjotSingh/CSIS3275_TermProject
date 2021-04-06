package com.CSIS3275FinalProject.ras.controller;

import com.CSIS3275FinalProject.ras.entity.OrderDetail;
import com.CSIS3275FinalProject.ras.entity.Registration;
import com.CSIS3275FinalProject.ras.service.OrderDetailService;
import com.CSIS3275FinalProject.ras.service.RecognitionService;
import com.CSIS3275FinalProject.ras.service.RegistrationService;
import com.CSIS3275FinalProject.ras.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RedeemController {

    @Autowired
    RegistrationService registrationService;
    @Autowired
    RoleService roleService;
    @Autowired
    HttpSession httpSession;
    @Autowired
    RecognitionService recognitionService;
    @Autowired
    OrderDetailService orderDetailService;



    @RequestMapping("/redeem")
    public ModelAndView redeemPoints() {
        if(httpSession.getAttribute("emailId")==null)
        {
            ModelAndView modelAndView=new ModelAndView("Registration");
            return modelAndView;
        }
        ModelAndView modelAndView=new ModelAndView("redeem");
        Registration registration = registrationService.checkForLogin((String) httpSession.getAttribute("emailId"), (String) httpSession.getAttribute("pass"));
        modelAndView.addObject("earnedGold", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Gold"));
        modelAndView.addObject("earnedSilver", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Silver"));
        modelAndView.addObject("earnedBronze", recognitionService.getEarned((String)httpSession.getAttribute("emailId"), "Bronze"));
        modelAndView.addObject("points",registration.getPoints());
        return modelAndView;
    }

    @PostMapping("/cartItem")
    @ResponseBody
    public int addcart(@RequestParam("price") int price,@RequestParam("item") String item)
    {
        Registration registration = registrationService.checkForLogin((String) httpSession.getAttribute("emailId"), (String) httpSession.getAttribute("pass"));
        Integer points=registration.getPoints();
        if(points>=price)
        {
            OrderDetail orderDetail=new OrderDetail();
            orderDetail.setProductName(item);
            orderDetail.setProductPrice(price);
            orderDetail.setRegistration(registration);
            orderDetailService.Insert(orderDetail);
            System.out.println(orderDetail.getProductName());
            return 1;
        }
        else
        {
            return 0;
        }
    }

    @PostMapping("/deleteItem")
    @ResponseBody
    public int deletecart(@RequestParam("price") int price,@RequestParam("item") String item)
    {
        List<OrderDetail> list = orderDetailService.getList(item, price);
        System.out.println(list.get(0));
        orderDetailService.deleteList(list.get(0));
        return 1;
    }

    @PostMapping("/placeOrder")
    @ResponseBody
    public int placeOrder()
    {
        int points=0,totalAmount=0;
        Registration registration = registrationService.checkForLogin((String) httpSession.getAttribute("emailId"), (String) httpSession.getAttribute("pass"));
        List<OrderDetail> list = orderDetailService.getListForOrderPLacing(registration,0);
        if(list.isEmpty())
        {
            return 2;
        }
        points=registration.getPoints();
        for (OrderDetail orderDetail:list) {
            totalAmount=totalAmount+orderDetail.getProductPrice();
        }
        if(points>=totalAmount)
        {
            for (OrderDetail orderDetail:list) {
                orderDetail.setFlag(1);
                orderDetailService.Insert(orderDetail);
            }
            registration.setPoints(points-totalAmount);
            registrationService.insert(registration);
            return 1;
        }
        else
        {
            return 0;
        }
    }
}
