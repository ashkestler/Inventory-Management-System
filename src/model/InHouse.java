package model;

/** Models an In-House part.
 *
 * @author Ashley Kestler
 */

public class InHouse extends Part {
    /** The machine ID for the part */
    private int machineID;

    /** The constructor for new instance of InHouse object
     * @param partID Unique identifier for part
     * @param partName Name for part
     * @param partPrice Price or cost of part
     * @param partStock Inventory level of part
     * @param min The minimum inventory level
     * @param max The maximum inventory level
     * @param machineID The machine ID for the part
     * */
    public InHouse(int partID, String partName, double partPrice, int partStock, int min, int max, int machineID) {
        super(partID, partName, partPrice, partStock, min, max);
    }

    /**
     *
     * @return machine ID for part
     */
    public int getMachineID() {
        return machineID;
    }

    /** Sets machine ID for part.
     *
     * @param machineID
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
