package application.datasource;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonSerializer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import application.json.JSONObjectFactory;
import application.json.EmploymentInfoSerializer;
import application.models.EmploymentInfo;

public class DataSource {
    private static DataSource instance = null;
    private List<EmploymentInfo> dataset = null;

    public static String DATASET_URL = "/home/student/projects/employment_dataset/employment-us/data/aat1.csv";
    // public static String DATASET_URL = "C:/work/OperatorPlayground/datasets/aat1.csv";
    public static String DATASET_ORIG_URL = "https://raw.githubusercontent.com/datasets/employment-us/master/data/aat1.csv";
    // public static String DATASET_URL = "https://datahub.io/core/employment-us/r/0.csv";

    private DataSource() {
        // System.out.println("Env: " + System.getenv());
        String datasetURLEnv = System.getenv("EMPLOYMENT_DATASET_URL");
        System.out.println("Env EMPLOYMENT_DATASET_URL: " + datasetURLEnv);
        if ( datasetURLEnv != null && ! datasetURLEnv.trim().isEmpty()) {
            DATASET_URL = datasetURLEnv.trim();
        } 
        System.out.println("DATASET_URL: " + DATASET_URL);
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }

        return instance;
    }

    public List<EmploymentInfo> fetchDataset() {
        if ( this.dataset != null && this.dataset.size() > 0 ) {
            //use the dataset already loaded because it is static
            return this.dataset;
        }

        if ( this.dataset == null ) {
            this.dataset = new ArrayList<EmploymentInfo>();
        }

        System.out.println("Fetch dataset...");

        EmploymentInfo datasetRow = null;

        InputStreamReader in = null;
        Iterable<CSVRecord> records = null;

        try {
            System.out.println("Fetch dataset from " + DATASET_URL);
            if (DATASET_URL.startsWith("http:") || DATASET_URL.startsWith("https:")) {
                in = new InputStreamReader(new URL(DATASET_URL).openStream());
            } else {
                in = new FileReader(DATASET_URL);
            }

            // try to parse the dataset
            records = parseCSV(in);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (records == null) {
            try {
                // an error occured... try the original URL for dataset
                System.out.println("Fetch dataset from " + DATASET_ORIG_URL);
                in = new InputStreamReader(new URL(DATASET_ORIG_URL).openStream());
                records = parseCSV(in);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (records == null) {
            System.out.println("could not fetch the dataset...");
            return this.dataset;
        }

        try {
            // parse the dataset
            for (CSVRecord record : records) {
                // System.out.println("Row " + record);
                // String year = record.get("year");
                // String population = record.get("population");
                // String population_percent = record.get("population_percent");
                // System.out.println("year: " + year + ", population: " + population + ",
                // population_percent: " + population_percent);

                datasetRow = new EmploymentInfo();
                datasetRow.setId((int) record.getRecordNumber());
                datasetRow.setYear(Integer.parseInt(record.get("year")));
                datasetRow.setPopulation(Integer.parseInt(record.get("population")));
                datasetRow.setLabor_force(Integer.parseInt(record.get("labor_force")));
                datasetRow.setPopulation_percent(Double.parseDouble(record.get("population_percent")));
                datasetRow.setEmployed_total(Integer.parseInt(record.get("employed_total")));
                datasetRow.setEmployed_percent(Double.parseDouble(record.get("employed_percent")));
                datasetRow.setAgrictulture_ratio(Integer.parseInt(record.get("agrictulture_ratio")));
                datasetRow.setNonagriculture_ratio(Integer.parseInt(record.get("nonagriculture_ratio")));
                datasetRow.setUnemployed(Integer.parseInt(record.get("unemployed")));
                datasetRow.setUnemployed_percent(Double.parseDouble(record.get("unemployed_percent")));
                datasetRow.setNot_in_labor(Integer.parseInt(record.get("not_in_labor")));

                this.dataset.add(datasetRow);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // System.out.println("dataset:" + dataset);

        return this.dataset;
    }

    private Iterable<CSVRecord> parseCSV(Reader reader ) {
        System.out.println("parse CSV...");

        Iterable<CSVRecord> records = null;
        try {
            records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(reader);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return records;
    }

    public EmploymentInfo findInfo(int id) {
        if ( this.dataset == null ) {
            fetchDataset();
        }
        List<EmploymentInfo> list = this.dataset;
        if ( this.dataset == null ) {
            return null;
        }

        EmploymentInfo info = null;

        for(int i=0; i< list.size();i++){
            if ( list.get(i).getId() == id ) {
                info = list.get(i);
                break;
            }
        }

        return info;
    }

    public EmploymentInfo findInfoBiggestLaborForcePercent() {
        if ( this.dataset == null ) {
            fetchDataset();
        }
        List<EmploymentInfo> list = this.dataset;
        if ( this.dataset == null ) {
            return null;
        }

        EmploymentInfo info = null;
        double maxPercent = 0;

        for(EmploymentInfo s: list){
            if ( s.getPopulation_percent() > maxPercent ) {
                info = s;
                maxPercent = s.getPopulation_percent();
            }
        }

        System.out.println("Year with biggest labor force percent: "+ (info != null ? info.getYear() : 0));

        return info;
    }

    public EmploymentInfo findInfoSmallestLaborForcePercent() {
        if ( this.dataset == null ) {
            fetchDataset();
        }
        List<EmploymentInfo> list = this.dataset;
        if ( this.dataset == null ) {
            return null;
        }

        EmploymentInfo info = null;
        double minPercent = 101;

        for(EmploymentInfo s: list){
            if ( s.getPopulation_percent() < minPercent ) {
                info = s;
                minPercent = s.getPopulation_percent();
            }
        }

        System.out.println("Year with smallest labor force percent: "+ (info != null ? info.getYear() : 0));

        return info;
    }

    public EmploymentInfo findInfoBiggestEmployedPercent() {
        if ( this.dataset == null ) {
            fetchDataset();
        }
        List<EmploymentInfo> list = this.dataset;
        if ( this.dataset == null ) {
            return null;
        }

        EmploymentInfo info = null;
        double maxPercent = 0;

        for(EmploymentInfo s: list){
            if ( s.getEmployed_percent() > maxPercent ) {
                info = s;
                maxPercent = s.getEmployed_percent();
            }
        }

        System.out.println("Year with biggest employed percent: "+ (info != null ? info.getYear() : 0));

        return info;
    }

    public EmploymentInfo findInfoSmallestEmployedPercent() {
        if ( this.dataset == null ) {
            fetchDataset();
        }
        List<EmploymentInfo> list = this.dataset;
        if ( this.dataset == null ) {
            return null;
        }

        EmploymentInfo info = null;
        double minPercent = 101;

        for(EmploymentInfo s: list){
            if ( s.getEmployed_percent() < minPercent ) {
                info = s;
                minPercent = s.getEmployed_percent();
            }
        }

        System.out.println("Year with smallest employed percent: "+ (info != null ? info.getYear() : 0));

        return info;
    }

    public EmploymentInfo findInfoBiggestUnemployedPercent() {
        if ( this.dataset == null ) {
            fetchDataset();
        }
        List<EmploymentInfo> list = this.dataset;
        if ( this.dataset == null ) {
            return null;
        }

        EmploymentInfo info = null;
        double maxPercent = 0;

        for(EmploymentInfo s: list){
            if ( s.getUnemployed_percent() > maxPercent ) {
                info = s;
                maxPercent = s.getUnemployed_percent();
            }
        }

        System.out.println("Year with biggest unemployed percent: "+ (info != null ? info.getYear() : 0));

        return info;
    }

    public EmploymentInfo findInfoSmallestUnemployedPercent() {
        if ( this.dataset == null ) {
            fetchDataset();
        }
        List<EmploymentInfo> list = this.dataset;
        if ( this.dataset == null ) {
            return null;
        }

        EmploymentInfo info = null;
        double minPercent = 101;

        for(EmploymentInfo s: list){
            if ( s.getUnemployed_percent() < minPercent ) {
                info = s;
                minPercent = s.getUnemployed_percent();
            }
        }

        System.out.println("Year with smallest unemployed percent: "+ (info != null ? info.getYear() : 0));

        return info;
    }

    public static void main(String[] args) {
        Gson gson = new Gson();

        DataSource instance = getInstance();

        System.out.println(gson.toJson(instance.fetchDataset()));

        // Map<String, List<EmploymentInfo>> a = new HashMap<String, List<EmploymentInfo>>();
        // a.put("dataset", instance.fetchDataset());
        // System.out.println(gson.toJson(a));

        // JsonArray array = JSONObjectFactory.getInstance().generateJSONArray(instance.fetchDataset());
        // HashMap<String, JsonArray> map = new HashMap<String, JsonArray>();
        // map.put("dataset", array);
        // System.out.println(gson.toJson(map));

        // instance.findInfoBiggestLaborForcePercent();
        // instance.findInfoSmallestLaborForcePercent();

        // instance.findInfoBiggestEmployedPercent();
        // instance.findInfoSmallestEmployedPercent();

        // instance.findInfoBiggestUnemployedPercent();
        // instance.findInfoSmallestUnemployedPercent();


        // GsonBuilder gsonBuilder = new GsonBuilder();
        // JsonSerializer<EmploymentInfo> serializer = new EmploymentInfoSerializer();
        // gsonBuilder.registerTypeAdapter(EmploymentInfo.class, serializer);

        // Gson customGson = gsonBuilder.create();
        // System.out.println(customGson.toJson(instance.fetchDataset())); 
    }

}
