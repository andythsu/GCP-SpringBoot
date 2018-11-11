package com.example.SpringBoot.DemoREST;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.rmi.activation.ActivationGroup_Stub;
import java.util.List;
import java.util.Map;

/**
 * @Author: Andy Su
 * @Date: 2018-11-10
 */

public interface DemoRoute {
    String TYPE = "type";
    String NAME = "name";

    @RequestMapping(value = "/demo" , method = RequestMethod.GET)
    List<DemoDto> getAll();

    @RequestMapping(value = "/demo/" + "{" + TYPE + "}", method = RequestMethod.GET)
    List<DemoDto> getAllByType(String type);

    @RequestMapping(value = "/demo/" + "{" + TYPE +"}" + "/" + "{" + NAME + "}", method = RequestMethod.GET)
    List<DemoDto> getAllByTypeAndName(String type, String name);

    @RequestMapping(value = "/demo", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    String save(Map<String, String> data);
}
