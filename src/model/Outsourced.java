package model;

/**
 * Class for outsourced parts that extend the Part class
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Constructor for a new instance of Outsourced
     * @param id ID for new instance of Outsourced
     * @param name Name for new instance of Outsourced
     * @param price Price for new instance of Outsourced
     * @param stock Stock for new instance of Outsourced
     * @param min Min for new instance of Outsourced
     * @param max Max for new instance of Outsourced
     * @param companyName Company Name for new instance of Outsourced
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Getter for companyName
     * @return companyName Company Name of the part
     */
    public String getCompanyName(){
        return companyName;
    }

    /**
     * Setter for companyName
     * @param companyName Company Name of the part
     */

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
}
