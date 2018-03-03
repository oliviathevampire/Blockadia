package net.thegaminghuskymc.sandboxgame.creativetab;

import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.init.Items;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.util.NonNullList;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

public abstract class CreativeTabs {
    public static final CreativeTabs BUILDING_BLOCKS = new CreativeTabs(0, "buildingBlocks") {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(Item.getItemFromBlock(Blocks.BRICK_BLOCK));
        }
    };
    public static final CreativeTabs DECORATIONS = new CreativeTabs(1, "decorations") {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(Item.getItemFromBlock(Blocks.GLASS), 1, 0);
        }
    };
    public static final CreativeTabs REDSTONE = new CreativeTabs(2, "redstone") {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.REDSTONE);
        }
    };
    public static final CreativeTabs TRANSPORTATION = new CreativeTabs(3, "transportation") {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(Item.getItemFromBlock(Blocks.GOLDEN_RAIL));
        }
    };
    public static final CreativeTabs MISC = new CreativeTabs(6, "misc") {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.LAVA_BUCKET);
        }
    };
    public static final CreativeTabs SEARCH = (new CreativeTabs(5, "search") {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.COMPASS);
        }
    }).setBackgroundImageName("item_search.png");
    public static final CreativeTabs FOOD = new CreativeTabs(7, "food") {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.APPLE);
        }
    };
    public static final CreativeTabs TOOLS = (new CreativeTabs(8, "tools") {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.IRON_AXE);
        }
    });
    public static final CreativeTabs COMBAT = (new CreativeTabs(9, "combat") {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.GOLDEN_SWORD);
        }
    });
    public static final CreativeTabs MATERIALS = MISC;
    public static final CreativeTabs HOTBAR = new CreativeTabs(4, "hotbar") {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(Blocks.BEDROCK);
        }

        /**
         * only shows items which have tabToDisplayOn == this
         */
        @SideOnly(Side.CLIENT)
        public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_) {
            throw new RuntimeException("Implement exception client-side.");
        }

        @SideOnly(Side.CLIENT)
        public boolean isAlignedRight() {
            return true;
        }
    };
    public static final CreativeTabs INVENTORY = (new CreativeTabs(11, "inventory") {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(Item.getItemFromBlock(Blocks.DIRT));
        }
    }).setBackgroundImageName("inventory.png").setNoScrollbar().setNoTitle();
    public static CreativeTabs[] CREATIVE_TAB_ARRAY = new CreativeTabs[12];
    private final int tabIndex;
    private final String tabLabel;
    /**
     * Texture to use.
     */
    private String backgroundTexture = "items.png";
    private boolean hasScrollbar = true;
    /**
     * Whether to draw the title in the foreground of the creative GUI
     */
    private boolean drawTitle = true;
    private ItemStack iconItemStack;

    public CreativeTabs(String label) {
        this(getNextID(), label);
    }

    public CreativeTabs(int index, String label) {
        if (index >= CREATIVE_TAB_ARRAY.length) {
            CreativeTabs[] tmp = new CreativeTabs[index + 1];
            for (int x = 0; x < CREATIVE_TAB_ARRAY.length; x++) {
                tmp[x] = CREATIVE_TAB_ARRAY[x];
            }
            CREATIVE_TAB_ARRAY = tmp;
        }
        this.tabIndex = index;
        this.tabLabel = label;
        this.iconItemStack = ItemStack.EMPTY;
        CREATIVE_TAB_ARRAY[index] = this;
    }

    public static int getNextID() {
        return CREATIVE_TAB_ARRAY.length;
    }

    @SideOnly(Side.CLIENT)
    public int getTabIndex() {
        return this.tabIndex;
    }

    @SideOnly(Side.CLIENT)
    public String getTabLabel() {
        return this.tabLabel;
    }

    /**
     * Gets the translated Label.
     */
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return "itemGroup." + this.getTabLabel();
    }

    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        if (this.iconItemStack.isEmpty()) {
            this.iconItemStack = this.getTabIconItem();
        }

        return this.iconItemStack;
    }

    @SideOnly(Side.CLIENT)
    public abstract ItemStack getTabIconItem();

    @SideOnly(Side.CLIENT)
    public String getBackgroundImageName() {
        return this.backgroundTexture;
    }

    public CreativeTabs setBackgroundImageName(String texture) {
        this.backgroundTexture = texture;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public boolean drawInForegroundOfTab() {
        return this.drawTitle;
    }

    public CreativeTabs setNoTitle() {
        this.drawTitle = false;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldHidePlayerInventory() {
        return this.hasScrollbar;
    }

    public CreativeTabs setNoScrollbar() {
        this.hasScrollbar = false;
        return this;
    }

    /**
     * returns index % 6
     */
    @SideOnly(Side.CLIENT)
    public int getTabColumn() {
        if (tabIndex > 11) {
            return ((tabIndex - 12) % 10) % 5;
        }
        return this.tabIndex % 6;
    }

    /**
     * returns tabIndex < 6
     */
    @SideOnly(Side.CLIENT)
    public boolean isTabInFirstRow() {
        if (tabIndex > 11) {
            return ((tabIndex - 12) % 10) < 5;
        }
        return this.tabIndex < 6;
    }

    @SideOnly(Side.CLIENT)
    public boolean isAlignedRight() {
        return this.getTabColumn() == 5;
    }

    public int getTabPage() {
        if (tabIndex > 11) {
            return ((tabIndex - 12) / 10) + 1;
        }
        return 0;
    }

    /**
     * Determines if the search bar should be shown for this tab.
     *
     * @return True to show the bar
     */
    public boolean hasSearchBar() {
        return tabIndex == CreativeTabs.SEARCH.tabIndex;
    }

    /**
     * Gets the width of the search bar of the creative tab, use this if your
     * creative tab name overflows together with a custom texture.
     *
     * @return The width of the search bar, 89 by default
     */
    public int getSearchbarWidth() {
        return 89;
    }
}