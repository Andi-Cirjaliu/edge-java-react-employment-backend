package application.models;

public class EmploymentInfo {
    int id;
    int year;
    int population;
    int labor_force;
    double population_percent;
    int employed_total;
    double employed_percent;
    int agrictulture_ratio;
    int nonagriculture_ratio;
    int unemployed;
    double unemployed_percent;
    int not_in_labor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getLabor_force() {
        return labor_force;
    }

    public void setLabor_force(int labor_force) {
        this.labor_force = labor_force;
    }

    public double getPopulation_percent() {
        return population_percent;
    }

    public void setPopulation_percent(double population_percent) {
        this.population_percent = population_percent;
    }

    public int getEmployed_total() {
        return employed_total;
    }

    public void setEmployed_total(int employed_total) {
        this.employed_total = employed_total;
    }

    public double getEmployed_percent() {
        return employed_percent;
    }

    public void setEmployed_percent(double employed_percent) {
        this.employed_percent = employed_percent;
    }

    public int getAgrictulture_ratio() {
        return agrictulture_ratio;
    }

    public void setAgrictulture_ratio(int agrictulture_ratio) {
        this.agrictulture_ratio = agrictulture_ratio;
    }

    public int getNonagriculture_ratio() {
        return nonagriculture_ratio;
    }

    public void setNonagriculture_ratio(int nonagriculture_ratio) {
        this.nonagriculture_ratio = nonagriculture_ratio;
    }

    public int getUnemployed() {
        return unemployed;
    }

    public void setUnemployed(int unemployed) {
        this.unemployed = unemployed;
    }

    public double getUnemployed_percent() {
        return unemployed_percent;
    }

    public void setUnemployed_percent(double unemployed_percent) {
        this.unemployed_percent = unemployed_percent;
    }

    public int getNot_in_labor() {
        return not_in_labor;
    }

    public void setNot_in_labor(int not_in_labor) {
        this.not_in_labor = not_in_labor;
    }

    @Override
    public String toString() {
        return "EmploymentInfo [agrictulture_ratio=" + agrictulture_ratio + ", employed_percent=" + employed_percent
                + ", employed_total=" + employed_total + ", labor_force=" + labor_force + ", nonagriculture_ratio="
                + nonagriculture_ratio + ", not_in_labor=" + not_in_labor + ", population=" + population
                + ", population_percent=" + population_percent + ", unemployed=" + unemployed + ", unemployed_percent="
                + unemployed_percent + ", year=" + year + "]";
    }
    
    
}