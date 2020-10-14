package application.rest.v1;

import java.util.HashMap;

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
@RequestMapping("/v1")
public class ListEndpoint {

    @RequestMapping(value="/dataset", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> listSongs() {
      System.out.println("List endpoint");

      EmploymentMetrics.requests.labels("/list").inc();
      
      JsonArray array = JSONObjectFactory.getInstance().generateJSONArray(DataSource.getInstance().fetchDataset());

      HashMap<String, JsonArray> map = new HashMap<String, JsonArray>();
      map.put("dataset", array);

      Gson gson = new Gson();
      String json = gson.toJson(map);
      System.out.println("JSON reponse: "+ json);

      return new ResponseEntity<String>(json, HttpStatus.OK);
    }

}
