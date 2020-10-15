package application.rest.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

// import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import application.datasource.DataSource;
import application.json.JSONObjectFactory;
import application.metrics.EmploymentMetrics;

@RestController
@CrossOrigin
@RequestMapping("/v1")
public class APIEndpoints {

    @RequestMapping("/")
    @ResponseBody
    public ResponseEntity<String> root() {
        List<String> list = new ArrayList<String>();
        //return a simple list of strings
        list.add("Congratulations, your application is up and running. Please use the specific endpoints for this API:");
        list.add("  fetch dataset        : /dataset");
        return new ResponseEntity<String>(list.toString(), HttpStatus.OK);
    }

    @RequestMapping(value="/dataset", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> listDataset() {
      System.out.println("List endpoint");

      EmploymentMetrics.requests.labels("/dataset").inc();
      
      JsonArray array = JSONObjectFactory.getInstance().generateJSONArray(DataSource.getInstance().fetchDataset());

      HashMap<String, JsonArray> map = new HashMap<String, JsonArray>();
      map.put("dataset", array);

      Gson gson = new Gson();
      String json = gson.toJson(map);
      System.out.println("JSON reponse: "+ json);

      return new ResponseEntity<String>(json, HttpStatus.OK);
    }

}
