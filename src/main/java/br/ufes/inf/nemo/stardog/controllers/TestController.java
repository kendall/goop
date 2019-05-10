package br.ufes.inf.nemo.stardog.controllers;

import com.complexible.stardog.ext.spring.SnarlTemplate;
import com.complexible.stardog.ext.spring.mapper.SimpleRowMapper;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private SnarlTemplate template;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "endpoint";
    }


    @RequestMapping(value = "/sparql{query}", method = RequestMethod.GET)
    @ResponseBody
    public String sparql(@RequestParam(value="query") String query, @RequestParam(value = "format") String format) {
        String resultJSON = "[";
        String eachResult = "";
        String sparql = "PREFIX foaf:<http://xmlns.com/foaf/0.1/> " +
                "select * { ?s rdf:type foaf:Person }";
        sparql = query;
        // Queries the database using the SnarlTemplate and gets back a list of mapped objects
        List<Map<String, String>> results = template.query(sparql, new SimpleRowMapper());
        if(!results.isEmpty()) {
            for (int i = 0; i < results.size(); i++) {
                System.out.println(results.get(i));
                eachResult = results.get(i).toString().replace("=", "\":\"");
                eachResult = eachResult.replace("{", "{\"");
                eachResult = eachResult.replace("}", "\"}");
                eachResult = eachResult.replace(", ", "\", \"");
                if(results.size() == 1) {
                    resultJSON += eachResult;
                }
                if(i != results.size()-1) {
                    if (i == 0)
                        resultJSON += eachResult;
                    else
                        resultJSON += ", " + eachResult;
                }
            }
        }
        resultJSON = resultJSON + "]";
        System.out.println(resultJSON);
        return resultJSON;
    }

    @RequestMapping("/test")
    public void test(@RequestParam(value="name", defaultValue="World") String name) {

        log.trace(">> test()");

        String sparql = "SELECT ?s ?p ?o WHERE { ?s ?p ?o } LIMIT 5";

        List<Map<String, String>> results = template.query(sparql, new SimpleRowMapper());

        for (Map<String, String> result : results) {

            log.trace("-- test() > --------------------------");

            Set keys = result.keySet();

            for (Iterator i = keys.iterator(); i.hasNext(); ) {
                String key = (String) i.next();
                String value = result.get(key);
                log.trace("-- test() > " + key + " | " + value);

            }
        }
    }


}