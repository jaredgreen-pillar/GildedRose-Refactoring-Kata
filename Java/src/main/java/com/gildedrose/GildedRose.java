package com.gildedrose;

class GildedRose {
    Item[] items;

    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED_NAME = "Conjured Mana Cake";

    private static final int MAXIMUM_QUALITY = 50;
    private static final int MINIMUM_QUALITY = 0;

    private static final int FAR_BACKSTAGE_PASS_SELLIN = 10;
    private static final int NEAR_BACKSTAGE_PASS_SELLIN = 5;

    private static final int NEAR_BACKSTAGE_DEGRADATION_RATE = 3;
    private static final int FAR_BACKSTAGE_DEGRADATION_RATE = 2;
    private static final int STANDARD_DEGRADATION_RATE = 1;

    private static final int STANDARD_ITEM_DEGRADATION_MULTIPLIER = 1;
    private static final int CONJURED_ITEM_DEGRADATION_MULTIPLIER = 2;
    private static final int EXPIRED_ITEM_DEGRADATION_MULTIPLIER = 2;


    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (SULFURAS.equals(item.name)) {
                continue;
            }

            int degradationRate = calculateDegradationRate(item);
            decrementQuality(item, degradationRate);

            item.sellIn--;
        }
    }

    private int calculateDegradationMultiplier(Item item) {
        int degradationMultiplier = STANDARD_ITEM_DEGRADATION_MULTIPLIER;
        if (CONJURED_NAME.equals(item.name)) {
            degradationMultiplier *= CONJURED_ITEM_DEGRADATION_MULTIPLIER;
        }
        if (isPastSellByDate(item)) {
            degradationMultiplier *= EXPIRED_ITEM_DEGRADATION_MULTIPLIER;
        }
        return degradationMultiplier;
    }

    private int calculateDegradationRate(Item item) {
        int degradationRate = STANDARD_DEGRADATION_RATE;
        int degradationMultiplier = calculateDegradationMultiplier(item);

        if (BACKSTAGE_PASSES.equals(item.name)) {
            degradationRate = calculateBackstageDegradationRate(item);
        }
        degradationRate *= degradationMultiplier;

        int appreciationRate = -degradationRate;
        return isDegrading(item) ? degradationRate : appreciationRate;
    }

    private int calculateBackstageDegradationRate(Item item) {
        if (isPastSellByDate(item)) {
            return item.quality;
        } else if (item.sellIn <= NEAR_BACKSTAGE_PASS_SELLIN) {
            return NEAR_BACKSTAGE_DEGRADATION_RATE;
        } else if (item.sellIn <= FAR_BACKSTAGE_PASS_SELLIN) {
            return FAR_BACKSTAGE_DEGRADATION_RATE;
        }
        return STANDARD_DEGRADATION_RATE;
    }

    private void decrementQuality(Item item, int degradationRate) {
        item.quality -= degradationRate;
        if (item.quality >= MAXIMUM_QUALITY) {
            item.quality = MAXIMUM_QUALITY;
        }
        if (item.quality <= MINIMUM_QUALITY) {
            item.quality = MINIMUM_QUALITY;
        }
    }

    private boolean isDegrading(Item item) {
        String itemName = item.name;
        if (BACKSTAGE_PASSES.equals(itemName)) {
            return isPastSellByDate(item);
        }

        return !AGED_BRIE.equals(itemName);
    }

    private boolean isPastSellByDate(Item item) {
        return item.sellIn <= 0;
    }
}