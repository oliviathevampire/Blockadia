package team.hdt.blockadia.engine.core.item;

public class ItemGroup {

    public static ItemGroup BASIC_BLOCKS = new ItemGroup("basic_blocks");
    public static ItemGroup BASIC_ITEMS = new ItemGroup("basic_items");

    private String groupName;

    public ItemGroup(String groupName) {
        this.groupName = groupName;
    }

}
