package com.baasday;

import java.util.Map;

public class Item extends BaasdayObject {
    private final String collectionName;

    Item(final String collectionName, final Map<String, Object> values) {
        super(values);
        this.collectionName = collectionName;
    }

    public String getCollectionName() {
        return this.collectionName;
    }

    private static String collectionAPIPath(final String collectionName) {
        return "items/" + collectionName;
    }

    private static String apiPath(final String collectionName, final String id) {
        return collectionAPIPath(collectionName) + "/" + id;
    }

    String apiPath() throws BaasdayException {
        return apiPath(this.collectionName, this.getId());
    }

    private static class ItemFactory implements APIClient.BaasdayObjectFactory<Item> {
        private final String collectionName;

        ItemFactory(final String collectionName) {
            this.collectionName = collectionName;
        }

        public Item createFromAPIResult(final Map<String, Object> values) throws BaasdayException {
            return new Item(this.collectionName, values);
        }
    }

    public static Item create(final String collectionName, final Map<String, Object> values) throws BaasdayException {
        return APIClient.create(collectionName, values, new ItemFactory(collectionName));
    }

    public static Item create(final String collectionName) throws BaasdayException {
        return create(collectionName, null);
    }

    public static Item fetch(final String collectionName, final String id) throws BaasdayException {
        return APIClient.fetch(apiPath(collectionName, id), new ItemFactory(collectionName));
    }

    public static ListResult<Item> fetchAll(final String collectionName, final Query query) throws BaasdayException {
        return APIClient.fetchAll(collectionAPIPath(collectionName), query, new ItemFactory(collectionName));
    }

    public static ListResult<Item> fetchAll(final String collectionName) throws BaasdayException {
        return fetchAll(collectionName, null);
    }
}
