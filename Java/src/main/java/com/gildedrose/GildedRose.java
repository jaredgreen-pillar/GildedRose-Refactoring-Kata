package com.gildedrose;

class GildedRose {
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final int MAXIMUM_QUALITY = 50;
    public static final int FAR_OUT_BACKSTAGE_PASS_SELLIN = 11;
    public static final int CLOSE_BACKSTAGE_PASS_SELLIN = 6;
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            String itemName = item.name;
            boolean isSulfuras = itemName.equals(SULFURAS);
            boolean isBackstagePass = itemName.equals(BACKSTAGE_PASSES);
            boolean isAgedBrie = itemName.equals(AGED_BRIE);

            if (!isAgedBrie && !isBackstagePass) {
                if (item.quality > 0) {
                    if (!isSulfuras) {
                        item.quality = item.quality - 1;
                    }
                }
            } else {
                if (item.quality < MAXIMUM_QUALITY) {
                    item.quality = item.quality + 1;

                    if (isBackstagePass) {
                        if (item.sellIn < FAR_OUT_BACKSTAGE_PASS_SELLIN) {
                            if (item.quality < MAXIMUM_QUALITY) {
                                item.quality = item.quality + 1;
                            }
                        }

                        if (item.sellIn < CLOSE_BACKSTAGE_PASS_SELLIN) {
                            if (item.quality < MAXIMUM_QUALITY) {
                                item.quality = item.quality + 1;
                            }
                        }
                    }
                }
            }

            if (!isSulfuras) {
                item.sellIn = item.sellIn - 1;
            }

            if (item.sellIn < 0) {
                if (!isAgedBrie) {
                    if (!isBackstagePass) {
                        if (item.quality > 0) {
                            if (!isSulfuras) {
                                item.quality = item.quality - 1;
                            }
                        }
                    } else {
                        item.quality = 0;
                    }
                } else {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1;
                    }
                }
            }
        }
    }
}