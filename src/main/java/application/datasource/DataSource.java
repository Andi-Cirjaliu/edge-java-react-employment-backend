package application.datasource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonSerializer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import application.json.JSONObjectFactory;
import application.json.EmploymentInfoSerializer;
import application.models.EmploymentInfo;

public class DataSource {
    private static DataSource instance = null;
    private List<EmploymentInfo> dataset = new ArrayList<EmploymentInfo>();

    private DataSource() {
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }

        return instance;
    }

    public List<EmploymentInfo> fetchDataset() {
        List<EmploymentInfo> dataset = new ArrayList<EmploymentInfo>();

        String datasetURL = "https://raw.githubusercontent.com/datasets/employment-us/master/data/aat1.csv";
        // String datasetURL = "https://datahub.io/core/employment-us/r/0.csv";

        EmploymentInfo datasetRow = null;

        try {
            InputStreamReader in = new InputStreamReader(new URL(datasetURL).openStream());
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                // System.out.println("Row " + record);                
                // String year = record.get("year");
                // String population = record.get("population");
                // String population_percent = record.get("population_percent");
                // System.out.println("year: " + year + ", population: " + population + ", population_percent: " + population_percent);

                datasetRow = new EmploymentInfo();
                datasetRow.setId((int)record.getRecordNumber());
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

                dataset.add(datasetRow);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // System.out.println("dataset:" + dataset);

        return dataset;
    }

    // public Song findSong(int id) {
    //     List<Song> list = this.songs;

    //     Song song = null;

    //     for(int i=0; i< list.size();i++){
    //         if ( list.get(i).getId() == id ) {
    //             song = list.get(i);
    //             break;
    //         }
    //     }

    //     return song;
    // }

    // public Song findMostVotedSong() {
    //     List<Song> list = this.songs;

    //     Song song = null;
    //     int maxVotes = 0;

    //     for(Song s: list){
    //         if ( s.getRatingCount() > maxVotes ) {
    //             song = s;
    //             maxVotes = s.getRatingCount();
    //         }
    //     }

    //     System.out.println("Most voted song: "+ song);

    //     return song;
    // }

    // public Song findLeastVotedSong() {
    //     List<Song> list = this.songs;

    //     Song song = null;
    //     int minVotes = 10000000;

    //     for(Song s: list){
    //         if ( s.getRatingCount() < minVotes ) {
    //             song = s;
    //             minVotes = s.getRatingCount();
    //         }
    //     }

    //     System.out.println("Least voted song: "+ song);

    //     return song;
    // }

    // public Song findBestRatedSong() {
    //     List<Song> list = this.songs;

    //     Song song = null;
    //     double maxRating = 0;

    //     for(Song s: list){
    //         if ( s.getRating() > maxRating ) {
    //             song = s;
    //             maxRating = s.getRating();
    //         }
    //     }

    //     System.out.println("Best rated song: "+ song);

    //     return song;
    // }

    // public Song findLowestRatedSong() {
    //     List<Song> list = this.songs;

    //     Song song = null;
    //     double minRating = 10;

    //     for(Song s: list){
    //         if ( s.getRating() < minRating ) {
    //             song = s;
    //             minRating = s.getRating();
    //         }
    //     }

    //     System.out.println("Lowest rated song: "+ song);

    //     return song;
    // }

    public static void main(String[] args) {
        Gson gson = new Gson();

        // System.out.println(gson.toJson(getInstance().fetchDataset()));

        // Map<String, List<EmploymentInfo>> a = new HashMap<String, List<EmploymentInfo>>();
        // a.put("dataset", getInstance().fetchDataset());
        // System.out.println(gson.toJson(a));

        JsonArray array = JSONObjectFactory.getInstance().generateJSONArray(getInstance().fetchDataset());
        HashMap<String, JsonArray> map = new HashMap<String, JsonArray>();
        map.put("dataset", array);
        System.out.println(gson.toJson(map));

        // GsonBuilder gsonBuilder = new GsonBuilder();
        // JsonSerializer<EmploymentInfo> serializer = new EmploymentInfoSerializer();
        // gsonBuilder.registerTypeAdapter(EmploymentInfo.class, serializer);

        // Gson customGson = gsonBuilder.create();
        // System.out.println(customGson.toJson(getInstance().fetchDataset())); 
    }

}
