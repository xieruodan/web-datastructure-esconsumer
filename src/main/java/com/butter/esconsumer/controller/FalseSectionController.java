package com.butter.esconsumer.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class FalseSectionController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient eurekaClient;

    /**
     * 每一个用户做错的题目的章节分布页面
     * @param userId
     * @param model
     * @return
     */
    @PostMapping("/false_section_info")
    public String getSectionInfo(Integer userId, Model model) {
        //根据名称获取服务地址
        InstanceInfo nextServerFromEureka = eurekaClient.getNextServerFromEureka("DataStructure_AdminEsProvider", false);
        String homePageUrl = nextServerFromEureka.getHomePageUrl();//真实的服务地址
        //跨域调用
        Map<String, Integer> falseSectionMap = restTemplate.getForObject(homePageUrl + "/exercise/record/" + userId, Map.class);

//        //负载均衡 跨域调用
//        Map<String, Integer> falseSectionMap = restTemplate.getForObject("http://DataStructure_AdminEsProvider/exercise/record/"
//                + userId, Map.class); //从eureka中找这个名称的服务

        log.info(falseSectionMap.toString());
        model.addAttribute("falseSectionMap", falseSectionMap);
        model.addAttribute("userId", userId);

        int falseTotal = 0;
        for (String s : falseSectionMap.keySet()) {
            falseTotal += falseSectionMap.get(s);
        }
        Map<String, String> falseSectionPercentageMap = new HashMap<>();
        for (String s : falseSectionMap.keySet()) {
            double percent = (double) falseSectionMap.get(s) / falseTotal * 100;
            DecimalFormat df = new DecimalFormat("#.00");
            String percentS = df.format(percent);
            falseSectionPercentageMap.put(s, percentS + "%");
        }
        model.addAttribute("falseSectionPercentageMap", falseSectionPercentageMap);
        log.info(falseSectionPercentageMap.toString());
//        for (Map.Entry<String, Integer> s : falseSectionMap.entrySet()) {
//            s.getKey();
//            s.getValue();
//        }

        return "false_section";
    }
}
