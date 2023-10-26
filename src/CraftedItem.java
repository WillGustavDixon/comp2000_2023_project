public class CraftedItem extends Item {
    public ItemInterface[] components;

    public CraftedItem(ItemDefinition def, ItemInterface[] comps) {
        super(def);
        components = comps;
    }

    public double getWeight() {
        double sum = 0;
        for (int i = 0; i < components.length; i++) {
            sum += components[i].getWeight();
        }
        return sum;
    }

    public String getCompositionDescription() {
        String comps = "";
        for (int i = 0; i < components.length -1; i++) {
            comps += components[i].getName() + ", \n";
        }
        comps += components[components.length-1].getName();
        return comps;
    }
}
