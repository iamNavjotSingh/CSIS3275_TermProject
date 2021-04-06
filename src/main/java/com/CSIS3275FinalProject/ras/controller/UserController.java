package com.CSIS3275FinalProject.ras.controller;

import com.CSIS3275FinalProject.ras.entity.OrderDetail;
import com.CSIS3275FinalProject.ras.entity.Recognition;
import com.CSIS3275FinalProject.ras.entity.Registration;
import com.CSIS3275FinalProject.ras.entity.Role;
import com.CSIS3275FinalProject.ras.service.OrderDetailService;
import com.CSIS3275FinalProject.ras.service.RecognitionService;
import com.CSIS3275FinalProject.ras.service.RegistrationService;
import com.CSIS3275FinalProject.ras.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;

@Controller
public class UserController {

    private static final String dirpath = "C:/Users/navjo/Desktop/Software final project/Uncomplete Final Reap/reap/src/main/resources/static/images";
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

    @RequestMapping("/")
    public ModelAndView register(RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView=new ModelAndView("Registration");
        redirectAttributes.addAttribute("failed");
        return modelAndView;
    }


    @RequestMapping(value = "/formdata", method = RequestMethod.POST)
    public ModelAndView register(Registration registration, RedirectAttributes redirectAttributes) {
        Registration registration1=registrationService.getUser(registration.getEmail());
        if(registration1!=null)
        {
            ModelAndView modelAndView=new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("failed","email id already exits");
            return modelAndView;
        }
        if (registration.getImage().isEmpty()) {
            System.out.println("hello");
            registration.setFileName("C:/Users/navjo/Desktop/Software final project/Uncomplete Final Reap/reap/src/main/resources/static/images/temp.png");
        } else {
            Path path = Paths.get(dirpath, registration.getFirstName() + ".jpeg");
            try {
                path = Files.write(path, registration.getImage().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(registration.getFirstName());
            registration.setFileName(path.toString());
        }
        registration.setPoints(0);
        Role user = new Role("user", 3, 2, 1);
        /*Role admin=new Role("admin");
        */
        /*Role practiceHead=new Role("Practice Head",9,6,3);
        Role supervisior=new Role("Practice Head",6,3,2);
        */

        List<Role> roleList = new ArrayList<>();
        roleList.add(user);

        registration.setRoles(roleList);
        roleService.insert(user);
        /*roleService.insert(admin);
*/
        if (registration.getRoles().contains("Practice Head")) {
            registration.setCurrentBadgeGold(9);
            registration.setCurrentBadgeSilver(6);
            registration.setCurrentBadgebronze(3);
        } else if (registration.getRoles().contains("supervisor")) {
            registration.setCurrentBadgeGold(6);
            registration.setCurrentBadgeSilver(3);
            registration.setCurrentBadgebronze(2);
        } else {
            registration.setCurrentBadgeGold(3);
            registration.setCurrentBadgeSilver(2);
            registration.setCurrentBadgebronze(1);
        }
        registrationService.insert(registration);
        ModelAndView modelAndView=new ModelAndView("redirect:/");
        redirectAttributes.addFlashAttribute("success","Successfully register");
        return modelAndView;
    }



    @RequestMapping("/formdata")
    public String registration() {
        return "Registration";
    }

    @PostMapping("/verifydata")
    @ResponseBody
    public int verifyLogin(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "password", required = false) String password)
    {
        Registration registration = registrationService.checkForLogin(email, password);
        System.out.println(registration);
        if(registration==null)
        {
            out.println("hello");
            return 0;
        }
        else if(registration.getActive() == 1)
        {
            System.out.println("registration");
            httpSession.setAttribute("emailId", email);
            if(registration.getFileName().contains("temp"))
            {
                httpSession.setAttribute("image", "/images/" + "temp" + ".png");
            }
            else
            {
                httpSession.setAttribute("image", "/images/" + registration.getFirstName() + ".jpeg");
            }
            httpSession.setAttribute("name", registration.getFirstName() + " " + registration.getLastName());
            httpSession.setAttribute("list", registrationService.getDetails(registration.getEmail()));
            httpSession.setAttribute("pass", registration.getPassword());
            return 1;
        }
        else
        {
            return 2;
        }
    }

    @RequestMapping(value = "/logindata")
    public ModelAndView validate() {
        Integer gold = null;
        Integer silver = null;
        Integer bronze = null;
        int adminflag = 0,userflag=0;

        if(httpSession.getAttribute("emailId")==null)
        {
            ModelAndView modelAndView=new ModelAndView("Registration");
            return modelAndView;
        }
        Registration registration = registrationService.checkForLogin((String) httpSession.getAttribute("emailId"), (String) httpSession.getAttribute("pass"));

        System.out.println(registration);
        if (registration != null && registration.getActive() == 1) {
            for (Role role :
                    registration.getRoles()) {
                if (role.getRole().equals("admin")) {
                    out.println("heeeeeeee");
                    adminflag=1;
                } else if (role.getRole().equals("user")) {
                    userflag=1;
                }
            }

            out.println(adminflag+userflag);
            if (adminflag == 1 && userflag==1) {
                out.println("hello from heree!!!!!!");
                ModelAndView modelAndView = new ModelAndView("AdminWithBadgeDashboard");
                modelAndView.addObject("gold", registration.getCurrentBadgeGold());
                modelAndView.addObject("silver", registration.getCurrentBadgeSilver());
                modelAndView.addObject("bronze", registration.getCurrentBadgebronze());
                modelAndView.addObject("earnedGold", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Gold"));
                modelAndView.addObject("earnedSilver", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Silver"));
                modelAndView.addObject("earnedBronze", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Bronze"));
                modelAndView.addObject("recognitionlist", recognitionService.getList());
                modelAndView.addObject("recognition", new Recognition());

                //httpSession.setAttribute("gold",registration.getCurrentBadgeGold());
                //httpSession.setAttribute("silver",registration.getCurrentBadgeSilver());
                //httpSession.setAttribute("bronze",registration.getCurrentBadgebronze());
                //httpSession.setAttribute("earnedGold",recognitionService.getEarned(email,"Gold"));
                //httpSession.setAttribute("earnedSilver",recognitionService.getEarned(email,"Silver"));
                //httpSession.setAttribute("earnedBronze",recognitionService.getEarned(email,"Bronze"));
                modelAndView.addObject("recognition", new Recognition());
                out.println(httpSession.getAttribute("emailId"));
                return modelAndView;
            } else if(adminflag==0 && userflag == 1) {
                ModelAndView modelAndView = new ModelAndView("UserDashboard");
                /*modelAndView.addObject("image","/images/"+registration.getFirstName()+".jpeg");
                modelAndView.addObject("name",registration.getFirstName()+" "+registration.getLastName());
                modelAndView.addObject("list",registrationService.getDetails(registration.getEmail()));
                modelAndView.addObject("email",registration.getEmail());*/
                modelAndView.addObject("gold", registration.getCurrentBadgeGold());
                modelAndView.addObject("silver", registration.getCurrentBadgeSilver());
                modelAndView.addObject("bronze", registration.getCurrentBadgebronze());
                modelAndView.addObject("earnedGold", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Gold"));
                modelAndView.addObject("earnedSilver", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Silver"));
                modelAndView.addObject("earnedBronze", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Bronze"));
                modelAndView.addObject("recognitionlist", recognitionService.getList());
                modelAndView.addObject("recognition", new Recognition());

                //httpSession.setAttribute("gold",registration.getCurrentBadgeGold());
                //httpSession.setAttribute("silver",registration.getCurrentBadgeSilver());
                //httpSession.setAttribute("bronze",registration.getCurrentBadgebronze());
                //httpSession.setAttribute("earnedGold",recognitionService.getEarned(email,"Gold"));
                //httpSession.setAttribute("earnedSilver",recognitionService.getEarned(email,"Silver"));
                //httpSession.setAttribute("earnedBronze",recognitionService.getEarned(email,"Bronze"));
                modelAndView.addObject("recognition", new Recognition());
                return modelAndView;
            }
            else
            {
                ModelAndView modelAndView = new ModelAndView("Registration");
                return modelAndView;
            }
        } else  {
            ModelAndView modelAndView = new ModelAndView("Registration");
            return modelAndView;
        }
    }

    @PostMapping("verifyDateFilter")
    @ResponseBody
    public int checkdate(@RequestParam(value = "date") String date)
    {
        out.println(date);
        if(date.contains("-"))
        {
            out.println("hello");
            httpSession.setAttribute("date",date);
            return 1;
        }
        else
        {
            return 0;
        }
    }
    @RequestMapping(value = "/filterdate")
    public ModelAndView filter(@RequestParam(value = "date",required = false) String date) throws ParseException {
        int flag = 0;
        if(httpSession.getAttribute("date")==null)
        {
            if (httpSession.getAttribute("emailId") != null) {
                Registration registration = registrationService.checkForLogin((String) httpSession.getAttribute("emailId"), (String) httpSession.getAttribute("pass"));

                //System.out.println(registration);
                if (registration != null && registration.getActive() == 1) {
                    for (Role role :
                            registration.getRoles()) {
                        if (role.getRole().equals("admin")) {
                            flag++;
                        } else if (role.getRole().equals("user")) {
                            flag++;
                        }
                    }
                    if (flag == 2) {
                        ModelAndView modelAndView = new ModelAndView("AdminWithBadgeDashboard");
                    /*modelAndView.addObject("image", "/images/" + registration.getFirstName() + ".jpeg");
                    modelAndView.addObject("name", registration.getFirstName() + " " + registration.getLastName());
                    modelAndView.addObject("list", registrationService.getDetails(registration.getEmail()));
                    modelAndView.addObject("email", registration.getEmail());*/
                        modelAndView.addObject("gold", registration.getCurrentBadgeGold());
                        modelAndView.addObject("silver", registration.getCurrentBadgeSilver());
                        modelAndView.addObject("bronze", registration.getCurrentBadgebronze());
                        modelAndView.addObject("earnedGold", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Gold"));
                        modelAndView.addObject("earnedSilver", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Silver"));
                        modelAndView.addObject("earnedBronze", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Bronze"));
                        modelAndView.addObject("recognitionlist", recognitionService.getList());
                        modelAndView.addObject("recognition", new Recognition());
                        out.println(httpSession.getAttribute("emailId"));
                        return modelAndView;
                    } else {
                        ModelAndView modelAndView = new ModelAndView("UserDashboard");
                /*modelAndView.addObject("image","/images/"+registration.getFirstName()+".jpeg");
                modelAndView.addObject("name",registration.getFirstName()+" "+registration.getLastName());
                modelAndView.addObject("list",registrationService.getDetails(registration.getEmail()));
                modelAndView.addObject("email",registration.getEmail());*/
                        modelAndView.addObject("gold", registration.getCurrentBadgeGold());
                        modelAndView.addObject("silver", registration.getCurrentBadgeSilver());
                        modelAndView.addObject("bronze", registration.getCurrentBadgebronze());
                        modelAndView.addObject("earnedGold", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Gold"));
                        modelAndView.addObject("earnedSilver", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Silver"));
                        modelAndView.addObject("earnedBronze", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Bronze"));
                        modelAndView.addObject("recognitionlist", recognitionService.getList());
                        modelAndView.addObject("recognition", new Recognition());

                        //httpSession.setAttribute("gold",registration.getCurrentBadgeGold());
                        //httpSession.setAttribute("silver",registration.getCurrentBadgeSilver());
                        //httpSession.setAttribute("bronze",registration.getCurrentBadgebronze());
                        //httpSession.setAttribute("earnedGold",recognitionService.getEarned(email,"Gold"));
                        //httpSession.setAttribute("earnedSilver",recognitionService.getEarned(email,"Silver"));
                        //httpSession.setAttribute("earnedBronze",recognitionService.getEarned(email,"Bronze"));
                        modelAndView.addObject("recognition", new Recognition());
                        return modelAndView;
                    }

                } else {
                    ModelAndView modelAndView = new ModelAndView("Registration");
                    return modelAndView;
                }
            }
            else {
                ModelAndView modelAndView = new ModelAndView("Registration");
                return modelAndView;
            }
        }
        else
        {
            String[] dates = httpSession.getAttribute("date").toString().split("-");
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date startdate = formatter.parse(dates[0]);
            Date enddate = formatter.parse(dates[1]);
            out.println(startdate);
            out.println(enddate);
            if (httpSession.getAttribute("emailId") != null) {
                Registration registration = registrationService.checkForLogin((String) httpSession.getAttribute("emailId"), (String) httpSession.getAttribute("pass"));

                //System.out.println(registration);
                if (registration != null && registration.getActive() == 1) {
                    for (Role role :
                            registration.getRoles()) {
                        if (role.getRole().equals("admin")) {
                            flag++;
                        } else if (role.getRole().equals("user")) {
                            flag++;
                        }
                    }
                    if (flag == 2) {
                        ModelAndView modelAndView = new ModelAndView("AdminWithBadgeDashboard");
                    /*modelAndView.addObject("image", "/images/" + registration.getFirstName() + ".jpeg");
                    modelAndView.addObject("name", registration.getFirstName() + " " + registration.getLastName());
                    modelAndView.addObject("list", registrationService.getDetails(registration.getEmail()));
                    modelAndView.addObject("email", registration.getEmail());*/
                        modelAndView.addObject("gold", registration.getCurrentBadgeGold());
                        modelAndView.addObject("silver", registration.getCurrentBadgeSilver());
                        modelAndView.addObject("bronze", registration.getCurrentBadgebronze());
                        modelAndView.addObject("earnedGold", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Gold"));
                        modelAndView.addObject("earnedSilver", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Silver"));
                        modelAndView.addObject("earnedBronze", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Bronze"));
                        modelAndView.addObject("recognitionlist", recognitionService.getFilterList(startdate, enddate));
                        modelAndView.addObject("recognition", new Recognition());
                        out.println(httpSession.getAttribute("emailId"));
                        return modelAndView;
                    } else {
                        ModelAndView modelAndView = new ModelAndView("UserDashboard");
                /*modelAndView.addObject("image","/images/"+registration.getFirstName()+".jpeg");
                modelAndView.addObject("name",registration.getFirstName()+" "+registration.getLastName());
                modelAndView.addObject("list",registrationService.getDetails(registration.getEmail()));
                modelAndView.addObject("email",registration.getEmail());*/
                        modelAndView.addObject("gold", registration.getCurrentBadgeGold());
                        modelAndView.addObject("silver", registration.getCurrentBadgeSilver());
                        modelAndView.addObject("bronze", registration.getCurrentBadgebronze());
                        modelAndView.addObject("earnedGold", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Gold"));
                        modelAndView.addObject("earnedSilver", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Silver"));
                        modelAndView.addObject("earnedBronze", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Bronze"));
                        modelAndView.addObject("recognitionlist", recognitionService.getFilterList(startdate, enddate));
                        modelAndView.addObject("recognition", new Recognition());

                        //httpSession.setAttribute("gold",registration.getCurrentBadgeGold());
                        //httpSession.setAttribute("silver",registration.getCurrentBadgeSilver());
                        //httpSession.setAttribute("bronze",registration.getCurrentBadgebronze());
                        //httpSession.setAttribute("earnedGold",recognitionService.getEarned(email,"Gold"));
                        //httpSession.setAttribute("earnedSilver",recognitionService.getEarned(email,"Silver"));
                        //httpSession.setAttribute("earnedBronze",recognitionService.getEarned(email,"Bronze"));
                        modelAndView.addObject("recognition", new Recognition());
                        return modelAndView;
                    }

                } else {
                    ModelAndView modelAndView = new ModelAndView("Registration");
                    return modelAndView;
                }
            }
            else {
                ModelAndView modelAndView = new ModelAndView("Registration");
                return modelAndView;
            }
        }

    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout() {
        //ModelAndView modelAndView =new ModelAndView("Registration");

        Registration registration=registrationService.getUser((String) httpSession.getAttribute("emailId"));
        List<OrderDetail> listForOrderPLacing = orderDetailService.getListForOrderPLacing(registration, 0);
if(!listForOrderPLacing.isEmpty())
{
    for (OrderDetail orderdetail:listForOrderPLacing) {
        orderDetailService.deleteList(orderdetail);
    }
}

        httpSession.removeAttribute("emailId");
        httpSession.invalidate();

        return "redirect:/";
    }
    @GetMapping(value = "/Downloadcsv")
    @ResponseBody
    public int downloadCsv(@RequestParam(value = "date",required = false) String date, HttpServletResponse response) throws ParseException, IOException {
        int flag = 0;
        if(httpSession.getAttribute("date")==null)
        {
            return 1;
        }
        else
            {
                String[] dates = httpSession.getAttribute("date").toString().split("-");
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Date startdate = formatter.parse(dates[0]);
                Date enddate = formatter.parse(dates[1]);
                out.println(startdate);
                out.println(enddate);
                List<Recognition> filterList = recognitionService.getFilterList(startdate, enddate);
                String csvFileName = "recognition.csv";
                response.setContentType("text/csv");
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"",
                        csvFileName);
                response.setHeader(headerKey, headerValue);

                ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                        CsvPreference.STANDARD_PREFERENCE);
                String[] header = {"recognitionId",
                        "badgeType","date","karma","message","registration",
                        "userEmail","flag" };

                csvWriter.writeHeader(header);


                for (Recognition list: filterList) {
                    csvWriter.write(list, header);
                }
                csvWriter.close();
                return 0;
            }
        }
    }



   /* @GetMapping("/logindata")
    public String backtohome(){
        return "redirect:/";
    }*/

