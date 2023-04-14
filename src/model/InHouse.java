package model;

/**
 * Class for InHouse parts
 */
public class InHouse extends Part {

    private int machineId;

    /**
     * Constructor for new instance of InHouse
     * @param id ID of part in InHouse
     * @param name Name of part in InHouse
     * @param price Price of part in InHouse
     * @param stock Inventory of part in InHouse
     * @param min Minimum inventory of part in InHouse
     * @param max Maximum inventory of part in InHouse
     * @param machineID Machine ID of part in InHouse
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID){
        super(id, name, price, stock, min, max );
        this.machineId = machineID;
    }

    /**
     * Getter for Machine Id
     */
    public int getMachineId(){

        return machineId;
    }

    /**
     * Setter for Machine ID
     * @param machineId Machine ID for part in InHouse
     */
    public void setMachineId(int machineId){

        this.machineId = machineId;
    }
}
