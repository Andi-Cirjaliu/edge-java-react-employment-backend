package application.json;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import application.datasource.DataSource;
import application.models.EmploymentInfo;

public class JSONObjectFactory {
    private static JSONObjectFactory instance = null;

    private JSONObjectFactory() {

    }

    public static JSONObjectFactory getInstance() {
        if ( instance == null ) {
            instance = new JSONObjectFactory();
        }

        return instance;
    }

    public JsonObject generateJSON(EmploymentInfo employmentInfo) {
        JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty("id", employmentInfo.getId());
        jsonObj.addProperty("year", employmentInfo.getYear());
        jsonObj.addProperty("population", employmentInfo.getPopulation());
        jsonObj.addProperty("labor_force", employmentInfo.getLabor_force());
        jsonObj.addProperty("population_percent", employmentInfo.getPopulation_percent());
        jsonObj.addProperty("employed_total", employmentInfo.getEmployed_total());
        jsonObj.addProperty("employed_percent", employmentInfo.getEmployed_percent());
        jsonObj.addProperty("agrictulture_ratio", employmentInfo.getAgrictulture_ratio());
        jsonObj.addProperty("nonagriculture_ratio", employmentInfo.getNonagriculture_ratio());
        jsonObj.addProperty("unemployed", employmentInfo.getUnemployed());
        jsonObj.addProperty("unemployed_percent", employmentInfo.getUnemployed_percent());
        jsonObj.addProperty("not_in_labor", employmentInfo.getNot_in_labor());

        return jsonObj;
    }

    public JsonArray generateJSONArray(List<EmploymentInfo> employment) {
        JsonArray jsonArray = new JsonArray();

        employment.forEach(employmentInfo -> jsonArray.add(generateJSON(employmentInfo)));
        System.out.println("Generated JSON array:"+ jsonArray);

        return jsonArray;
    }

    public String generateJSON(List<EmploymentInfo> employment) {
        JsonArray jsonArray = generateJSONArray(employment);

        String json = jsonArray.toString();
        System.out.println("Generated JSON:"+ json);

        return json;
    }

    public static void main(String[] args) {
        JSONObjectFactory.getInstance().generateJSONArray(DataSource.getInstance().fetchDataset());
        JSONObjectFactory.getInstance().generateJSON(DataSource.getInstance().fetchDataset());
    }
}
