package model;

/** Models an Outsourced part.
 *
 * @author Ashley Kestler
 */

public class Outsourced extends Part {
    private String companyName;

    /** The constructor for new instance of Outsourced object.
     *
     * @param partID Unique identifier for part
     * @param partName Name for part
     * @param partPrice Price or cost of part
     * @param partStock Inventory level of part
     * @param min The minimum inventory level
     * @param max The maximum inventory level
     * @param companyName The company name of the manufacturer of the part */
    public Outsourced(int partID, String partName, double partPrice, int partStock, int min, int max, String companyName) {
        super(partID, partName, partPrice, partStock, min, max);
    }

    /**
     *
     * @return company name for part
     */
    public String getCompanyName() {
        return companyName;
    }

    /** Sets company name for part.
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
