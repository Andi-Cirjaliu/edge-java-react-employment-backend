package application.json;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import application.models.EmploymentInfo;

public class EmploymentInfoSerializer implements JsonSerializer<EmploymentInfo> {

    @Override
    public JsonElement serialize(EmploymentInfo src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty("id", src.getId());
        jsonObj.addProperty("year", src.getYear());
        jsonObj.addProperty("population", src.getPopulation());
        jsonObj.addProperty("labor_force", src.getLabor_force());
        jsonObj.addProperty("population_percent", src.getPopulation_percent());
        jsonObj.addProperty("employed_total", src.getEmployed_total());
        jsonObj.addProperty("employed_percent", src.getEmployed_percent());
        jsonObj.addProperty("agrictulture_ratio", src.getAgrictulture_ratio());
        jsonObj.addProperty("nonagriculture_ratio", src.getNonagriculture_ratio());
        jsonObj.addProperty("unemployed", src.getUnemployed());
        jsonObj.addProperty("unemployed_percent", src.getUnemployed_percent());
        jsonObj.addProperty("not_in_labor", src.getNot_in_labor());

        // System.out.println(jsonObj);

        return jsonObj;
    }
}
