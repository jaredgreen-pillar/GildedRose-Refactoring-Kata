package com.gildedrose;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GildedRoseTest {

    private static final String STANDARD_ITEM_NAME = "Thunder-Forged Steel Longsword";
    private static final String AGED_BRIE_ITEM_NAME = "Aged Brie";
    private static final String SULFURAS_ITEM_NAME = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE_PASS_ITEM_NAME = "Backstage passes to a TAFKAL80ETC concert";
    private static final String CONJURED_ITEM_NAME = "Conjured Mana Cake";

    private static final int POSITIVE_DAYS_SELL_IN = 1;
    private static final int ZERO_DAYS_SELL_IN = 0;
    private static final int NEGATIVE_DAYS_SELL_IN = -1;
    private static final int GREATER_THAN_TEN_DAYS_SELL_IN = 11;
    private static final int TEN_DAYS_SELL_IN = 10;
    private static final int FIVE_DAYS_SELL_IN = 5;

    private static final int MAXIMUM_QUALITY = 50;
    private static final int POSITIVE_QUALITY = 15;
    private static final int ZERO_QUALITY = 0;

    @Test
    public void constructorTakesInItemsAndSetsAsProperty() {
        Item firstItem = new Item(STANDARD_ITEM_NAME, 0, 0);
        Item secondItem = new Item(STANDARD_ITEM_NAME, 2, 2);
        Item[] items = new Item[]{firstItem, secondItem};

        GildedRose app = new GildedRose(items);

        assertThat(app.items, is(items));
    }

    @Test
    public void whenUpdateQualityLowersSellInByOne() {
        Item[] items = new Item[]{new Item(STANDARD_ITEM_NAME, POSITIVE_DAYS_SELL_IN, 0)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        int expectedSellIn = POSITIVE_DAYS_SELL_IN - 1;
        assertThat(app.items[0].sellIn, is(expectedSellIn));
    }

    @Test
    public void givenNonzeroSellInWhenUpdateQualityThenLowersQualityByOne() {
        Item[] items = new Item[]{new Item(STANDARD_ITEM_NAME, POSITIVE_DAYS_SELL_IN, POSITIVE_QUALITY)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        int expectedQuality = POSITIVE_QUALITY - 1;
        assertThat(app.items[0].quality, is(expectedQuality));
    }

    @Test
    public void givenMultipleItemsWhenUpdateQualityThenUpdatesForAllItems() {
        Item firstItem = new Item(STANDARD_ITEM_NAME, 1, 1);
        Item secondItem = new Item(STANDARD_ITEM_NAME, 2, 2);
        Item[] items = new Item[]{firstItem, secondItem};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(app.items[0].sellIn, is(0));
        assertThat(app.items[0].quality, is(0));
        assertThat(app.items[1].sellIn, is(1));
        assertThat(app.items[1].quality, is(1));
    }

    @Test
    public void givenZeroSellInWhenUpdateQualityThenLowersQualityByTwo() {
        Item[] items = new Item[]{new Item(STANDARD_ITEM_NAME, ZERO_DAYS_SELL_IN, POSITIVE_QUALITY)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        int expectedQuality = POSITIVE_QUALITY - 2;
        assertThat(app.items[0].quality, is(expectedQuality));
    }

    @Test
    public void givenNegativeSellInWhenUpdateQualityThenLowersQualityByTwo() {
        Item[] items = new Item[]{new Item(STANDARD_ITEM_NAME, NEGATIVE_DAYS_SELL_IN, POSITIVE_QUALITY)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        int expectedQuality = POSITIVE_QUALITY - 2;
        assertThat(app.items[0].quality, is(expectedQuality));
    }

    @Test
    public void givenPositiveSellInAndZeroQualityWhenUpdateQualityThenQualityRemainsZero() {
        Item[] items = new Item[]{new Item(STANDARD_ITEM_NAME, POSITIVE_DAYS_SELL_IN, ZERO_QUALITY)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(app.items[0].quality, is(ZERO_QUALITY));
    }

    @Test
    public void givenZeroSellInAndZeroQualityWhenUpdateQualityThenQualityRemainsZero() {
        Item[] items = new Item[]{new Item(STANDARD_ITEM_NAME, ZERO_DAYS_SELL_IN, ZERO_QUALITY)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(app.items[0].quality, is(ZERO_QUALITY));
    }

    @Test
    public void givenNegativeSellInAndZeroQualityWhenUpdateQualityThenQualityRemainsZero() {
        Item[] items = new Item[]{new Item(STANDARD_ITEM_NAME, NEGATIVE_DAYS_SELL_IN, ZERO_QUALITY)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(app.items[0].quality, is(ZERO_QUALITY));
    }

    @Test
    public void givenAgedBrieUpdateQualityIncreasesInQuality() {
        Item[] items = new Item[]{new Item(AGED_BRIE_ITEM_NAME, POSITIVE_DAYS_SELL_IN, ZERO_QUALITY)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        int expectedQuality = ZERO_QUALITY + 1;
        assertThat(app.items[0].quality, is(expectedQuality));
    }

    @Test
    public void givenAgedBrieAndQualityOfFiftyWhenUpdateQualityThenQualityRemainsFifty() {
        Item[] items = new Item[]{new Item(AGED_BRIE_ITEM_NAME, POSITIVE_DAYS_SELL_IN, MAXIMUM_QUALITY)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(app.items[0].quality, is(MAXIMUM_QUALITY));
    }

    @Test
    public void givenAgedBrieAndSellInLessThanOrEqualToZeroWhenUpdateQualityThenQualityRemainsFifty() {
        Item[] items = new Item[]{new Item(AGED_BRIE_ITEM_NAME, ZERO_DAYS_SELL_IN, POSITIVE_QUALITY)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(app.items[0].quality, is(POSITIVE_QUALITY + 2));
    }

    @Test
    public void givenSulfurasWhenUpdateQualityThenSellInAndQualityRemainsTheSame() {
        Item[] items = new Item[]{new Item(SULFURAS_ITEM_NAME, POSITIVE_DAYS_SELL_IN, POSITIVE_QUALITY)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(app.items[0].sellIn, is(POSITIVE_DAYS_SELL_IN));
        assertThat(app.items[0].quality, is(POSITIVE_QUALITY));
    }

    @Test
    public void givenBackstagePassesAndGreaterThanTenDaysSellInWhenUpdateQualityThenQualityIncreasesByOne() {
        Item[] items = new Item[]{new Item(BACKSTAGE_PASS_ITEM_NAME, GREATER_THAN_TEN_DAYS_SELL_IN, POSITIVE_QUALITY)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(app.items[0].quality, is(POSITIVE_QUALITY + 1));
    }

    @Test
    public void givenBackstagePassesAndTenOrLessDaysSellInWhenUpdateQualityThenQualityIncreasesByTwo() {
        Item[] items = new Item[]{new Item(BACKSTAGE_PASS_ITEM_NAME, TEN_DAYS_SELL_IN, POSITIVE_QUALITY)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(app.items[0].quality, is(POSITIVE_QUALITY + 2));
    }

    @Test
    public void givenBackstagePassesAndFiveOrLessDaysSellInWhenUpdateQualityThenQualityIncreasesByThree() {
        Item[] items = new Item[]{new Item(BACKSTAGE_PASS_ITEM_NAME, FIVE_DAYS_SELL_IN, POSITIVE_QUALITY)};

        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(app.items[0].quality, is(POSITIVE_QUALITY + 3));
    }

    @Test
    public void givenBackstagePassesAndZeroToNegativeDaysSellInWhenUpdateQualityThenQualityIsSetToZero() {
        Item[] items = new Item[]{new Item(BACKSTAGE_PASS_ITEM_NAME, ZERO_DAYS_SELL_IN, POSITIVE_QUALITY)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(app.items[0].quality, is(ZERO_QUALITY));
    }

    @Test
    public void givenConjuredItemAndGreaterThanZeroDaysSellInWhenUpdateQualityThenQualityDegradesByTwo() {
        Item[] items = new Item[]{new Item(CONJURED_ITEM_NAME, POSITIVE_DAYS_SELL_IN, POSITIVE_QUALITY)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(app.items[0].quality, is(POSITIVE_QUALITY - 2));
    }

    @Test
    public void givenConjuredItemAndZeroToNegativeDaysSellInWhenUpdateQualityThenQualityDegradesByFour() {
        Item[] items = new Item[]{new Item(CONJURED_ITEM_NAME, ZERO_DAYS_SELL_IN, POSITIVE_QUALITY)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(app.items[0].quality, is(POSITIVE_QUALITY - 4));
    }
}
